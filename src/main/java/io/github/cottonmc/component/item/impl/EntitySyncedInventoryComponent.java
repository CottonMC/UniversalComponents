package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.UniversalComponents;
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
		this(size, UniversalComponents.INVENTORY_COMPONENT, entity);
	}

	public EntitySyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, Entity entity) {
		super(size);
		this.type = type;
		this.entity = entity;
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
