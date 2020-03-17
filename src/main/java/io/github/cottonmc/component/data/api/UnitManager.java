package io.github.cottonmc.component.data.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.cottonmc.component.data.impl.BinaryUnit;
import io.github.cottonmc.component.data.impl.SIUnit;
import io.github.cottonmc.component.data.impl.SimpleUnit;
import io.github.cottonmc.component.data.impl.TicksUnit;
import net.minecraft.fluid.Fluid;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A central place for unit registrations. It's highly recommended to register your unit on both the client *and* the
 * server.
 */
//TODO: directly ripped from ProbeDataProvider, worth upgrading for Fabric customs?
public class UnitManager {
	private static UnitManager INSTANCE;

	//Fluids
	//TODO: whatever the hell is gonna go on with fluids in Fabric
	public static final Unit BUCKETS_ANY = new SIUnit("buckets", "B", 0x283593); //800 indigo
//	public static final Unit BUCKETS_WATER = new SIUnit("buckets_water", "B", 0x1976D2); //700 blue
//	public static final Unit BUCKETS_LAVA  = new SIUnit("buckets_lava", "B", 0xFF8F00); //800 amber

	//Data
	public static final Unit BYTES = new BinaryUnit("bytes", "B", 0x76FF03); //A400 light green

	//Energy, eventually

	//Temperature
	public static final Unit KELVIN = new SIUnit("kelvin", "°K", 0xFF0000); //Programmer Red

	public static final Unit PERCENT = new SimpleUnit("percent", "%", 0xAAAAAA, NumberFormat.getIntegerInstance(), false); // Terrified Grey

	//Time
	public static final Unit TICKS = new TicksUnit("ticks", 0xAAAAAA); // Terrified Grey

	public static UnitManager getInstance() {
		if (INSTANCE==null) INSTANCE = new UnitManager();
		return INSTANCE;
	}

	private Map<String, Unit> registry = new HashMap<>();
	private BiMap<Unit, Fluid> fluidUnits = HashBiMap.create();

	private UnitManager() {
		register(BUCKETS_ANY);

		register(BYTES);

		register(KELVIN);
		register(PERCENT);

		register(TICKS);
	}

	/**
	 * Register a unit with the dictionary.
	 * @param unit the unit to register.
	 */
	public void register(Unit unit) {
		registry.put(unit.getFullName(), unit);
	}

	/**
	 * Registers this unit as a fluid
	 * @param unit the unit to register
	 * @param fluid the fluid the unit is associated with
	 */
	public void register(Unit unit, Fluid fluid) {
		registry.put(unit.getFullName(), unit);
		fluidUnits.put(unit, fluid);
	}

	/**
	 * Finds the IUnit with the specified proper name
	 * @param fullName the name the IUnit was registered under
	 * @return the IUnit itself, or null if none was registered under that name.
	 */
	@Nullable
	public Unit getUnit(String fullName) {
		return registry.get(fullName);
	}

	/**
	 * Returns true if the specified unit corresponds to buckets of a forge Fluid
	 * @param unit the Unit to test
	 * @return true if there is a known association between this Unit and a Fluid
	 */
	public boolean isFluid(Unit unit) {
		return fluidUnits.containsKey(unit);
	}

	/**
	 * Finds the Fluid that the specified Unit is associated with
	 * @param unit the Unit to find a Fluid for
	 * @return the Fluid this Unit is associated with, or null if it isn't known to be a Fluid
	 */
	@Nullable
	public Fluid getFluid(Unit unit) {
		return fluidUnits.get(unit);
	}

	/**
	 * Finds the IUnit that corresponds to this Fluid, if it exists
	 * @param fluid the Fluid to get a unit for
	 * @return the IUnit this Fluid is associated with, or null if this Fluid doesn't have a unit yet.
	 */
	@Nullable
	public Unit getUnit(Fluid fluid) {
		return fluidUnits.inverse().get(fluid);
	}
}
