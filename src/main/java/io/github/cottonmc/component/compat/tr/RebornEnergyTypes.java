package io.github.cottonmc.component.compat.tr;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.type.EnergyType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.EnergyTier;

public class RebornEnergyTypes {
	//TODO: better way to go from energy type to tier and vice versa?
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
}
