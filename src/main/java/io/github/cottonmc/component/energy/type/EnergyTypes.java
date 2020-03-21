package io.github.cottonmc.component.energy.type;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.impl.WUEnergyType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Predefined energy types for work units
 */
public class EnergyTypes {
	/** Base level. Coal-powered or early-game machines. Enough WU/t to smelt at 4x speed. */
	public static final EnergyType ULTRA_LOW_VOLTAGE = register("ultra_low_voltage", new WUEnergyType(4));
	/** Low-level. Coal coke powered, first industrial machines. */
	public static final EnergyType LOW_VOLTAGE = register("low_voltage", new WUEnergyType(16));
	/** Mid-level. Steam powered, medium-scale automation. */
	public static final EnergyType MEDIUM_VOLTAGE = register("medium_voltage", new WUEnergyType(64));
	/** High-level. Liquid fuel powered, large-scale machines. */
	public static final EnergyType HIGH_VOLTAGE = register("high_voltage", new WUEnergyType(256));
	/** Endgame. Uranium or higher, epic-scale industrial setups or high-voltage power lines. */
	public static final EnergyType ULTRA_HIGH_VOLTAGE = register("ultra_high_voltage", new WUEnergyType(1024));

	public static void init() { }

	private static EnergyType register(String name, EnergyType type) {
		return Registry.register(UniversalComponents.ENERGY_TYPES, new Identifier(UniversalComponents.MODID, name), type);
	}
}
