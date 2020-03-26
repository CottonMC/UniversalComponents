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
	public int[] getInvAvailableSlots(Direction side) {
		return IntStream.range(0, inv.getInvSize()).toArray();
	}

	@Override
	public boolean canInsertInvStack(int slot, ItemStack stack, @Nullable Direction dir) {
		return true;
	}

	@Override
	public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
		return true;
	}

	@Override
	public int getInvSize() {
		return inv.getInvSize();
	}

	@Override
	public boolean isInvEmpty() {
		return inv.isInvEmpty();
	}

	@Override
	public ItemStack getInvStack(int slot) {
		return inv.getInvStack(slot);
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount) {
		return inv.takeInvStack(slot, amount);
	}

	@Override
	public ItemStack removeInvStack(int slot) {
		return inv.removeInvStack(slot);
	}

	@Override
	public void setInvStack(int slot, ItemStack stack) {
		inv.setInvStack(slot, stack);
	}

	@Override
	public void markDirty() {
		inv.markDirty();
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player) {
		return inv.canPlayerUseInv(player);
	}

	@Override
	public void clear() {
		inv.clear();
	}
}
