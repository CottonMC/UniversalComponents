package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.energy.impl.ElectricalEnergyType;
import team.reborn.energy.EnergyTier;

public class RebornEnergyType extends ElectricalEnergyType {
	private EnergyTier tier;

	public RebornEnergyType(EnergyTier tier) {
		this.tier = tier;
	}

	@Override
	public float getEnergyPerFuelTick() {
		return 2.5f;
	}

	@Override
	public int getMaximumTransferSize() {
		return tier.getMaxOutput();
	}

	@Override
	public String getDisplaySubkey() {
		return "teamreborn.energy";
	}

	public EnergyTier getTier() {
		return tier;
	}
}
