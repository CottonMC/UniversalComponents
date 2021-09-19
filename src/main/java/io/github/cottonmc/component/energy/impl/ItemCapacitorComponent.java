package io.github.cottonmc.component.energy.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import net.minecraft.nbt.NbtCompound;

public class ItemCapacitorComponent extends SimpleCapacitorComponent {
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
		NbtCompound tag = new NbtCompound();
		NbtCompound tag1 = new NbtCompound();
		that.writeToNbt(tag);
		this.writeToNbt(tag1);
		return tag1.equals(tag1);
	}

	public ComponentKey<CapacitorComponent> getComponentKey() {
		return key;
	}
}
