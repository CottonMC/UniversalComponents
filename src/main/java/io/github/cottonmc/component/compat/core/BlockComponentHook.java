package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.fluid.TankComponentHelper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockComponentHook implements InventoryComponentHelper.BlockInventoryHook, TankComponentHelper.BlockTankHook, CapacitorComponentHelper.BlockCapacitorHook {
	public static final BlockComponentHook INSTANCE = new BlockComponentHook();

	public static void initInventory() {
		InventoryComponentHelper.addBlockHook(INSTANCE);
	}

	public static void initTank() {
		TankComponentHelper.addBlockHook(INSTANCE);
	}

	public static void initCap() {
		CapacitorComponentHelper.addBlockHook(INSTANCE);
	}

	public boolean hasInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).hasComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, dir);
	}

	@Nullable
	public InventoryComponent getInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, dir);
	}

	@Override
	public boolean hasTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).hasComponent(world, pos, UniversalComponents.TANK_COMPONENT, dir);
	}

	@Nullable
	@Override
	public TankComponent getTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).getComponent(world, pos, UniversalComponents.TANK_COMPONENT, dir);
	}

	@Override
	public boolean hasCapComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).hasComponent(world, pos, UniversalComponents.CAPACITOR_COMPONENT, dir);
	}

	@Nullable
	@Override
	public CapacitorComponent getCapComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).getComponent(world, pos, UniversalComponents.CAPACITOR_COMPONENT, dir);
	}

	private BlockComponentHook() { }
}
