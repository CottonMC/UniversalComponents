package io.github.cottonmc.component.item;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.api.Observable;
import io.github.cottonmc.component.compat.vanilla.InventoryWrapper;
import io.github.cottonmc.component.serializer.StackSerializer;
import nerdhub.cardinal.components.api.component.Component;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface InventoryComponent extends Component, Observable {

	/**
	 * @return How many slots are in this inventory.
	 */
	int getSize();

	/**
	 * @return Whether this inventory is empty or not.
	 */
	default boolean isEmpty() {
		for (ItemStack stack : getStacks()) {
			if (!stack.isEmpty()) return false;
		}
		return true;
	}

	/**
	 * @return A copy of the list of all item stacks in this inventory.
	 */
	//TODO: is it really worth doing a deep copy here? It's a lot more expensive and I'm not a hundred percent sure it's worth it
	List<ItemStack> getStacks();

	/**
	 * DO NOT USE THIS FOR COMPONENT-TO-COMPONENT INTERACTION. ONLY EXISTS FOR INTEGRATION PURPOSES.
	 * @return The mutable list of all item stacks in this inventory.
	 */
	DefaultedList<ItemStack> getMutableStacks();

	/**
	 * @param slot The slot to get the stack for.
	 * @return A copy of the item stack in the slot.
	 */
	ItemStack getStack(int slot);

	/**
	 * @param slot The slot to insert into.
	 * @return Whether this slot can be inserted into.
	 */
	boolean canInsert(int slot); //TODO: take a stack argument?

	/**
	 * @param slot The slot to extract from.
	 * @return Whether this slot can be extracted from.
	 */
	boolean canExtract(int slot); //TODO: take a stack argument?

	/**
	 * Remove part of an item stack.
	 * @param slot The slot to take the stack from.
	 * @param amount The amount of items to extract.
	 * @param action The type of action to perform.
	 * @return The stack that was successfully removed.
	 */
	ItemStack takeStack(int slot, int amount, ActionType action);

	/**
	 * Remove an entire item stack.
	 * @param slot The slot to take the stack from.
	 * @param action The type of action to perform.
	 * @return The stack that was successfully removed.
	 */
	ItemStack removeStack(int slot, ActionType action);

	/**
	 * Set the stack in a slot, overriding the slot's previous contents.
	 * @param slot The slot to set the stack in.
	 * @param stack The stack to set.
	 */
	void setStack(int slot, ItemStack stack);

	/**
	 * Insert a stack into a single slot.
	 * @param slot The slot to insert into.
	 * @param stack The stack to insert.
	 * @param action The type of action to perform.
	 * @return Any remainder that was not able to be inserted.
	 */
	ItemStack insertStack(int slot, ItemStack stack, ActionType action);

	/**
	 * Insert a stack into the entire inventory, spilling over into other slots.
	 * @param stack The stack to insert.
	 * @param action The type of action to perform.
	 * @return Any remainder that was not able to be inserted.
	 */
	ItemStack insertStack(ItemStack stack, ActionType action);

	/**
	 * Empty this inventory, removing all stacks.
	 */
	default void clear() {
		for (int i = 0; i < getSize(); i++) {
			removeStack(i, ActionType.PERFORM);
		}
	}

	/**
	 * @param slot The slot to check the max size for.
	 * @return The maximum amount of items able to be held in this slot.
	 */
	default int getMaxStackSize(int slot) {
		return 64;
	}

	/**
	 * @param slot The slot to check for.
	 * @param stack The stack to check for.
	 * @return Whether this stack may be inserted into this slot.
	 */
	default boolean isAcceptableStack(int slot, ItemStack stack) {
		return true;
	}

	/**
	 * @param item The item to check the total of.
	 * @return The total amount of the passed item in this inventory across all stacks.
	 */
	default int amountOf(Item item) {
		return amountOf(Collections.singleton(item));
	}

	/**
	 * @param items The items to check the total of.
	 * @return The total amount of the passed items in this inventory across all stacks.
	 */
	default int amountOf(Set<Item> items) {
		int amount = 0;

		for (ItemStack stack : this.getStacks()) {
			if (items.contains(stack.getItem())) {
				amount += stack.getCount();
			}
		}

		return amount;
	}

	/**
	 * @param item The item to check for.
	 * @return Whether there are any stacks of this item in this inventory.
	 */
	default boolean contains(Item item) {
		return contains(Collections.singleton(item));
	}

	/**
	 * @param items The items to check for.
	 * @return Whether there are any stacks of these items in this inventory.
	 */
	default boolean contains(Set<Item> items) {
		for (ItemStack stack : getStacks()) {
			if (items.contains(stack.getItem()) && stack.getCount() > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Represent this component as a vanilla {@link Inventory} for use in containers and world interaction.
	 */
	default Inventory asInventory() {
		return InventoryWrapper.of(this);
	}

	/**
	 * Represent this component as a vanilla {@link SidedInventory} for use with hoppers and sided world interaction. Only usable for block components.
	 * @param world The world this component is in.
	 * @param pos The position this component is at.
	 * @return A sided inventory wrapper of this component, or null if it's not attached to a block or has no sided behavior.
	 */
	@Nullable
	default SidedInventory asLocalInventory(IWorld world, BlockPos pos) {
		return null;
	}

	@Override
	default void fromTag(CompoundTag tag) {
		clear();
		ListTag items = tag.getList("Items", NbtType.COMPOUND);
		for (int i = 0; i < items.size(); i++) {
			CompoundTag stackTag = (CompoundTag) items.get(i);
			setStack(i, StackSerializer.fromTag(stackTag));
		}
	}

	@Override
	default CompoundTag toTag(CompoundTag tag) {
		ListTag items = new ListTag();
		for (ItemStack stack : getStacks()) {
			items.add(StackSerializer.toTag(stack, new CompoundTag()));
		}
		tag.put("Items", items);
		return tag;
	}
}
