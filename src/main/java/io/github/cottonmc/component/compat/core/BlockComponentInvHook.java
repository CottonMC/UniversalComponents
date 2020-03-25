package io.github.cottonmc.component.compat.core;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.item.InventoryComponentHelper;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockComponentInvHook implements InventoryComponentHelper.BlockInventoryHook {
	private static final BlockComponentInvHook INSTANCE = new BlockComponentInvHook();

	public static BlockComponentInvHook getInstance() {
		return INSTANCE;
	}

	public boolean hasComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).hasComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, dir);
	}

	@Nullable
	public InventoryComponent getComponent(World world, BlockPos pos, @Nullable Direction dir) {
		return BlockComponentProvider.get(world.getBlockState(pos)).getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, dir);
	}

	private BlockComponentInvHook() { }
}
