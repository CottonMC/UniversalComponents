package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.util.ItemComponent;
import net.minecraft.nbt.CompoundTag;

public class ItemTankComponent extends SimpleTankComponent implements ItemComponent<TankComponent> {
	private ComponentType<TankComponent> type;

	public ItemTankComponent(int size, Fraction maxCapacity) {
		this(size, maxCapacity, UniversalComponents.TANK_COMPONENT);
	}

	public ItemTankComponent(int size, Fraction maxCapacity, ComponentType<TankComponent> type) {
		super(size, maxCapacity);
		this.type = type;
	}

	@Override
	public boolean isComponentEqual(Component component) {
		return component.toTag(new CompoundTag()).equals(this.toTag(new CompoundTag())); //TODO: do this better?
	}

	@Override
	public ComponentType<TankComponent> getComponentType() {
		return type;
	}
}
