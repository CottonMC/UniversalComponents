package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.api.ActionType;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.WorldSyncedComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WorldSyncedInventoryComponent extends SimpleInventoryComponent implements WorldSyncedComponent {
	private World world;

	public WorldSyncedInventoryComponent(int size, World world) {
		super(size);
		this.world = world;
	}

	@Override
	public ItemStack takeStack(int slot, int amount, ActionType action) {
		ItemStack ret = super.takeStack(slot, amount, action);
		if (action.shouldExecute()) sync();
		return ret;
	}

	@Override
	public ItemStack removeStack(int slot, ActionType action) {
		ItemStack ret = super.removeStack(slot, action);
		if (action.shouldExecute()) sync();
		return ret;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		super.setStack(slot, stack);
		sync();
	}

	@Override
	public ItemStack insertStack(int slot, ItemStack stack, ActionType action) {
		ItemStack ret = super.insertStack(slot, stack, action);
		if (action.shouldExecute()) sync();
		return ret;
	}

	@Override
	public ItemStack insertStack(ItemStack stack, ActionType action) {
		ItemStack ret = super.insertStack(stack, action);
		if (action.shouldExecute()) sync();
		return ret;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public ComponentType<?> getComponentType() {
		return CommonComponents.INVENTORY_COMPONENT;
	}
}
