package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.LevelSyncedComponent;

public class LevelSyncedTankComponent extends SimpleTankComponent implements LevelSyncedComponent {
	private ComponentType<?> type;

	public LevelSyncedTankComponent(int size, Fraction maxCapacity) {
		this(size, maxCapacity, UniversalComponents.TANK_COMPONENT);
	}

	public LevelSyncedTankComponent(int size, Fraction maxCapacity, ComponentType<?> type) {
		super(size, maxCapacity);
		this.type = type;
		listen(this::sync);
	}

	@Override
	public ComponentType<?> getComponentType() {
		return type;
	}
}
