package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.util.ItemComponent;
import net.minecraft.nbt.CompoundTag;

/**
 * Inventory component for adding item storage onto an item stack.
 */
public class ItemInventoryComponent extends SimpleInventoryComponent implements ItemComponent<InventoryComponent> {
	private ComponentType<InventoryComponent> type;

	public ItemInventoryComponent(int size) {
		this(size, UniversalComponents.INVENTORY_COMPONENT);
	}

	public ItemInventoryComponent(int size, ComponentType<InventoryComponent> type) {
		super(size);
		this.type = type;
	}

	@Override
	public boolean isComponentEqual(Component component) {
		return component.toTag(new CompoundTag()).equals(this.toTag(new CompoundTag())); //TODO: better way to do this?
	}

	@Override
	public ComponentType<InventoryComponent> getComponentType() {
		return type;
	}
}
