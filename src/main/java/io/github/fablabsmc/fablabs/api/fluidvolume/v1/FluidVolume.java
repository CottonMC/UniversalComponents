package io.github.fablabsmc.fablabs.api.fluidvolume.v1;

import io.github.cottonmc.component.UniversalComponents;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * DISCLAIMER: ALL CODE HERE NOT FINAL, MAY ENCOUNTER BREAKING CHANGES REGULARLY
 */
public final class FluidVolume {
	public static final FluidVolume EMPTY = new FluidVolume(Fluids.EMPTY);

	private Fluid fluid;
	private Fraction amount;
	private NbtCompound nbt;

	private boolean empty;

	public FluidVolume(Fluid fluid) {
		this(fluid, Fraction.ONE);
	}

	public FluidVolume(Fluid fluid, Fraction amount) {
		this.fluid = fluid;
		this.amount = amount;
		this.updateEmptyState();
	}

	private FluidVolume(NbtCompound nbt) {
		fluid = Registry.FLUID.get(new Identifier(nbt.getString("Id")));
		amount = Fraction.CODEC.parse(NbtOps.INSTANCE, nbt.getCompound("Amount")).resultOrPartial(UniversalComponents.logger::error).orElse(Fraction.ZERO);

		if (nbt.contains("Nbt", NbtType.COMPOUND)) {
			this.nbt = nbt.getCompound("Nbt");
		}

		this.updateEmptyState();
	}

	public Fluid getFluid() {
		return empty ? Fluids.EMPTY : fluid;
	}

	public Fraction getAmount() {
		return empty ? Fraction.ZERO : amount;
	}

	public boolean isEmpty() {
		if (this == EMPTY) {
			return true;
		} else if (this.getFluid() != null && this.getFluid() != Fluids.EMPTY) {
			return !this.amount.isPositive();
		} else {
			return true;
		}
	}

	public void setAmount(Fraction amount) {
		this.amount = amount;
	}

	public void increment(Fraction incrementBy) {
		amount = amount.add(incrementBy);
	}

	public void decrement(Fraction decrementBy) {
		amount = amount.subtract(decrementBy);
		if (amount.isNegative()) amount = Fraction.ZERO;
	}

	public FluidVolume split(Fraction amount) {
		Fraction min = Fraction.min(amount, this.amount);
		FluidVolume volume = this.copy();
		volume.setAmount(min);
		this.decrement(min);
		return volume;
	}

	//TODO: better equality/stackability methods
	public static boolean areFluidsEqual(FluidVolume left, FluidVolume right) {
		return left.getFluid() == right.getFluid();
	}

	//public static boolean areCombinable(FluidVolume left, FluidVolume right) {
	//	if (left == right) return true;
	//	if (left.isEmpty() && right.isEmpty()) return true;
	//}

	private void updateEmptyState() {
		empty = isEmpty();
	}

	public boolean hasNbt() {
		return !empty && nbt != null && !nbt.isEmpty();
	}

	public NbtCompound getNbt() {
		return nbt;
	}

	public NbtCompound getOrCreateNbt() {
		if (nbt == null) {
			nbt = new NbtCompound();
		}

		return nbt;
	}

	public void setNbt(NbtCompound nbt) {
		this.nbt = nbt;
	}

	public FluidVolume copy() {
		if (this.isEmpty()) return FluidVolume.EMPTY;
		FluidVolume stack = new FluidVolume(this.fluid, this.amount);
		if (this.hasNbt()) stack.setNbt(this.getNbt());
		return stack;
	}

	public static FluidVolume fromNbt(NbtCompound nbt) {
		return new FluidVolume(nbt);
	}

	public NbtCompound toNbt(NbtCompound nbt) {
		nbt.putString("Id", Registry.FLUID.getId(getFluid()).toString());

		nbt.put("Amount", Fraction.CODEC.encodeStart(NbtOps.INSTANCE, amount).resultOrPartial(UniversalComponents.logger::error).orElseGet(NbtCompound::new));

		if (this.nbt != null) {
			nbt.put("Nbt", this.nbt.copy());
		}

		return nbt;
	}
}
