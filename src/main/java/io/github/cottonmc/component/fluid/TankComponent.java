package io.github.cottonmc.component.fluid;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.api.Observable;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.FluidVolume;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.fluid.Fluid;

import java.util.List;
import java.util.Set;

/**
 * DISCLAIMER: ALL CODE HERE NOT FINAL, MAY ENCOUNTER BREAKING CHANGES REGULARLY
 * CROSS-COMPATIBILITY WILL BE IMPLEMENTED WHEN API STABILIZES; PLEASE BE PATIENT
 */
public interface TankComponent extends Component, Observable {
	int getTanks();

	Fraction getMaxCapacity(int tank);

	boolean isEmpty(); //TODO: default impl

	List<FluidVolume> getAllContents();

	FluidVolume getContents(int tank);

	boolean canInsert(int tank);

	boolean canExtract(int tank);

	FluidVolume takeFluid(int tank, Fraction amount, ActionType action);

	FluidVolume removeFluid(int tank, ActionType action);

	void setFluid(int tank, FluidVolume fluid);

	FluidVolume insertFluid(int tank, FluidVolume fluid, ActionType action);

	FluidVolume insertFluid(FluidVolume fluid, ActionType action);

	void clear(); //TODO: default impl

	boolean isAcceptableFluid(int tank); //TODO: default impl

	Fraction amountOf(Fluid fluid); //TODO: default impl

	Fraction amountOf(Set<Fluid> fluids); //TODO: default impl

	boolean contains(Fluid fluid); //TODO: default impl

	boolean contains(Set<Fluid> fluids); //TODO: default impl

	//TODO: default impl of toTag and fromTag
}
