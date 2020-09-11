package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;
import net.minecraft.entity.Entity;

/**
 * Synced inventory component for adding item storage onto an entity.
 */
public class EntitySyncedInventoryComponent extends SimpleInventoryComponent implements EntitySyncedComponent {
	private ComponentType<InventoryComponent> type;
	private Entity entity;

	/**
	 * Create an entity inventory component that works with {@link io.github.cottonmc.component.item.InventoryComponentHelper} access methods.
	 * @param size How many slots this inventory should have.
	 * @param entity The entity this inventory is on.
	 */
	public EntitySyncedInventoryComponent(int size, Entity entity) {
		this(size, UniversalComponents.INVENTORY_COMPONENT, entity);
	}

	/**
	 * Create a custom entity inventory component accessible through your own component type.
	 * @param size How many slots this inventory should have.
	 * @param type The component type this should be accessed with.
	 * @param entity The entity this inventory is on.
	 */
	public EntitySyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, Entity entity) {
		super(size);
		this.type = type;
		this.entity = entity;
		listen(this::sync);
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
