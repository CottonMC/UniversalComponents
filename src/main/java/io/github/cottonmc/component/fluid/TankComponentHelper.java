package io.github.cottonmc.component.fluid;

import io.github.cottonmc.component.api.IntegrationHandler;
import io.github.cottonmc.component.compat.core.BlockComponentInvHook;
import io.github.cottonmc.component.compat.core.EntityComponentInvHook;
import io.github.cottonmc.component.compat.core.ItemComponentInvHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * DISCLAIMER: ALL CODE HERE NOT FINAL, MAY ENCOUNTER BREAKING CHANGES REGULARLY
 * CROSS-COMPATIBILITY WILL BE IMPLEMENTED WHEN API STABILIZES; PLEASE BE PATIENT
 */
public class TankComponentHelper {
	private static final List<TankComponentHelper.BlockTankHook> BLOCK_HOOKS = new ArrayList<>();

	private static final List<TankComponentHelper.ItemTankHook> ITEM_HOOKS = new ArrayList<>();

	/**
	 * Query whether a block has a compatible inventory component.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param dir The direction to access the inventory from, or null.
	 * @return Whether this block has an inventory we can access.
	 */
	public static boolean hasTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		//check registered block hooks
		for (TankComponentHelper.BlockTankHook hook : BLOCK_HOOKS) {
			if (hook.hasTankComponent(world, pos, dir)) return true;
		}
		//no special hooks, so return false
		return false;
	}

	/**
	 * Get a compatible inventory component on a block.
	 * @param world The world the block is in.
	 * @param pos The position the block is at.
	 * @param dir The direction to access the inventory from, or null.
	 * @return The inventory component on this block, or null if it doesn't exist or is incompatible.
	 */
	@Nullable
	public static TankComponent getTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		//check registered block hooks
		for (TankComponentHelper.BlockTankHook hook : BLOCK_HOOKS) {
			TankComponent component = hook.getTankComponent(world, pos, dir);
			if (component != null) return component;
		}
		//no special hooks, so return null
		return null;
	}

	/**
	 * Query whether a stack has a compatible inventory component.
	 * @param stack The stack to check on.
	 * @return Whether a this stack has an inventory we can access.
	 */
	public static boolean hasTankComponent(ItemStack stack) {
		for (TankComponentHelper.ItemTankHook hook : ITEM_HOOKS) {
			if (hook.hasTankComponent(stack)) return true;
		}
		return false;
	}

	/**
	 * Get a compatible inventory component on a stack.
	 * @param stack The stack to check on.
	 * @return The inventory component on this stack, or null if it doesn't exist or is incompatible.
	 */
	@Nullable
	public static TankComponent getTankComponent(ItemStack stack) {
		for (TankComponentHelper.ItemTankHook hook : ITEM_HOOKS) {
			TankComponent component = hook.getTankComponent(stack);
			if (component != null) return component;
		}
		return null;
	}

	/**
	 * Add a new hook for accessing an inventory stored on a block or an entity at a given position.
	 * @param hook The hook to add.
	 */
	public static void addBlockHook(TankComponentHelper.BlockTankHook hook) {
		BLOCK_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing an inventory stored on an item stack.
	 * @param hook The hook to add.
	 */
	public static void addItemHook(TankComponentHelper.ItemTankHook hook) {
		ITEM_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing both inventories stored on blocks or entities and on item stacks.
	 * @param hook The hook to add.
	 */
	public static void addDualHook(TankComponentHelper.DualTankHook hook) {
		BLOCK_HOOKS.add(hook);
		ITEM_HOOKS.add(hook);
	}

	private TankComponentHelper() { }

	/**
	 * Interface for accessing inventories in the world - either on a block, or on an entity at the given position.
	 */
	public interface BlockTankHook {
		/**
		 * Test for a compatible inventory in the world.
		 * @param world The world to test in.
		 * @param pos The position to test at.
		 * @param dir The direction to test from, or null.
		 * @return Whether a compatible inventory exists here.
		 */
		boolean hasTankComponent(World world, BlockPos pos, @Nullable Direction dir);

		/**
		 * Get a compatible inventory in the world.
		 * @param world The world to get in.
		 * @param pos The position to get at.
		 * @param dir The direction to get from, or null.
		 * @return A wrapped form of a compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		TankComponent getTankComponent(World world, BlockPos pos, @Nullable Direction dir);
	}

	/**
	 * Interface for accessing inventories on item stacks.
	 */
	public interface ItemTankHook {
		/**
		 * Test for a compatible inventory on a stack.
		 * @param stack The stack to test.
		 * @return Whether the stack has a compatible inventory.
		 */
		boolean hasTankComponent(ItemStack stack);

		/**
		 * Get a compatible inventory on a stack.
		 * @param stack The stack to get from.
		 * @return A wrapped form of the compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		TankComponent getTankComponent(ItemStack stack);
	}

	/**
	 * Interface for accessing inventories both in the world and on item stacks.
	 */
	public interface DualTankHook extends BlockTankHook, ItemTankHook { }

	static {
		//block components - first priority for blocks, since they're ours
		addBlockHook("cardinal-components-block", BlockComponentInvHook::getInstance);
		//entity components - second priority for blocks, since they're ours
		addBlockHook("cardinal-components-entity", EntityComponentInvHook::getInstance);
		//item components - first priority for items
		addItemHook("cardinal-components-item", ItemComponentInvHook::getInstance);
		//TODO: Patchwork capabilities once it's out
	}

	//TODO: this might still be bad, we'll find out
	private static void addBlockHook(String targetMod, Supplier<BlockTankHook> hook) {
		IntegrationHandler.runIfPresent(targetMod, () -> () -> addBlockHook(hook.get()));
	}

	private static void addItemHook(String targetMod, Supplier<ItemTankHook> hook) {
		IntegrationHandler.runIfPresent(targetMod, () -> () -> addItemHook(hook.get()));
	}

	private static void addDualHook(String targetMod, Supplier<DualTankHook> hook) {
		IntegrationHandler.runIfPresent(targetMod, () -> () -> addDualHook(hook.get()));
	}
}
