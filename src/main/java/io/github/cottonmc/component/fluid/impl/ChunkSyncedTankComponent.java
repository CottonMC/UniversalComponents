package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.ChunkSyncedComponent;
import net.minecraft.world.chunk.Chunk;

public class ChunkSyncedTankComponent extends SimpleTankComponent implements ChunkSyncedComponent<TankComponent> {
	private ComponentType<TankComponent> type;
	private Chunk chunk;

	public ChunkSyncedTankComponent(int size, Fraction maxCapacity, Chunk chunk) {
		this(size, maxCapacity, UniversalComponents.TANK_COMPONENT, chunk);
	}

	public ChunkSyncedTankComponent(int size, Fraction maxCapacity, ComponentType<TankComponent> type, Chunk chunk) {
		super(size, maxCapacity);
		this.type = type;
		this.chunk = chunk;
		listen(this::sync);
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public ComponentType<TankComponent> getComponentType() {
		return type;
	}
}
