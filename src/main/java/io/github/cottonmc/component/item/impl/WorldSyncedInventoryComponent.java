package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.UniversalComponents;
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
		this(size, UniversalComponents.INVENTORY_COMPONENT, world);
	}

	public WorldSyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, World world) {
		super(size);
		this.type = type;
		this.world = world;
		listen(this::sync);
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
