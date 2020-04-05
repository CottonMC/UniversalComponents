package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.type.EnergyType;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.EnergyTier;

public class RebornEnergyTypes {
	public static final EnergyType MICRO_TIER = register("micro_tier", new RebornEnergyType(EnergyTier.MICRO));
	public static final EnergyType LOW_TIER = register("low_tier", new RebornEnergyType(EnergyTier.LOW));
	public static final EnergyType MEDIUM_TIER = register("medium_tier", new RebornEnergyType(EnergyTier.MEDIUM));
	public static final EnergyType HIGH_TIER = register("high_tier", new RebornEnergyType(EnergyTier.HIGH));
	public static final EnergyType EXTREME_TIER = register("extreme_tier", new RebornEnergyType(EnergyTier.EXTREME));
	public static final EnergyType INSANE_TIER = register("insane_tier", new RebornEnergyType(EnergyTier.INSANE));
	public static final EnergyType INFINITE_TIER = register("infinite_tier", new RebornEnergyType(EnergyTier.INFINITE));

	public static void init() { }

	private static EnergyType register(String name, EnergyType type) {
		return Registry.register(UniversalComponents.ENERGY_TYPES, new Identifier("tech_reborn_energy", name), type);
	}

	public static EnergyType getEquivalentType(EnergyTier tier) {
		switch (tier) {
			case MICRO:
				return MICRO_TIER;
			case LOW:
				return LOW_TIER;
			case MEDIUM:
				return MEDIUM_TIER;
			case HIGH:
				return HIGH_TIER;
			case EXTREME:
				return EXTREME_TIER;
			case INSANE:
				return INSANE_TIER;
			default:
				return INFINITE_TIER;
		}
	}

	public static EnergyTier getEquivalentTier(EnergyType type) {
		if (type instanceof RebornEnergyType) return ((RebornEnergyType)type).getTier();
		if (type == EnergyTypes.ULTRA_LOW_VOLTAGE) return EnergyTier.MICRO;
		if (type == EnergyTypes.LOW_VOLTAGE) return EnergyTier.LOW;
		if (type == EnergyTypes.MEDIUM_VOLTAGE) return EnergyTier.MEDIUM;
		if (type == EnergyTypes.HIGH_VOLTAGE) return EnergyTier.HIGH;
		if (type == EnergyTypes.ULTRA_HIGH_VOLTAGE) return EnergyTier.EXTREME;
		return EnergyTier.INFINITE;
	}
}
