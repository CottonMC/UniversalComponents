package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.api.ActionType;
import io.github.cottonmc.component.energy.CapacitorComponent;
import team.reborn.energy.EnergySide;
import team.reborn.energy.EnergyStorage;
import team.reborn.energy.EnergyTier;

public class EnergyStorageWrapper implements EnergyStorage {
	private CapacitorComponent component;

	public EnergyStorageWrapper(CapacitorComponent component) {
		this.component = component;
	}

	@Override
	public double getStored(EnergySide energySide) {
		return component.getCurrentEnergy();
	}

	@Override
	public void setStored(double v) {
		component.extractEnergy(component.getPreferredType(), component.getCurrentEnergy(), ActionType.PERFORM);
		component.insertEnergy(component.getPreferredType(), (int) v, ActionType.PERFORM);
	}

	@Override
	public double getMaxStoredPower() {
		return component.getMaxEnergy();
	}

	@Override
	public EnergyTier getTier() {
		return RebornEnergyTypes.getEquivalentTier(component.getPreferredType());
	}
}
