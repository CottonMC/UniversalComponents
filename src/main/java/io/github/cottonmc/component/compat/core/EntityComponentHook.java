package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.CapacitorComponentHelper;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.fluid.TankComponentHelper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class EntityComponentHook implements InventoryComponentHelper.BlockInventoryHook, TankComponentHelper.BlockTankHook, CapacitorComponentHelper.BlockCapacitorHook {
	private static final EntityComponentHook INSTANCE = new EntityComponentHook();

	public static void initInventory() {
		InventoryComponentHelper.INSTANCE.addBlockHook(INSTANCE);
	}

	public static void initTank() {
		TankComponentHelper.INSTANCE.addBlockHook(INSTANCE);
	}

	public static void initCap() {
		CapacitorComponentHelper.INSTANCE.addBlockHook(INSTANCE);
	}

	public static final Predicate<Entity> HAS_INV_COMPONENT = entity -> UniversalComponents.INVENTORY_COMPONENT.maybeGet(entity).isPresent();

	public static final Predicate<Entity> HAS_TANK_COMPONENT = entity -> UniversalComponents.TANK_COMPONENT.maybeGet(entity).isPresent();

	public static final Predicate<Entity> HAS_CAP_COMPONENT = entity -> UniversalComponents.CAPACITOR_COMPONENT.maybeGet(entity).isPresent();

	@Override
	public boolean hasInvComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		//can't access entities from just a BlockView!
		return false;
	}

	@Override
	@Nullable
	public InventoryComponent getInvComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		//can't access entities from just a BlockView!
		return null;
	}

	@Override
	public boolean hasExtendedInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_INV_COMPONENT);
		return !list.isEmpty();
	}

	@Nullable
	@Override
	public InventoryComponent getExtendedInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_INV_COMPONENT);
		if (list.isEmpty()) return null;
		return UniversalComponents.INVENTORY_COMPONENT.get(list.get(new Random().nextInt(list.size())));
	}

	@Override
	public boolean hasTankComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		//can't access entities from just a BlockView!
		return false;
	}

	@Nullable
	@Override
	public TankComponent getTankComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		//can't access entities from just a BlockView!
		return null;
	}

	@Override
	public boolean hasExtendedTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_TANK_COMPONENT);
		return !list.isEmpty();
	}

	@Nullable
	@Override
	public TankComponent getExtendedTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_TANK_COMPONENT);
		if (list.isEmpty()) return null;
		return UniversalComponents.TANK_COMPONENT.get(list.get(new Random().nextInt(list.size())));
	}

	@Override
	public boolean hasCapComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		//can't access entities from just a BlockView!
		return false;
	}

	@Nullable
	@Override
	public CapacitorComponent getCapComponent(BlockView world, BlockPos pos, @Nullable Direction dir) {
		//can't access entities from just a BlockView!
		return null;
	}

	@Override
	public boolean hasExtendedCapComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_CAP_COMPONENT);
		return !list.isEmpty();
	}

	@Nullable
	@Override
	public CapacitorComponent getExtendedCapComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_CAP_COMPONENT);
		if (list.isEmpty()) return null;
		return UniversalComponents.CAPACITOR_COMPONENT.get(list.get(new Random().nextInt(list.size())));
	}

	@Override
	public String getId() {
		return "cardinal-components-entity";
	}

	private EntityComponentHook() { }
}
