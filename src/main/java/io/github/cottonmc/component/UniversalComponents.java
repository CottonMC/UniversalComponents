package io.github.cottonmc.component;

import com.mojang.serialization.Lifecycle;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import io.github.cottonmc.component.api.IntegrationHandler;
import io.github.cottonmc.component.compat.tr.RebornEnergyTypes;
import io.github.cottonmc.component.data.DataProviderComponent;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.item.InventoryComponent;
import io.github.cottonmc.component.mixin.vanilla.RegistryAccessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UniversalComponents implements ModInitializer {
	public static final String MODID = "universalcomponents";

	public static final Logger logger = LogManager.getLogger();

	/**
	 * An item-handling component, so you can handle inventory with all your other components.
	 */
	public static final ComponentKey<InventoryComponent> INVENTORY_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(MODID, "inventory"), InventoryComponent.class);
	/**
	 * A fluid-handling component, for dealing with fluid volumes.
	 */
	public static final ComponentKey<TankComponent> TANK_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(MODID, "tank"), TankComponent.class);
	/**
	 * An energy-handling component,
	 */
	public static final ComponentKey<CapacitorComponent> CAPACITOR_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(MODID, "capacitor"), CapacitorComponent.class);
	/**
	 * A component for providing structured data, for use with automation or HUDs.
	 */
	public static final ComponentKey<DataProviderComponent> DATA_PROVIDER_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(MODID, "data_provider"), DataProviderComponent.class);

	public static final RegistryKey<Registry<EnergyType>> ENERGY_TYPES_REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(MODID, "energy_types"));
	public static final Registry<EnergyType> ENERGY_TYPES = RegistryAccessor.create(ENERGY_TYPES_REGISTRY_KEY, new DefaultedRegistry<>(new Identifier(MODID, "ultra_low_voltage").toString(), ENERGY_TYPES_REGISTRY_KEY, Lifecycle.stable()), () -> EnergyTypes.ULTRA_LOW_VOLTAGE, Lifecycle.stable());

	@Override
	public void onInitialize() {
		EnergyTypes.init();
		IntegrationHandler.runIfPresent("team_reborn_energy", () -> RebornEnergyTypes::init);
	}
}
