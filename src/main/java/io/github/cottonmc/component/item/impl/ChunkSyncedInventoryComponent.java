package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.ChunkSyncedComponent;
import net.minecraft.world.chunk.Chunk;

/**
 * Synced inventory component for adding item storage onto a chunk.
 */
public class ChunkSyncedInventoryComponent extends SimpleInventoryComponent implements ChunkSyncedComponent<InventoryComponent> {
	private ComponentType<InventoryComponent> type;
	private Chunk chunk;

	public ChunkSyncedInventoryComponent(int size, Chunk chunk) {
		this(size, UniversalComponents.INVENTORY_COMPONENT, chunk);
	}

	public ChunkSyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, Chunk chunk) {
		super(size);
		this.type = type;
		this.chunk = chunk;
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public ComponentType<InventoryComponent> getComponentType() {
		return type;
	}
}
