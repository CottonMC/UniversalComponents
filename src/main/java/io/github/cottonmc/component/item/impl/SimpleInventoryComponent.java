package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of an inventory component for adding onto a block.
 */
public class SimpleInventoryComponent implements InventoryComponent {
	protected DefaultedList<ItemStack> stacks;

	public SimpleInventoryComponent(int size) {
		stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
	}

	@Override
	public int getSize() {
		return stacks.size();
	}

	@Override
	public List<ItemStack> getStacks() {
		List<ItemStack> ret = new ArrayList<>();
		for (ItemStack stack : stacks) {
			ret.add(stack.copy());
		}
		return ret;
	}

	@Override
	public ItemStack getStack(int slot) {
		return stacks.get(slot).copy();
	}

	@Override
	public boolean canInsert(int slot) {
		return true;
	}

	@Override
	public boolean canExtract(int slot) {
		return true;
	}

	@Override
	public ItemStack takeStack(int slot, int amount, ActionType action) {
		ItemStack stack = stacks.get(slot);
		if (!action.shouldExecute()) stack = stack.copy();
		return stack.split(amount);
	}

	@Override
	public ItemStack removeStack(int slot, ActionType action) {
		ItemStack stack = stacks.get(slot);
		if (action.shouldExecute()) {
			setStack(slot, ItemStack.EMPTY);
		}
		return stack;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		stacks.set(slot, stack);
	}

	@Override
	public ItemStack insertStack(int slot, ItemStack stack, ActionType action) {
		ItemStack target = stacks.get(slot);

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
			if (action.shouldExecute()) {
				target.increment(stack.getCount());
			}
			return ItemStack.EMPTY;
		} else {
			//the target can't accept our whole stack, we're gonna have a remainder
			if (action.shouldExecute()) {
				target.setCount(maxSize);
			}
			stack.decrement(sizeLeft);
			return stack;
		}
	}

	@Override
	public ItemStack insertStack(ItemStack stack, ActionType action) {
		for (int i = 0; i < stacks.size(); i++) {
			stack = insertStack(i, stack, action);
			if (stack.isEmpty()) return stack;
		}
		return stack;
	}
}
