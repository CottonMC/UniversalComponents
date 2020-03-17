package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.CommonComponents;
import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.ChunkSyncedComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.chunk.Chunk;

/**
 * Synced inventory component for adding item storage onto a chunk.
 */
public class ChunkSyncedInventoryComponent extends SimpleInventoryComponent implements ChunkSyncedComponent<InventoryComponent> {
	private ComponentType<InventoryComponent> type;
	private Chunk chunk;

	public ChunkSyncedInventoryComponent(int size, Chunk chunk) {
		this(size, CommonComponents.INVENTORY_COMPONENT, chunk);
	}

	public ChunkSyncedInventoryComponent(int size, ComponentType<InventoryComponent> type, Chunk chunk) {
		super(size);
		this.type = type;
		this.chunk = chunk;
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
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public ComponentType<InventoryComponent> getComponentType() {
		return type;
	}
}
