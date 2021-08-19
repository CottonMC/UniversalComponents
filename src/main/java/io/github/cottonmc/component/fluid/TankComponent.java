package io.github.cottonmc.component.fluid;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.api.Observable;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.FluidVolume;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DISCLAIMER: ALL CODE HERE NOT FINAL, MAY ENCOUNTER BREAKING CHANGES REGULARLY
 * CROSS-COMPATIBILITY WILL BE IMPLEMENTED WHEN API STABILIZES; PLEASE BE PATIENT
 * Currently works effectively identically to an InventoryComponent, and <i>ignores fluid stack NBT</i>
 */
public interface TankComponent extends Component, Observable {
	int getTanks();

	Fraction getMaxCapacity(int tank);

	default boolean isEmpty() {
		for (FluidVolume volume : getAllContents()) {
			if (!volume.isEmpty()) return false;
		}
		return true;
	}

	List<FluidVolume> getAllContents();

	FluidVolume getContents(int tank);

	boolean canInsert(int tank);

	boolean canExtract(int tank);

	FluidVolume removeFluid(int tank, Fraction amount, ActionType action);

	FluidVolume removeFluid(int tank, ActionType action);

	void setFluid(int tank, FluidVolume fluid);

	FluidVolume insertFluid(int tank, FluidVolume fluid, ActionType action);

	FluidVolume insertFluid(FluidVolume fluid, ActionType action);

	default void clear() {
		for (int i = 0; i < getTanks(); i++) {
			removeFluid(i, ActionType.PERFORM);
		}
	}

	default boolean isAcceptableFluid(int tank) {
		return true;
	}

	default Fraction amountOf(Fluid fluid) {
		return amountOf(Collections.singleton(fluid));
	}

	default Fraction amountOf(Set<Fluid> fluids) {
		Fraction amount = Fraction.ZERO;
		for (FluidVolume volume : getAllContents()) {
			if (fluids.contains(volume.getFluid())) {
				amount = amount.add(volume.getAmount());
			}
		}
		return amount;
	}

	default boolean contains(Fluid fluid) {
		return contains(Collections.singleton(fluid));
	}

	default boolean contains(Set<Fluid> fluids) {
		for (FluidVolume volume : getAllContents()) {
			if (fluids.contains(volume.getFluid())) {
				return true;
			}
		}
		return false;
	}

	@Override
	default void readFromNbt(CompoundTag tag) {
		clear();
		ListTag contents = tag.getList("Contents", NbtType.COMPOUND);
		for (int i = 0; i < contents.size(); i++) {
			CompoundTag volTag = (CompoundTag)contents.get(i);
			setFluid(i, FluidVolume.fromTag(volTag));
		}
	}

	@Override
	default void writeToNbt(CompoundTag tag) {
		ListTag contents = new ListTag();
		for (FluidVolume vol : getAllContents()) {
			contents.add(vol.toTag(new CompoundTag()));
		}
		tag.put("Contents", contents);
	}

}
