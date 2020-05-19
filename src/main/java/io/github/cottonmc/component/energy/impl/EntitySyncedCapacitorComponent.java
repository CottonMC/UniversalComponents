package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.type.EnergyType;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;
import net.minecraft.entity.Entity;

public class EntitySyncedCapacitorComponent extends SimpleCapacitorComponent implements EntitySyncedComponent {
	private ComponentType<?> componentType;
	private Entity entity;

	public EntitySyncedCapacitorComponent(int maxEnergy, EnergyType type, Entity entity) {
		this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT, entity);
	}

	public EntitySyncedCapacitorComponent(int maxEnergy, EnergyType type, ComponentType<?> componentType, Entity entity) {
		super(maxEnergy, type);
		this.componentType = componentType;
		this.entity = entity;
		listen(this::sync);
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public ComponentType<?> getComponentType() {
		return componentType;
	}
}
