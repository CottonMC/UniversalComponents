package io.github.cottonmc.component.item;

import io.github.cottonmc.component.compat.core.BlockComponentInvHook;
import io.github.cottonmc.component.compat.core.EntityComponentInvHook;
import io.github.cottonmc.component.compat.core.ItemComponentInvHook;
import io.github.cottonmc.component.compat.fluidity.FluidityInvHook;
import io.github.cottonmc.component.compat.iteminv.ItemInvHook;
import io.github.cottonmc.component.compat.lba.LBAInvHook;
import io.github.cottonmc.component.compat.vanilla.WrappedInvComponent;
import io.github.cottonmc.component.compat.vanilla.WrappedSidedInvComponent;
import io.github.cottonmc.component.util.IntegrationHandler;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class InventoryComponentHelper {

	private static final List<BlockInventoryHook> BLOCK_HOOKS = new ArrayList<>();

	private static final List<ItemInventoryHook> ITEM_HOOKS = new ArrayList<>();

	/**
	 * Query whether a block has a compatible inventory component.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param dir The direction to access the inventory from, or null.
	 * @return Whether this block has an inventory we can access.
	 */
	public static boolean hasInventoryComponent(World world, BlockPos pos, @Nullable Direction dir) {
		//check registered block hooks
		for (BlockInventoryHook hook : BLOCK_HOOKS) {
			if (hook.hasComponent(world, pos, dir)) return true;
		}
		//no special hooks, so fall back to vanilla
		return HopperBlockEntity.getInventoryAt(world, pos) != null;
	}

	/**
	 * Get a compatible inventory component on a block.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param dir The direction to access the inventory from, or null.
	 * @return The inventory component on this block, or null if it doesn't exist or is incompatible.
	 */
	@Nullable
	public static InventoryComponent getInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		//check registered block hooks
		for (BlockInventoryHook hook : BLOCK_HOOKS) {
			InventoryComponent component = hook.getComponent(world, pos, dir);
			if (component != null) return component;
		}
		//no special hooks, so fall back to vanilla
		Inventory inv = HopperBlockEntity.getInventoryAt(world, pos);
		if (inv instanceof SidedInventory) {
			return new WrappedSidedInvComponent((SidedInventory)inv, dir);
		}
		if (inv != null) {
			return new WrappedInvComponent(inv);
		}
		return null;
	}

	/**
	 * Query whether a stack has a compatible inventory component.
	 * @param stack The stack to check on.
	 * @return Whether a this stack has an inventory we can access.
	 */
	public static boolean hasInventoryComponent(ItemStack stack) {
		for (ItemInventoryHook hook : ITEM_HOOKS) {
			if (hook.hasComponent(stack)) return true;
		}
		return false;
	}

	/**
	 * Get a compatible inventory component on a stack.
	 * @param stack The stack to check on.
	 * @return The inventory component on this stack, or null if it doesn't exist or is incompatible.
	 */
	@Nullable
	public static InventoryComponent getInventoryComponent(ItemStack stack) {
		for (ItemInventoryHook hook : ITEM_HOOKS) {
			InventoryComponent component = hook.getComponent(stack);
			if (component != null) return component;
		}
		return null;
	}

	/**
	 * Add a new hook for accessing an inventory stored on a block or an entity at a given position.
	 * @param hook The hook to add.
	 */
	public static void addBlockHook(BlockInventoryHook hook) {
		BLOCK_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing an inventory stored on an item stack.
	 * @param hook The hook to add.
	 */
	public static void addItemHook(ItemInventoryHook hook) {
		ITEM_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing both inventories stored on blocks or entities and on item stacks.
	 * @param hook The hook to add.
	 */
	public static void addDualHook(DualInventoryHook hook) {
		BLOCK_HOOKS.add(hook);
		ITEM_HOOKS.add(hook);
	}

	private InventoryComponentHelper() { }

	/**
	 * Interface for accessing inventories in the world - either on a block, or on an entity at the given position.
	 */
	public interface BlockInventoryHook {
		/**
		 * Test for a compatible inventory in the world.
		 * @param world The world to test in.
		 * @param pos The position to test at.
		 * @param dir The direction to test from, or null.
		 * @return Whether a compatible inventory exists here.
		 */
		boolean hasComponent(World world, BlockPos pos, @Nullable Direction dir);

		/**
		 * Get a compatible inventory in the world.
		 * @param world The world to get in.
		 * @param pos The position to get at.
		 * @param dir The direction to get from, or null.
		 * @return A wrapped form of a compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		InventoryComponent getComponent(World world, BlockPos pos, @Nullable Direction dir);
	}

	/**
	 * Interface for accessing inventories on item stacks.
	 */
	public interface ItemInventoryHook {
		/**
		 * Test for a compatible inventory on a stack.
		 * @param stack The stack to test.
		 * @return Whether the stack has a compatible inventory.
		 */
		boolean hasComponent(ItemStack stack);

		/**
		 * Get a compatible inventory on a stack.
		 * @param stack The stack to get from.
		 * @return A wrapped form of the compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		InventoryComponent getComponent(ItemStack stack);
	}

	public interface DualInventoryHook extends BlockInventoryHook, ItemInventoryHook {

	}

	static {
		//block components - first priority for blocks, since they're ours
		addBlockHook("cardinal-components-block", BlockComponentInvHook.INSTANCE);
		//entity components - second priority for blocks, since they're ours
		addBlockHook("cardinal-components-entity", EntityComponentInvHook.INSTANCE);
		//item components - first priority for items
		addItemHook("cardinal-components-item", ItemComponentInvHook.INSTANCE);
		addItemHook("iteminventory", ItemInvHook.INSTANCE);
		addDualHook("libblockattributes_item", LBAInvHook.INSTANCE);
		addDualHook("fluidity", FluidityInvHook.INSTANCE);
		//TODO: Patchwork capabilities once it's out
	}

	private static void addBlockHook(String modid, BlockInventoryHook hook) {
		IntegrationHandler.runIfPresent(modid, () -> addBlockHook(hook));
	}

	private static void addItemHook(String modid, ItemInventoryHook hook) {
		IntegrationHandler.runIfPresent(modid, () -> addItemHook(hook));
	}

	private static void addDualHook(String modid, DualInventoryHook hook) {
		IntegrationHandler.runIfPresent(modid, () -> addDualHook(hook));
	}
}
