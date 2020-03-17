package io.github.cottonmc.component.item;

import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.compat.fluidity.FluidityInvHelper;
import io.github.cottonmc.component.compat.lba.LBAInvHelper;
import io.github.cottonmc.component.item.impl.WrappedInvComponent;
import io.github.cottonmc.component.item.impl.WrappedSidedInvComponent;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class BlockInventoryHelper {
	public static boolean hasInventoryComponent(IWorld world, BlockPos pos, @Nullable Direction dir) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		BlockComponentProvider provider = BlockComponentProvider.get(state);
		//check for a full-fledged component
		if (provider.hasComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, dir)) {
			return true;
		}
		//no component, so check for an inventory provider
		if (block instanceof InventoryProvider) {
			if (((InventoryProvider)block).getInventory(state, world, pos) != null) return true;
		}
		//no inventory provider, so check for a block entity that implements Inventory
		if (block.hasBlockEntity()) {
			if (world.getBlockEntity(pos) instanceof Inventory) return true;
		}
		//no block entity inventory, so check for LBA item attributes
		if (FabricLoader.getInstance().isModLoaded("libblockattributes")) {
			if (LBAInvHelper.hasInvAttribute(world, pos, dir)) return true;
		}
		//no LBA item attributes, so check for a Fluidity device provider
		if (FabricLoader.getInstance().isModLoaded("fluidity")) {
			if (FluidityInvHelper.hasInvDevice(world, pos, dir)) return true;
		}
		//nothing else to check, no inventory here
		return false;
	}

	@Nullable
	public static InventoryComponent getInvComponent(IWorld world, BlockPos pos, @Nullable Direction dir) {
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		BlockComponentProvider provider = BlockComponentProvider.get(state);
		if (provider.hasComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, dir)) {
			return provider.getComponent(world, pos, CommonComponents.INVENTORY_COMPONENT, dir);
		}
		if (block instanceof InventoryProvider) {
			SidedInventory inv = ((InventoryProvider)block).getInventory(state, world, pos);
			if (inv != null) return new WrappedSidedInvComponent(inv, dir);
		}
		if (block.hasBlockEntity()) {
			BlockEntity be = world.getBlockEntity(pos);
			if (be instanceof SidedInventory) {
				return new WrappedSidedInvComponent((SidedInventory)be, dir);
			}
			if (be instanceof Inventory) {
				return new WrappedInvComponent((Inventory)be);
			}
		}
		if (FabricLoader.getInstance().isModLoaded("libblockattributes")) {
			InventoryComponent component = LBAInvHelper.getWrappedInvAttribute(world, pos, dir);
			if (component != null) return component;
		}
		if (FabricLoader.getInstance().isModLoaded("fluidity")) {
			InventoryComponent component = FluidityInvHelper.getWrappedInvDevice(world, pos, dir);
			if (component != null) return component;
		}
		return null;
	}

	private BlockInventoryHelper() { }
}
