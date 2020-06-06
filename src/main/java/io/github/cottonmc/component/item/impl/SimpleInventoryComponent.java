package io.github.cottonmc.component.item.impl;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.component.extension.SyncedComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of an inventory component for adding onto a block.
 */
public class SimpleInventoryComponent implements InventoryComponent {
	protected DefaultedList<ItemStack> stacks;
	private final List<Runnable> listeners = new ArrayList<>();

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
	public DefaultedList<ItemStack> getMutableStacks() {
		return stacks;
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
		if (!action.shouldPerform()) {
			stack = stack.copy();
		} else {
			onChanged();
		}
		return stack.split(amount);
	}

	@Override
	public ItemStack removeStack(int slot, ActionType action) {
		ItemStack stack = stacks.get(slot);
		if (action.shouldPerform()) {
			setStack(slot, ItemStack.EMPTY);
			onChanged();
		}
		return stack;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		if (isAcceptableStack(slot, stack)) stacks.set(slot, stack);
		onChanged();
	}

	@Override
	public ItemStack insertStack(int slot, ItemStack stack, ActionType action) {
		ItemStack target = stacks.get(slot);

		if ((!target.isEmpty() && !target.isItemEqualIgnoreDamage(stack)) || !isAcceptableStack(slot, stack))  {
			//unstackable or unacceptable, can't merge!
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
				if (target.isEmpty()) {
					setStack(slot, stack);
				} else {
					target.increment(stack.getCount());
				}
				onChanged();
			}
			return ItemStack.EMPTY;
		} else {
			//the target can't accept our whole stack, we're gonna have a remainder
			if (action.shouldPerform()) {
				if (target.isEmpty()) {
					ItemStack newStack = stack.copy();
					newStack.setCount(maxSize);
					setStack(slot, newStack);
				} else {
					target.setCount(maxSize);
				}
				onChanged();
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

	@Override
	public List<Runnable> getListeners() {
		return listeners;
	}
}
