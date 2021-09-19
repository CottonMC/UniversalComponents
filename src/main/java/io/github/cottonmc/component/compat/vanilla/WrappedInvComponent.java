package io.github.cottonmc.component.compat.vanilla;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WrappedInvComponent implements InventoryComponent {
	private Inventory inv;

	public WrappedInvComponent(Inventory inv) {
		this.inv = inv;
	}

	@Override
	public int size() {
		return inv.size();
	}

	@Override
	public List<ItemStack> getStacks() {
		List<ItemStack> ret = new ArrayList<>();
		for (int i = 0; i < inv.size(); i++) {
			ret.add(inv.getStack(i).copy());
		}
		return ret;
	}

	@Override
	public DefaultedList<ItemStack> getMutableStacks() {
		throw new UnsupportedOperationException("getMutableStacks only exists for use in asInventory, it should never be called on a WrappedInvComponent!");
	}

	@Override
	public ItemStack getStack(int slot) {
		return inv.getStack(slot).copy();
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
	public ItemStack removeStack(int slot, int amount, ActionType action) {
		ItemStack original = inv.getStack(slot).copy();
		ItemStack ret = inv.removeStack(slot, amount);
		if (!action.shouldPerform()) {
			inv.setStack(slot, original); //don't mutate the inventory
		}
		inv.markDirty();
		return ret;
	}

	@Override
	public ItemStack removeStack(int slot, ActionType action) {
		ItemStack original = inv.getStack(slot).copy();
		ItemStack ret = inv.removeStack(slot);
		if (!action.shouldPerform()) {
			inv.setStack(slot, original); //don't mutate the inventory
		}
		inv.markDirty();
		return ret;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inv.setStack(slot, stack);
		inv.markDirty();
	}

	@Override
	public ItemStack insertStack(int slot, ItemStack stack, ActionType action) {
		ItemStack target = inv.getStack(slot);

		if (!target.isEmpty() && !target.isItemEqualIgnoreDamage(stack)) {
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
				if (target.isEmpty()) {
					inv.setStack(slot, stack);
				} else {
					target.increment(stack.getCount()); //we can do this safely since the Inventory contract doesn't force immutability
				}
				inv.markDirty();
			}
			return ItemStack.EMPTY;
		} else {
			//the target can't accept our whole stack, we're gonna have a remainder
			if (action.shouldPerform()) {
				if (target.isEmpty()) {
					ItemStack newStack = stack.copy();
					newStack.setCount(maxSize);
					inv.setStack(slot, newStack);
				} else {
					target.setCount(maxSize); //we can do this safely since the Inventory contract doesn't force immutability
				}
				inv.markDirty();
			}
			stack.decrement(sizeLeft);
			return stack;
		}
	}

	@Override
	public ItemStack insertStack(ItemStack stack, ActionType action) {
		for (int i = 0; i < inv.size(); i++) {
			stack = insertStack(i, stack, action);
			if (stack.isEmpty()) return stack;
		}
		return stack;
	}

	@Override
	public boolean isAcceptableStack(int slot, ItemStack stack) {
		return inv.isValid(slot, stack);
	}

	@Override
	public int amountOf(Set<Item> items) {
		int ret = 0;
		for (Item item : items) {
			ret += inv.count(item);
		}
		return ret;
	}

	@Override
	public boolean contains(Set<Item> items) {
		return inv.containsAny(items);
	}

	@Override
	public Inventory asInventory() {
		return inv;
	}

	@Override
	public void onChanged() {
		inv.markDirty();
	}

	@Override
	public List<Runnable> getListeners() {
		return new ArrayList<>();
	}
}
