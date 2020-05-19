package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.util.ItemComponent;
import net.minecraft.nbt.CompoundTag;

public class ItemCapacitorComponent extends SimpleCapacitorComponent implements ItemComponent<CapacitorComponent> {
	private ComponentType<CapacitorComponent> type;

	public ItemCapacitorComponent(int maxEnergy) {
		this(maxEnergy, UniversalComponents.CAPACITOR_COMPONENT);
	}

	public ItemCapacitorComponent(int maxEnergy, ComponentType<CapacitorComponent> type) {
		super(maxEnergy);
		this.type = type;
	}

	@Override
	public boolean isComponentEqual(Component component) {
		return component.toTag(new CompoundTag()).equals(this.toTag(new CompoundTag()));
	}

	@Override
	public ComponentType<CapacitorComponent> getComponentType() {
		return type;
	}
}
