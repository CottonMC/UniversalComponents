package io.github.cottonmc.component.compat.vanilla;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.extension.SyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

/**
 * Vanilla {@link SidedInventory} wrapper for inventory components, so inventory components can have compat with hoppers and similar blocks.
 */
public interface SidedInventoryWrapper extends SidedInventory {

	@Nullable
	InventoryComponent getComponent(@Nullable Direction dir);

	@Override
	default int[] getInvAvailableSlots(Direction side) {
		InventoryComponent component = getComponent(side);
		if (component == null) return new int[0];
		return IntStream.range(0, component.getSize()).filter(slot -> component.canInsert(slot) || component.canExtract(slot)).toArray();
	}

	@Override
	default boolean canInsertInvStack(int slot, ItemStack stack, @Nullable Direction dir) {
		InventoryComponent component = getComponent(dir);
		if (component == null) return false;
		return component.canInsert(slot) && component.isAcceptableStack(slot, stack);
	}

	@Override
	default boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
		InventoryComponent component = getComponent(dir);
		if (component == null) return false;
		return component.canExtract(slot) && component.isAcceptableStack(slot, stack);
	}

	@Override
	default int getInvSize() {
		InventoryComponent component = getComponent(null);
		if (component == null) return 0;
		return component.getSize();
	}

	@Override
	default boolean isInvEmpty() {
		InventoryComponent component = getComponent(null);
		return component != null && component.isEmpty();
	}

	//WARNING: this may cause issues, since vanilla Inventory#getInvStack doesn't have an immutability contract!
	@Override
	default ItemStack getInvStack(int slot) {
		InventoryComponent component = getComponent(null);
		if (component == null) return ItemStack.EMPTY;
		return component.getStack(slot);
	}

	@Override
	default ItemStack takeInvStack(int slot, int amount) {
		InventoryComponent component = getComponent(null);
		if (component == null) return ItemStack.EMPTY;
		return component.takeStack(slot, amount, ActionType.EXECUTE);
	}

	@Override
	default ItemStack removeInvStack(int slot) {
		InventoryComponent component = getComponent(null);
		if (component == null) return ItemStack.EMPTY;
		return component.removeStack(slot, ActionType.EXECUTE);
	}

	@Override
	default void setInvStack(int slot, ItemStack stack) {
		InventoryComponent component = getComponent(null);
		if (component != null) component.setStack(slot, stack);
	}

	@Override
	default void markDirty() {
		InventoryComponent component = getComponent(null);
		if (component instanceof SyncedComponent) {
			((SyncedComponent)component).sync();
		}
	}

	@Override
	default boolean canPlayerUseInv(PlayerEntity player) {
		return getComponent(null) != null;
	}

	@Override
	default void clear() {
		InventoryComponent component = getComponent(null);
		if (component != null) component.clear();
	}
}
