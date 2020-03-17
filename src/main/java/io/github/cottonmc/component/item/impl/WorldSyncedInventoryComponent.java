package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.WorldSyncedComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Synced inventory component for adding item storage onto a world.
 */
public class WorldSyncedInventoryComponent extends SimpleInventoryComponent implements WorldSyncedComponent {
	private ComponentType<InventoryComponent> type;
	private World world;

	public WorldSyncedInventoryComponent(int size, World world) {
		this(size, CommonComponents.INVENTORY_COMPONENT, world);
	}

	public WorldSyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, World world) {
		super(size);
		this.type = type;
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
		return type;
	}
}
