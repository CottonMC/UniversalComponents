package io.github.cottonmc.component.compat.vanilla;

import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Vanilla {@link SidedInventory} wrapper for inventory components, so inventory components can have compat with hoppers and similar blocks.
 */
public interface SidedInventoryWrapper extends SidedInventory, InventoryWrapper {

	@Override
	default InventoryComponent getComponent() {
		return getComponent(null);
	}

	@Nullable
	InventoryComponent getComponent(@Nullable Direction dir);

	/**
	 * Construct a new SidedInventory wrapper.
	 * @param func A function that takes a nullable direction and outputs a nullable InventoryComponent.
	 * @return A sided inventory-based wrapper for this
	 */
	static SidedInventoryWrapper of(Function<Direction, InventoryComponent> func) {
		return func::apply;
	}

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
}
