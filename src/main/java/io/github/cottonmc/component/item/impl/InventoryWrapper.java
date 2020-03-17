package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.extension.SyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Set;

/**
 * Vanilla {@link Inventory} wrapper for inventory components, so inventory components can have compat with vanilla inventories.
 */
public interface InventoryWrapper extends Inventory {

	InventoryComponent getComponent();

	@Override
	default int getInvSize() {
		return getComponent().getSize();
	}

	@Override
	default boolean isInvEmpty() {
		return getComponent().isEmpty();
	}

	//WARNING: this may cause issues, since vanilla Inventory#getInvStack doesn't have an immutability contract!
	@Override
	default ItemStack getInvStack(int slot) {
		return getComponent().getStack(slot);
	}

	@Override
	default ItemStack takeInvStack(int slot, int amount) {
		return getComponent().takeStack(slot, amount, ActionType.EXECUTE);
	}

	@Override
	default ItemStack removeInvStack(int slot) {
		return getComponent().removeStack(slot, ActionType.EXECUTE);
	}

	@Override
	default void setInvStack(int slot, ItemStack stack) {
		getComponent().setStack(slot, stack);
	}

	@Override
	default int getInvMaxStackAmount() {
		//TODO: better impl
		return getComponent().getMaxStackSize(0);
	}

	@Override
	default void markDirty() {
		if (getComponent() instanceof SyncedComponent) {
			((SyncedComponent) getComponent()).sync();
		}
	}

	@Override
	default boolean canPlayerUseInv(PlayerEntity player) {
		return true;
	}

	@Override
	default void clear() {
		getComponent().clear();
	}

	@Override
	default boolean isValidInvStack(int slot, ItemStack stack) {
		return getComponent().isAcceptableStack(slot, stack);
	}

	@Override
	default int countInInv(Item item) {
		return getComponent().amountOf(item);
	}

	@Override
	default boolean containsAnyInInv(Set<Item> items) {
		return getComponent().contains(items);
	}
}
