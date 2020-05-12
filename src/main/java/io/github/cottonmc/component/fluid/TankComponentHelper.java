package io.github.cottonmc.component.fluid;

import io.github.cottonmc.component.api.ComponentHelper;
import io.github.cottonmc.component.api.IntegrationHandler;
import io.github.cottonmc.component.compat.core.BlockComponentHook;
import io.github.cottonmc.component.compat.core.EntityComponentHook;
import io.github.cottonmc.component.compat.core.ItemComponentHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * DISCLAIMER: ALL CODE HERE NOT FINAL, MAY ENCOUNTER BREAKING CHANGES REGULARLY
 * CROSS-COMPATIBILITY WILL BE IMPLEMENTED WHEN API STABILIZES; PLEASE BE PATIENT
 */
public class TankComponentHelper implements ComponentHelper<TankComponent> {
	public static final TankComponentHelper INSTANCE = new TankComponentHelper();

	private final List<TankComponentHelper.BlockTankHook> BLOCK_HOOKS = new ArrayList<>();

	private final List<TankComponentHelper.ItemTankHook> ITEM_HOOKS = new ArrayList<>();

	//legacy hooks - will be removed on 1.0 release
	@Deprecated
	public static boolean hasTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return INSTANCE.hasExtendedComponent(world, pos, dir);
	}

	@Deprecated
	public static TankComponent getTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return INSTANCE.getExtendedComponent(world, pos, dir);
	}

	@Deprecated
	public static boolean hasTankComponent(ItemStack stack) {
		return INSTANCE.hasComponent(stack);
	}

	@Deprecated
	public static TankComponent getTankComponent(ItemStack stack) {
		return INSTANCE.getComponent(stack);
	}

	@Override
	public boolean hasComponent(BlockView world, BlockPos pos, @Nullable Direction dir, String ignore) {
		//check registered block hooks
		for (TankComponentHelper.BlockTankHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			if (hook.hasTankComponent(world, pos, dir)) return true;
		}
		//no special hooks, so return false
		return false;
	}

	@Nullable
	public TankComponent getComponent(BlockView world, BlockPos pos, @Nullable Direction direction, String ignore) {
		//check registered block hooks
		for (TankComponentHelper.BlockTankHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			TankComponent component = hook.getTankComponent(world, pos, direction);
			if (component != null) return component;
		}
		//no special hooks, so return null
		return null;
	}

	@Override
	public boolean hasExtendedComponent(World world, BlockPos pos, @Nullable Direction direction, String ignore) {
		//check registered block hooks
		for (TankComponentHelper.BlockTankHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			if (hook.hasExtendedTankComponent(world, pos, direction)) return true;
		}
		//no special hooks, so return false
		return false;
	}

	@Override
	public TankComponent getExtendedComponent(World world, BlockPos pos, @Nullable Direction direction, String ignore) {
		//check registered block hooks
		for (TankComponentHelper.BlockTankHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			TankComponent component = hook.getExtendedTankComponent(world, pos, direction);
			if (component != null) return component;
		}
		//no special hooks, so return null
		return null;
	}

	@Override
	public boolean hasComponent(ItemStack stack, String ignore) {
		for (TankComponentHelper.ItemTankHook hook : ITEM_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			if (hook.hasTankComponent(stack)) return true;
		}
		return false;
	}

	@Override
	@Nullable
	public TankComponent getComponent(ItemStack stack, String ignore) {
		for (TankComponentHelper.ItemTankHook hook : ITEM_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			TankComponent component = hook.getTankComponent(stack);
			if (component != null) return component;
		}
		return null;
	}

	/**
	 * Add a new hook for accessing an inventory stored on a block or an entity at a given position.
	 * @param hook The hook to add.
	 */
	public void addBlockHook(TankComponentHelper.BlockTankHook hook) {
		BLOCK_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing an inventory stored on an item stack.
	 * @param hook The hook to add.
	 */
	public void addItemHook(TankComponentHelper.ItemTankHook hook) {
		ITEM_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing both inventories stored on blocks or entities and on item stacks.
	 * @param hook The hook to add.
	 */
	public void addDualHook(TankComponentHelper.DualTankHook hook) {
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
		boolean hasTankComponent(BlockView world, BlockPos pos, @Nullable Direction dir);

		/**
		 * Get a compatible inventory in the world.
		 * @param world The world to get in.
		 * @param pos The position to get at.
		 * @param dir The direction to get from, or null.
		 * @return A wrapped form of a compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		TankComponent getTankComponent(BlockView world, BlockPos pos, @Nullable Direction dir);

		/**
		 * Test for a compatible tank in the world. Supports entities.
		 * @param world The world to test in.
		 * @param pos The position to test at.
		 * @param dir The direction to test from, or null.
		 * @return Whether a compatible tank exists here.
		 */
		default boolean hasExtendedTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
			return hasTankComponent(world, pos, dir);
		}

		/**
		 * Get a compatible tank in the world. Supports entities.
		 * @param world The world to get in.
		 * @param pos The position to get at.
		 * @param dir The direction to get from, or null.
		 * @return A wrapped form of a compatible tank, or null if one doesn't exist.
		 */
		@Nullable
		default TankComponent getExtendedTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
			return getTankComponent(world, pos, dir);
		}

		String getId();
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

		String getId();
	}

	/**
	 * Interface for accessing inventories both in the world and on item stacks.
	 */
	public interface DualTankHook extends BlockTankHook, ItemTankHook { }

	static {
		//block components - first priority for blocks, since they're ours
		IntegrationHandler.runIfPresent("cardinal-components-block", () -> BlockComponentHook::initTank);
		//entity components - second priority for blocks, since they're ours
		IntegrationHandler.runIfPresent("cardinal-components-entity", () -> EntityComponentHook::initTank);
		//item components - first priority for items
		IntegrationHandler.runIfPresent("cardinal-components-item", () -> ItemComponentHook::initTank);
		//TODO: Patchwork capabilities once it's out
	}

}
