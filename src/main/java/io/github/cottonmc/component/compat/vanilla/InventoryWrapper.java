package io.github.cottonmc.component.compat.vanilla;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.extension.SyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

import java.util.Set;

/**
 * Vanilla {@link Inventory} wrapper for inventory components, so inventory components can have compat with vanilla inventories.
 * Based on `ImplementedInventory` by Juuz.
 */
public interface InventoryWrapper extends Inventory {

	/**
	 * @return The component this inventory is wrapping.
	 */
	InventoryComponent getComponent();

	// Creation
	/**
	 * @return An Inventory-based wrapper of the provided component.
	 */
	static InventoryWrapper of(InventoryComponent component) {
		return () -> component;
	}
	
	// Inventory
	/**
	 * @return The inventory size.
	 */
	@Override
	default int getInvSize() {
		return getComponent().getMutableStacks().size();
	}

	/**
	 * @return true if this inventory has only empty stacks, false otherwise.
	 */
	@Override
	default boolean isInvEmpty() {
		for (int i = 0; i < getInvSize(); i++) {
			ItemStack stack = getInvStack(i);
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the item in the slot.
	 */
	@Override
	default ItemStack getInvStack(int slot) {
		return getComponent().getMutableStacks().get(slot);
	}

	/**
	 * Takes a stack of the size from the slot.
	 * <p>(default implementation) If there are less items in the slot than what are requested,
	 * takes all items in that slot.
	 */
	@Override
	default ItemStack takeInvStack(int slot, int count) {
		ItemStack result = Inventories.splitStack(getComponent().getMutableStacks(), slot, count);
		if (!result.isEmpty()) {
			markDirty();
		}
		return result;
	}

	/**
	 * Removes the current stack in the {@code slot} and returns it.
	 */
	@Override
	default ItemStack removeInvStack(int slot) {
		return Inventories.removeStack(getComponent().getMutableStacks(), slot);
	}

	/**
	 * Replaces the current stack in the {@code slot} with the provided stack.
	 * <p>If the stack is too big for this inventory ({@link Inventory#getInvMaxStackAmount()}),
	 * it gets resized to this inventory's maximum amount.
	 */
	@Override
	default void setInvStack(int slot, ItemStack stack) {
		getComponent().getMutableStacks().set(slot, stack);
		if (stack.getCount() > getInvMaxStackAmount()) {
			stack.setCount(getInvMaxStackAmount());
		}
	}

	/**
	 * Clears {@linkplain InventoryComponent#getMutableStacks() the item list}.
	 */
	@Override
	default void clear() {
		getComponent().getMutableStacks().clear();
	}

	@Override
	default void markDirty() {
		getComponent().markDirty();
	}

	@Override
	default boolean canPlayerUseInv(PlayerEntity player) {
		return true;
	}
}
