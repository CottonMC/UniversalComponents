package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.LevelSyncedComponent;
import net.minecraft.item.ItemStack;

/**
 * Synced inventory component for adding item storage onto a level.
 */
public class LevelSyncedInventoryComponent extends SimpleInventoryComponent implements LevelSyncedComponent {
	private ComponentType<InventoryComponent> type;

	public LevelSyncedInventoryComponent(int size) {
		this(size, UniversalComponents.INVENTORY_COMPONENT);
	}

	public LevelSyncedInventoryComponent(int size, ComponentType<InventoryComponent> type) {
		super(size);
		this.type = type;
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
	public ComponentType<?> getComponentType() {
		return type;
	}
}
