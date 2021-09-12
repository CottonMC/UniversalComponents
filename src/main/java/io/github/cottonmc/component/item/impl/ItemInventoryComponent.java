package io.github.cottonmc.component.item.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

/**
 * Inventory component for adding item storage onto an item stack.
 */
public class ItemInventoryComponent extends SimpleInventoryComponent {
	private ComponentKey<InventoryComponent> type;

	public ItemInventoryComponent(int size) {
		this(size, UniversalComponents.INVENTORY_COMPONENT);
	}

	public ItemInventoryComponent(int size, ComponentKey<InventoryComponent> type) {
		super(size);
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemInventoryComponent that = (ItemInventoryComponent) o;
		NbtCompound tag = new NbtCompound();
		NbtCompound tag1 = new NbtCompound(); //TODO: better way to do this?
		that.writeToNbt(tag);
		this.writeToNbt(tag1);
		return tag1.equals(tag1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	public ComponentKey<InventoryComponent> getComponentKey() {
		return type;
	}
}
