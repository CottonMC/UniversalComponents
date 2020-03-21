package io.github.cottonmc.component.compat.iteminv;

import dev.emi.iteminventory.api.ItemInventory;
import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WrappedItemInventory implements InventoryComponent {
	private ItemStack stack;
	private ItemInventory inv;

	public WrappedItemInventory(ItemStack stack, ItemInventory inventory) {
		this.stack = stack;
		this.inv = inventory;
	}

	@Override
	public int getSize() {
		return inv.getInvSize(stack);
	}

	@Override
	public List<ItemStack> getStacks() {
		List<ItemStack> ret = new ArrayList<>();
		for (int i = 0; i < inv.getInvSize(stack); i++) {
			ret.add(inv.getStack(stack, i).copy());
		}
		return ret;
	}

	@Override
	public DefaultedList<ItemStack> getMutableStacks() {
		throw new UnsupportedOperationException("getMutableStacks only exists for use in asInventory, it should never be called on a WrappedItemInventory!");
	}

	@Override
	public ItemStack getStack(int slot) {
		return inv.getStack(stack, slot).copy();
	}

	@Override
	public boolean canInsert(int slot) {
		return inv.canInsert(stack, slot, ItemStack.EMPTY); //TODO: better solution?
	}

	@Override
	public boolean canExtract(int slot) {
		return inv.canTake(stack, slot);
	}

	@Override
	public ItemStack takeStack(int slot, int amount, ActionType action) {
		ItemStack original = inv.getStack(stack, slot).copy();
		ItemStack ret = inv.getStack(stack, slot).split(amount);
		if (!action.shouldPerform()) {
			inv.setStack(stack, slot, original); //don't mutate the inventory
		}
		return ret;
	}

	@Override
	public ItemStack removeStack(int slot, ActionType action) {
		ItemStack ret = inv.getStack(stack, slot);
		if (action.shouldPerform()) {
			inv.setStack(stack, slot, ItemStack.EMPTY);
		}
		return ret;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inv.setStack(stack, slot, stack);
	}

	@Override
	public ItemStack insertStack(int slot, ItemStack stack, ActionType action) {
		ItemStack target = inv.getStack(stack, slot);

		if (target.isItemEqualIgnoreDamage(stack)) {
			//unstackable, can't merge!
			return stack;
		}
		int count = target.getCount();
		int maxSize = Math.min(target.getItem().getMaxCount(), getMaxStackSize(slot));
		if (count == maxSize) {
			//target stack is already full, can't merge!
			return stack;
		}
		int sizeLeft = maxSize - count;
		if (sizeLeft >= stack.getCount()) {
			//the target stack can accept our whole stack!
			if (action.shouldPerform()) {
				target.increment(stack.getCount()); //we can do this safely since the ItemInventory contract doesn't force immutability
			}
			return ItemStack.EMPTY;
		} else {
			//the target can't accept our whole stack, we're gonna have a remainder
			if (action.shouldPerform()) {
				target.setCount(maxSize); //we can do this safely since the ItemInventory contract doesn't force immutability
			}
			stack.decrement(sizeLeft);
			return stack;
		}
	}

	@Override
	public ItemStack insertStack(ItemStack stack, ActionType action) {
		for (int i = 0; i < inv.getInvSize(stack); i++) {
			stack = insertStack(i, stack, action);
			if (stack.isEmpty()) return stack;
		}
		return stack;
	}

	@Override
	public boolean isAcceptableStack(int slot, ItemStack stack) {
		return inv.canInsert(stack, slot, stack);
	}

	@Override
	public int amountOf(Set<Item> items) {
		int ret = 0;
		for (int i = 0; i < inv.getInvSize(stack); i++) {
			ItemStack invStack = inv.getStack(stack, i);
			if (items.contains(invStack.getItem())) {
				ret += invStack.getCount();
			}
		}
		return ret;
	}

	@Override
	public boolean contains(Set<Item> items) {
		for (int i = 0; i < inv.getInvSize(stack); i++) {
			ItemStack invStack = inv.getStack(stack, i);
			if (items.contains(invStack.getItem())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Inventory asInventory() {
		return null; //TODO: ItemInventory as Inventory
	}

	@Override
	public List<Runnable> getListeners() {
		return new ArrayList<>();
	}
}
