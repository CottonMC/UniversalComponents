package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.util.ItemComponent;
import net.minecraft.nbt.CompoundTag;

public class ItemCapacitorComponent extends SimpleCapacitorComponent implements ItemComponent<CapacitorComponent> {
	private ComponentType<CapacitorComponent> componentType;

	public ItemCapacitorComponent(int maxEnergy, EnergyType type) {
		this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT);
	}

	public ItemCapacitorComponent(int maxEnergy, EnergyType type, ComponentType<CapacitorComponent> componentType) {
		super(maxEnergy, type);
		this.componentType = componentType;
	}

	@Override
	public boolean isComponentEqual(Component component) {
		return component.toTag(new CompoundTag()).equals(this.toTag(new CompoundTag()));
	}

	@Override
	public ComponentType<CapacitorComponent> getComponentType() {
		return componentType;
	}
}
