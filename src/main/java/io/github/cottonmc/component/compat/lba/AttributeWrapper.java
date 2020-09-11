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
		return component.size();
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
	public boolean setInvStack(int slot, ItemStack to, Simulation simulation) {
		if (simulation.isAction()) component.setStack(slot, to);
		return true;
	}

	@Override
	public int getChangeValue() {
		return 0; //TODO: impl?
	}

	@Nullable
	@Override
	public ListenerToken addListener(InvMarkDirtyListener listener, ListenerRemovalToken removalToken) {
		return null; //TODO: impl?
	}

	@Override
	public ItemStack attemptExtraction(ItemFilter filter, int maxAmount, Simulation simulation) {
		return component.removeStack(0, maxAmount, actionForSim(simulation)); //TODO: fix
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
