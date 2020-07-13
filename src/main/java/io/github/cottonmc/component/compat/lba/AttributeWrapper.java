package io.github.cottonmc.component.compat.lba;

import alexiil.mc.lib.attributes.ListenerRemovalToken;
import alexiil.mc.lib.attributes.ListenerToken;
import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.item.*;
import alexiil.mc.lib.attributes.item.filter.ItemFilter;
import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.item.InventoryComponent;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class AttributeWrapper implements FixedItemInv, ItemTransferable {
	private InventoryComponent component;

	// TODO: (LBA 0.7.1) Remove this field as the default impl handles no-op listeners
	private int changes;

	public AttributeWrapper(InventoryComponent component) {
		this.component = component;
	}

	@Override
	public ItemInsertable getInsertable() {
		return this;
	}

	@Override
	public ItemExtractable getExtractable() {
		return this;
	}

	@Override
	public int getSlotCount() {
		return component.getSize();
	}

	@Override
	public ItemStack getInvStack(int slot) {
		return component.getStack(slot);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return component.isAcceptableStack(slot, stack);
	}

	@Override
	public int getMaxAmount(int slot, ItemStack stack) {
		return component.getMaxStackSize(slot);
	}

	@Override
	public boolean setInvStack(int slot, ItemStack to, Simulation simulation) {
		// InventoryComponent doesn't return whether it was successful or not
		// so this just checks *everything*
		ItemStack current = getInvStack(slot);
		if (ItemStackUtil.areEqualIgnoreAmounts(to, current)) {

			if (to.getCount() > current.getCount()) {
				if (!component.canInsert(slot)) {
					return false;
				}
			} else if (to.getCount() < current.getCount()) {
				if (!component.canExtract(slot)) {
					return false;
				}
			} else {
				return true;
			}

		} else {
			if (!current.isEmpty() && !component.canExtract(slot)) {
				return false;
			}
			if (!to.isEmpty() && !component.canInsert(slot)) {
				return false;
			}
		}

		if (!component.isAcceptableStack(slot, to)) {
			return false;
		}
		if (simulation.isAction()) component.setStack(slot, to);
		return true;
	}

	@Override
	public int getChangeValue() {
		// TODO: (LBA 0.7.1) Remove this method as the default impl handles no-op listeners
		return changes++;
	}

	@Nullable
	@Override
	public ListenerToken addListener(InvMarkDirtyListener listener, ListenerRemovalToken removalToken) {
		// Not possible: InventoryComponent has no way to call the removal token
		// TODO: (LBA 0.7.1) Remove this method as the default impl handles no-op listeners
		return null;
	}

	@Override
	public ItemStack attemptExtraction(ItemFilter filter, int maxAmount, Simulation simulation) {
		// This is the simplest way to implement this.
		return getGroupedInv().attemptExtraction(filter, maxAmount, simulation);
	}

	@Override
	public ItemStack attemptInsertion(ItemStack stack, Simulation simulation) {
		return component.insertStack(stack, actionForSim(simulation));
	}

	private ActionType actionForSim(Simulation simulation) {
		if (simulation.isAction()) return ActionType.PERFORM;
		else return ActionType.TEST;
	}
}
