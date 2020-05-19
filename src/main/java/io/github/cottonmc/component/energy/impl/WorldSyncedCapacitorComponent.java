package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.type.EnergyType;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.WorldSyncedComponent;
import net.minecraft.world.World;

public class WorldSyncedCapacitorComponent extends SimpleCapacitorComponent implements WorldSyncedComponent {
	private ComponentType<?> componentType;
	private World world;

	public WorldSyncedCapacitorComponent(int maxEnergy, EnergyType type, World world) {
		this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT, world);
	}

	public WorldSyncedCapacitorComponent(int maxEnergy, EnergyType type, ComponentType<?> componentType, World world) {
		super(maxEnergy, type);
		this.componentType = componentType;
		this.world = world;
		listen(this::sync);
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public ComponentType<?> getComponentType() {
		return componentType;
	}
}
