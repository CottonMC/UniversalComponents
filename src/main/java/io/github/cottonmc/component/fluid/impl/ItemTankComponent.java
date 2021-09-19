package io.github.cottonmc.component.fluid.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import net.minecraft.nbt.NbtCompound;

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
		NbtCompound tag = new NbtCompound();
		NbtCompound tag1 = new NbtCompound();
		that.writeToNbt(tag);
		this.writeToNbt(tag1);
		return tag1.equals(tag1);
	}

	public ComponentKey<TankComponent> getComponentKey() {
		return type;
	}
}
