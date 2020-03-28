package io.github.cottonmc.component;

import io.github.cottonmc.component.api.IntegrationHandler;
import io.github.cottonmc.component.compat.tr.RebornEnergyType;
import io.github.cottonmc.component.compat.tr.RebornEnergyTypes;
import io.github.cottonmc.component.data.DataProviderComponent;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import io.github.cottonmc.component.energy.type.EnergyTypes;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UniversalComponents implements ModInitializer {
	public static final String MODID = "universalcomponents";

	public static final Logger logger = LogManager.getLogger();

	/**
	 * An item-handling component, so you can handle inventory with all your other components.
	 */
	public static final ComponentType<InventoryComponent> INVENTORY_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "inventory"), InventoryComponent.class);
	/**
	 * A fluid-handling component, for dealing with fluid stacks.
	 */
	public static final ComponentType<TankComponent> TANK_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "tank"), TankComponent.class);
	/**
	 * An energy-handling component,
	 */
	public static final ComponentType<CapacitorComponent> CAPACITOR_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "capacitor"), CapacitorComponent.class);
	/**
	 * A component for providing structured data, for use with automation or HUDs.
	 */
	public static final ComponentType<DataProviderComponent> DATA_PROVIDER_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "data_provider"), DataProviderComponent.class);

	public static final Registry<EnergyType> ENERGY_TYPES = new DefaultedRegistry<>("universalcomponents:ultra_low_voltage");

	@Override
	public void onInitialize() {
		EnergyTypes.init();
		if (FabricLoader.getInstance().isModLoaded("team_reborn_energy")) {
			RebornEnergyTypes.init();
		}
	}
}
