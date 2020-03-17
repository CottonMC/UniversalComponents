package io.github.cottonmc.component.compat.vanilla;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Set;

public class WrappedSidedInvComponent implements InventoryComponent {
	private SidedInventory inv;
	private Direction side;

	public WrappedSidedInvComponent(SidedInventory inv, Direction side) {
		this.inv = inv;
		this.side = side;
	}

	@Override
	public int getSize() {
		return inv.getInvSize();
	}

	@Override
	public List<ItemStack> getStacks() {
		int[] slots = inv.getInvAvailableSlots(side);
		List<ItemStack> stacks = DefaultedList.ofSize(inv.getInvSize(), ItemStack.EMPTY);
		for (int slot : slots) {
			stacks.set(slot, inv.getInvStack(slot));
		}
		return stacks;
	}

	@Override
	public ItemStack getStack(int slot) {
		int[] slots = inv.getInvAvailableSlots(side);
		for (int invSlot : slots) {
			if (slot == invSlot) {
				return inv.getInvStack(slot);
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canInsert(int slot) {
		return inv.canInsertInvStack(slot, ItemStack.EMPTY, side); //TODO: better solution?
	}

	@Override
	public boolean canExtract(int slot) {
		return inv.canExtractInvStack(slot, ItemStack.EMPTY, side); //TODO: better solution?
	}

	@Override
	public ItemStack takeStack(int slot, int amount, ActionType action) {
		ItemStack original = inv.getInvStack(slot).copy();
		ItemStack ret = inv.takeInvStack(slot, amount);
		if (!action.shouldExecute()) {
			inv.setInvStack(slot, original); //don't mutate the inventory
		}
		inv.markDirty();
		return ret;
	}

	@Override
	public ItemStack removeStack(int slot, ActionType action) {
		ItemStack original = inv.getInvStack(slot).copy();
		ItemStack ret = inv.removeInvStack(slot);
		if (!action.shouldExecute()) {
			inv.setInvStack(slot, original); //don't mutate the inventory
		}
		inv.markDirty();
		return ret;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inv.setInvStack(slot, stack);
		inv.markDirty();
	}

	@Override
	public ItemStack insertStack(int slot, ItemStack stack, ActionType action) {
		ItemStack target = inv.getInvStack(slot);

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
				target.increment(stack.getCount()); //we can do this safely since the Inventory contract doesn't force immutability
				inv.markDirty();
			}
			return ItemStack.EMPTY;
		} else {
			//the target can't accept our whole stack, we're gonna have a remainder
			if (action.shouldExecute()) {
				target.setCount(maxSize); //we can do this safely since the Inventory contract doesn't force immutability
				inv.markDirty();
			}
			stack.decrement(sizeLeft);
			return stack;
		}
	}

	@Override
	public ItemStack insertStack(ItemStack stack, ActionType action) {
		for (int i = 0; i < inv.getInvSize(); i++) {
			stack = insertStack(i, stack, action);
			if (stack.isEmpty()) return stack;
		}
		return stack;
	}

	@Override
	public boolean isAcceptableStack(int slot, ItemStack stack) {
		return inv.isValidInvStack(slot, stack);
	}

	@Override
	public int amountOf(Set<Item> items) {
		int ret = 0;
		for (Item item : items) {
			ret += inv.countInInv(item);
		}
		return ret;
	}

	@Override
	public boolean contains(Set<Item> items) {
		return inv.containsAnyInInv(items);
	}
}
