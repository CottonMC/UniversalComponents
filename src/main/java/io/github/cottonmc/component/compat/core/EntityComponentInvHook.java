package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
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
import java.util.function.Predicate;

public class EntityComponentInvHook implements InventoryComponentHelper.BlockInventoryHook {
	private static final EntityComponentInvHook INSTANCE = new EntityComponentInvHook();

	public static EntityComponentInvHook getInstance() {
		return INSTANCE;
	}

	public static final Predicate<Entity> HAS_COMPONENT = entity -> UniversalComponents.INVENTORY_COMPONENT.maybeGet(entity).isPresent();

	public boolean hasComponent(World world, BlockPos pos, @Nullable Direction dir) {
		List<Entity> list = world.getEntities((Entity)null, new Box(pos.getX() - 0.5D, pos.getY() - 0.5D, pos.getZ() - 0.5D, pos.getX() + 0.5D, pos.getY()+ 0.5D, pos.getZ() + 0.5D), HAS_COMPONENT);
		return !list.isEmpty();
	}

	@Nullable
	public InventoryComponent getComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, dir);
	}

	private EntityComponentInvHook() { }
}
