package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.type.EnergyType;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.LevelSyncedComponent;

public class LevelSyncedCapacitorComponent extends SimpleCapacitorComponent implements LevelSyncedComponent {
	private ComponentType<?> componentType;

	public LevelSyncedCapacitorComponent(int maxEnergy, EnergyType type) {
		this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT);
	}

	public LevelSyncedCapacitorComponent(int maxEnergy, EnergyType type, ComponentType<?> componentType) {
		super(maxEnergy, type);
		this.componentType = componentType;
		listen(this::sync);
	}

	@Override
	public ComponentType<?> getComponentType() {
		return componentType;
	}
}
