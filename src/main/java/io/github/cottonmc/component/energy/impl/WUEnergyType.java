package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.energy.type.EnergyType;

/**
 * Electrical energy type using Work Units - 1 WU == 1 fuel tick
 */
public class WUEnergyType extends ElectricalEnergyType {

	private final int maximum;

	public WUEnergyType(int maximum) {
		this.maximum = maximum;
	}

	@Override
	public int getMaximumTransferSize() {
		return maximum;
	}

	@Override
	public String getDisplaySubkey() {
		return "unicomp.wu";
	}

	@Override
	public float getEnergyPerFuelTick() {
		return 1;
	}

	@Override
	public int convertFrom(EnergyType type, int amount) {
		if (type instanceof ElectricalEnergyType) return Math.round((float)amount / ((ElectricalEnergyType)type).getEnergyPerFuelTick());
		return 0;
	}

	@Override
	public int convertTo(EnergyType type, int amount) {
		if (type instanceof ElectricalEnergyType) return Math.round(amount * ((ElectricalEnergyType)type).getEnergyPerFuelTick());
		return 0;
	}
}
