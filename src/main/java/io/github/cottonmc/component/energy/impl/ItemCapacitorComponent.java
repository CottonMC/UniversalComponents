package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.Component;
import nerdhub.cardinal.components.api.util.ItemComponent;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

public class ItemCapacitorComponent extends SimpleCapacitorComponent implements Component {
	private ComponentKey<CapacitorComponent> key;

	public ItemCapacitorComponent(int maxEnergy, EnergyType type) {
		this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT);
	}

	public ItemCapacitorComponent(int maxEnergy, EnergyType type, ComponentKey<CapacitorComponent> key) {
		super(maxEnergy, type);
		this.key = key;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemCapacitorComponent that = (ItemCapacitorComponent) o;
		CompoundTag tag = new CompoundTag();
		CompoundTag tag1 = new CompoundTag();
		that.writeToNbt(tag);
		this.writeToNbt(tag1);
		return tag1.equals(tag1);
	}

	public ComponentKey<CapacitorComponent> getComponentKey() {
		return key;
	}
}
