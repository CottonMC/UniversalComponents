package io.github.cottonmc.component.compat.vanilla;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class InvBEWrapper implements SidedInventory {
	private Inventory inv;

	public InvBEWrapper(Inventory inv) {
		this.inv = inv;
	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		return IntStream.range(0, inv.size()).toArray();
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return true;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}

	@Override
	public int size() {
		return inv.size();
	}

	@Override
	public boolean isEmpty() {
		return inv.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		return inv.getStack(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return inv.removeStack(slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot) {
		return inv.removeStack(slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inv.setStack(slot, stack);
	}

	@Override
	public void markDirty() {
		inv.markDirty();
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return inv.canPlayerUse(player);
	}

	@Override
	public void clear() {
		inv.clear();
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return inv.isValid(slot, stack);
	}

	@Override
	public void onOpen(PlayerEntity player) {
		inv.onOpen(player);
	}

	@Override
	public void onClose(PlayerEntity player) {
		inv.onClose(player);
	}

	@Override
	public int getMaxCountPerStack() {
		return inv.getMaxCountPerStack();
	}
}
