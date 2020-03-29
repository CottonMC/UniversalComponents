package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.fluid.TankComponentHelper;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class EntityComponentInvHook implements InventoryComponentHelper.BlockInventoryHook, TankComponentHelper.BlockTankHook {
	private static final EntityComponentInvHook INSTANCE = new EntityComponentInvHook();

	public static EntityComponentInvHook getInstance() {
		return INSTANCE;
	}

	public static final Predicate<Entity> HAS_INV_COMPONENT = entity -> UniversalComponents.INVENTORY_COMPONENT.maybeGet(entity).isPresent();

	public static final Predicate<Entity> HAS_TANK_COMPONENT = entity -> UniversalComponents.TANK_COMPONENT.maybeGet(entity).isPresent();

	public boolean hasInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_INV_COMPONENT);
		return !list.isEmpty();
	}

	@Nullable
	public InventoryComponent getInvComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_INV_COMPONENT);
		return UniversalComponents.INVENTORY_COMPONENT.get(list.get(new Random().nextInt(list.size())));
	}

	@Override
	public boolean hasTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_TANK_COMPONENT);
		return !list.isEmpty();
	}

	@Nullable
	@Override
	public TankComponent getTankComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_TANK_COMPONENT);
		return UniversalComponents.TANK_COMPONENT.get(list.get(new Random().nextInt(list.size())));
	}

	private EntityComponentInvHook() { }

}
