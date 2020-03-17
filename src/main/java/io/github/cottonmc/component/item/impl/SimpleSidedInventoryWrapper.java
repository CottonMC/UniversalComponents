package io.github.cottonmc.component.item.impl;


import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.compat.vanilla.SidedInventoryWrapper;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class SimpleSidedInventoryWrapper implements SidedInventoryWrapper {
	private IWorld world;
	private BlockPos pos;
	private BlockComponentProvider provider;

	public SimpleSidedInventoryWrapper(IWorld world, BlockPos pos, BlockState state) {
		this.world = world;
		this.pos = pos;
		this.provider = BlockComponentProvider.get(state);
	}

	@Nullable
	@Override
	public InventoryComponent getComponent(@Nullable Direction dir) {
		return provider.getComponent(world, pos, UniversalComponents.INVENTORY_COMPONENT, dir);
	}
}
