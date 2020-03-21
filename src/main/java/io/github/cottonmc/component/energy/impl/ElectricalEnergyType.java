package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.energy.type.EnergyType;
import net.minecraft.text.TranslatableText;

public abstract class ElectricalEnergyType implements EnergyType {
	/**
	 * @return How much energy is consumed to perform one tick of smelting in a vanilla furnace. Formula: (amount of energy from one piece of coal) / 1600.
	 */
	public abstract float getEnergyPerFuelTick();

	public float getScaledTransferSize() {
		return getMaximumTransferSize() / getEnergyPerFuelTick();
	}

	public abstract String getDisplaySubkey();

	@Override
	public TranslatableText getDisplayAmount(int amount) {
		if (amount < 1000) { // x < 1K
			return new TranslatableText("info." + getDisplaySubkey() + ".amount", amount);
		}
		else if (amount < 1_000_000) { // 1K < x < 1M
			float tAmount = amount / 1000f;
			return new TranslatableText("info." + getDisplaySubkey() + ".amount.k", tAmount);
		}
		else if (amount < 1_000_000_000) { // 1M < x < 1G
			float tAmount = amount / 1_000_1000f;
			return new TranslatableText("info." + getDisplaySubkey() + ".amount.m", tAmount);
		}
		else { // 1G < x
			float tAmount = amount / 1_000_000_000f;
			return new TranslatableText("info." + getDisplaySubkey() + ".amount.g", tAmount);
		}
	}

	@Override
	public boolean isCompatibleWith(EnergyType type) {
		return type instanceof ElectricalEnergyType;
	}

	@Override
	public boolean isHarmful(EnergyType type) {
		if (type instanceof ElectricalEnergyType) {
			ElectricalEnergyType electric = (ElectricalEnergyType)type;
			return electric.getScaledTransferSize() <= getMaximumTransferSize();
		}

		return true; //Other energy type isn't electrical; we can't deal with it.
	}

	@Override
	public int convertFrom(EnergyType type, int amount) {
		if (type instanceof ElectricalEnergyType) {
			ElectricalEnergyType electrical = ((ElectricalEnergyType)type);
			float proportion = getEnergyPerFuelTick() / electrical.getEnergyPerFuelTick();
			return Math.round((float)amount * proportion);
		}
		return 0;
	}

	@Override
	public int convertTo(EnergyType type, int amount) {
		if (type instanceof ElectricalEnergyType) {
			ElectricalEnergyType electrical = ((ElectricalEnergyType)type);
			float proportion = electrical.getEnergyPerFuelTick() / getEnergyPerFuelTick();
			return Math.round((float)amount * proportion);
		}
		return 0;
	}
}
