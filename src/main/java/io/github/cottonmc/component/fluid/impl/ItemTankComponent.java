package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.impl.ItemCapacitorComponent;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.Component;
import nerdhub.cardinal.components.api.util.ItemComponent;
import net.minecraft.nbt.CompoundTag;

public class ItemTankComponent extends SimpleTankComponent {
	private ComponentKey<TankComponent> type;

	public ItemTankComponent(int size, Fraction maxCapacity) {
		this(size, maxCapacity, UniversalComponents.TANK_COMPONENT);
	}

	public ItemTankComponent(int size, Fraction maxCapacity, ComponentKey<TankComponent> type) {
		super(size, maxCapacity);
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemTankComponent that = (ItemTankComponent) o;
		CompoundTag tag = new CompoundTag();
		CompoundTag tag1 = new CompoundTag();
		that.writeToNbt(tag);
		this.writeToNbt(tag1);
		return tag1.equals(tag1);
	}

	public ComponentKey<TankComponent> getComponentKey() {
		return type;
	}
}
