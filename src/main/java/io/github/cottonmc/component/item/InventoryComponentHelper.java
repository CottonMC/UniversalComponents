package io.github.cottonmc.component.item;

import io.github.cottonmc.component.api.ComponentHelper;
import io.github.cottonmc.component.compat.core.BlockComponentHook;
import io.github.cottonmc.component.compat.core.EntityComponentHook;
import io.github.cottonmc.component.compat.core.ItemComponentHook;
import io.github.cottonmc.component.compat.fluidity.FluidityHook;
import io.github.cottonmc.component.compat.iteminv.ItemInvHook;
import io.github.cottonmc.component.compat.lba.LBAInvHook;
import io.github.cottonmc.component.compat.vanilla.SidedInventoryWrapper;
import io.github.cottonmc.component.compat.vanilla.WrappedInvComponent;
import io.github.cottonmc.component.compat.vanilla.WrappedSidedInvComponent;
import io.github.cottonmc.component.api.IntegrationHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class InventoryComponentHelper implements ComponentHelper<InventoryComponent> {
	public static final InventoryComponentHelper INSTANCE = new InventoryComponentHelper();

	private final List<BlockInventoryHook> BLOCK_HOOKS = new ArrayList<>();

	private final List<ItemInventoryHook> ITEM_HOOKS = new ArrayList<>();

	//legacy hooks - will be removed on 1.0 release
	@Deprecated
	public static boolean hasInventoryComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return INSTANCE.hasExtendedComponent(world, pos, dir);
	}

	@Deprecated
	public static InventoryComponent getInventoryComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return INSTANCE.getExtendedComponent(world, pos, dir);
	}

	@Deprecated
	public static boolean hasInventoryComponent(ItemStack stack) {
		return INSTANCE.hasComponent(stack);
	}

	@Deprecated
	public static InventoryComponent getInventoryComponent(ItemStack stack) {
		return INSTANCE.getComponent(stack);
	}

	@Override
	public boolean hasComponent(BlockView world, BlockPos pos, @Nullable Direction dir, String ignore) {
		//check registered block hooks
		for (BlockInventoryHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			if (hook.hasInvComponent(world, pos, dir)) return true;
		}
		//no special hooks, so fall back to vanilla
		if (!ignore.equals("minecraft")) {
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block instanceof InventoryProvider && world instanceof WorldAccess) {
				return ((InventoryProvider)block).getInventory(state, (WorldAccess)world, pos) != null;
			} else if (block.hasBlockEntity()) {
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity instanceof Inventory) {
					if (blockEntity instanceof ChestBlockEntity && block instanceof ChestBlock && world instanceof World) {
						return ChestBlock.getInventory((ChestBlock)block, state, (World)world, pos, true) != null;
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Nullable
	public InventoryComponent getComponent(BlockView world, BlockPos pos, @Nullable Direction dir, String ignore) {
		//check registered block hooks
		for (BlockInventoryHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			InventoryComponent component = hook.getInvComponent(world, pos, dir);
			if (component != null) return component;
		}
		//no special hooks, so fall back to vanilla
		if (!ignore.equals("minecraft")) {
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block instanceof InventoryProvider && world instanceof WorldAccess) {
				SidedInventory inv = ((InventoryProvider)block).getInventory(state, (WorldAccess)world, pos);
				if (inv != null) {
					return new WrappedSidedInvComponent(inv, dir);
				}
			} else if (block.hasBlockEntity()) {
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity instanceof SidedInventory) {
					return new WrappedSidedInvComponent((SidedInventory)blockEntity, dir);
				} else if (blockEntity instanceof Inventory) {
					if (blockEntity instanceof ChestBlockEntity && block instanceof ChestBlock && world instanceof World) {
						return new WrappedInvComponent(ChestBlock.getInventory((ChestBlock)block, state, (World)world, pos, true));
					}
					return new WrappedInvComponent((Inventory)blockEntity);
				}
			}
		}
		return null;
	}

	@Override
	public boolean hasExtendedComponent(World world, BlockPos pos, @Nullable Direction dir, String ignore) {
		//check registered block hooks
		for (BlockInventoryHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			if (hook.hasExtendedInvComponent(world, pos, dir)) return true;
		}
		//no special hooks, so fall back to vanilla
		if (!ignore.equals("minecraft")) return HopperBlockEntity.getInventoryAt(world, pos) != null;
		return false;
	}

	@Override
	@Nullable
	public InventoryComponent getExtendedComponent(World world, BlockPos pos, @Nullable Direction dir, String ignore) {
		//check registered block hooks
		for (BlockInventoryHook hook : BLOCK_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			InventoryComponent component = hook.getExtendedInvComponent(world, pos, dir);
			if (component != null) return component;
		}
		//no special hooks, so fall back to vanilla
		if (!ignore.equals("minecraft")) {
			Inventory inv = HopperBlockEntity.getInventoryAt(world, pos);
			if (inv instanceof SidedInventory) {
				return new WrappedSidedInvComponent((SidedInventory) inv, dir);
			}
			if (inv != null) {
				return new WrappedInvComponent(inv);
			}
		}
		return null;
	}

	@Override
	public boolean hasComponent(ItemStack stack, String ignore) {
		for (ItemInventoryHook hook : ITEM_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			if (hook.hasInvComponent(stack)) return true;
		}
		return false;
	}

	@Override
	@Nullable
	public InventoryComponent getComponent(ItemStack stack, String ignore) {
		for (ItemInventoryHook hook : ITEM_HOOKS) {
			if (hook.getId().equals(ignore)) continue;
			InventoryComponent component = hook.getInvComponent(stack);
			if (component != null) return component;
		}
		return null;
	}

	/**
	 * Add a new hook for accessing an inventory stored on a block or an entity at a given position.
	 * @param hook The hook to add.
	 */
	public void addBlockHook(BlockInventoryHook hook) {
		BLOCK_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing an inventory stored on an item stack.
	 * @param hook The hook to add.
	 */
	public void addItemHook(ItemInventoryHook hook) {
		ITEM_HOOKS.add(hook);
	}

	/**
	 * Add a new hook for accessing both inventories stored on blocks or entities and on item stacks.
	 * @param hook The hook to add.
	 */
	public void addDualHook(DualInventoryHook hook) {
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
		boolean hasInvComponent(BlockView world, BlockPos pos, @Nullable Direction dir);

		/**
		 * Get a compatible inventory in the world.
		 * @param world The world to get in.
		 * @param pos The position to get at.
		 * @param dir The direction to get from, or null.
		 * @return A wrapped form of a compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		InventoryComponent getInvComponent(BlockView world, BlockPos pos, @Nullable Direction dir);

		/**
		 * Test for a compatible inventory in the world. Supports entities.
		 * @param world The world to test in.
		 * @param pos The position to test at.
		 * @param dir The direction to test from, or null.
		 * @return Whether a compatible inventory exists here.
		 */
		default boolean hasExtendedInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
			return hasInvComponent(world, pos, dir);
		}

		/**
		 * Get a compatible inventory in the world. Supports entities.
		 * @param world The world to get in.
		 * @param pos The position to get at.
		 * @param dir The direction to get from, or null.
		 * @return A wrapped form of a compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		default InventoryComponent getExtendedInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
			return getInvComponent(world, pos, dir);
		}

		String getId();
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
		boolean hasInvComponent(ItemStack stack);

		/**
		 * Get a compatible inventory on a stack.
		 * @param stack The stack to get from.
		 * @return A wrapped form of the compatible inventory, or null if one doesn't exist.
		 */
		@Nullable
		InventoryComponent getInvComponent(ItemStack stack);

		String getId();
	}

	/**
	 * Interface for accessing inventories both in the world and on item stacks.
	 */
	public interface DualInventoryHook extends BlockInventoryHook, ItemInventoryHook { }

	static {
		//block components - first priority for blocks, since they're ours
		IntegrationHandler.runIfPresent("cardinal-components-block", () -> BlockComponentHook::initInventory);
		//entity components - second priority for blocks, since they're ours
		IntegrationHandler.runIfPresent("cardinal-components-entity", () -> EntityComponentHook::initInventory);
		//item components - first priority for items
		IntegrationHandler.runIfPresent("cardinal-components-item", () -> ItemComponentHook::initInventory);
		IntegrationHandler.runIfPresent("iteminventory", () -> ItemInvHook::init);
		IntegrationHandler.runIfPresent("libblockattributes_item", () -> LBAInvHook::initInv);
		IntegrationHandler.runIfPresent("fluidity", () -> FluidityHook::initInv);
		//TODO: Patchwork capabilities once it's out
	}
}
