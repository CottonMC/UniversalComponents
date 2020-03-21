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
		listen(this::sync);
	}

	@Override
	public ComponentType<?> getComponentType() {
		return type;
	}
}
