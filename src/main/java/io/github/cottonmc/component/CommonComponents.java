package io.github.cottonmc.component;

import io.github.cottonmc.component.data.DataProviderComponent;
import io.github.cottonmc.component.item.InventoryComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonComponents implements ModInitializer {
	public static final String MODID = "commoncomponents";

	public static final Logger logger = LogManager.getLogger();

	/**
	 * An inventory-handling component, so you can handle inventory with all your other components.
	 */
	public static final ComponentType<InventoryComponent> INVENTORY_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "inventory"), InventoryComponent.class);

	public static final ComponentType<DataProviderComponent> DATA_PROVIDER_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "data_provider"), DataProviderComponent.class);

	@Override
	public void onInitialize() {

	}
}
