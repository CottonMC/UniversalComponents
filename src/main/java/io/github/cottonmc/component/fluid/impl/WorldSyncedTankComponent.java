package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.WorldSyncedComponent;
import net.minecraft.world.World;

public class WorldSyncedTankComponent extends SimpleTankComponent implements WorldSyncedComponent {
	private ComponentType<?> type;
	private World world;

	public WorldSyncedTankComponent(int size, Fraction maxCapacity, World world) {
		this(size, maxCapacity, UniversalComponents.TANK_COMPONENT, world);
	}


	public WorldSyncedTankComponent(int size, Fraction maxCapacity, ComponentType<?> type, World world) {
		super(size, maxCapacity);
		this.type = type;
		this.world = world;
		listen(this::sync);
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public ComponentType<?> getComponentType() {
		return type;
	}
}
