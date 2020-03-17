package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

/**
 * Synced inventory component for adding item storage onto an entity.
 */
public class EntitySyncedInventoryComponent extends SimpleInventoryComponent implements EntitySyncedComponent {
	private ComponentType<InventoryComponent> type;
	private Entity entity;

	public EntitySyncedInventoryComponent(int size, Entity entity) {
		this(size, CommonComponents.INVENTORY_COMPONENT, entity);
	}

	public EntitySyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, Entity entity) {
		super(size);
		this.type = type;
		this.entity = entity;
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
	public Entity getEntity() {
		return entity;
	}

	@Override
	public ComponentType<?> getComponentType() {
		return type;
	}
}
