package io.github.cottonmc.component.energy.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.ChunkSyncedComponent;
import net.minecraft.world.chunk.Chunk;

public class ChunkSyncedCapacitorComponent extends SimpleCapacitorComponent implements ChunkSyncedComponent<CapacitorComponent> {
	private ComponentType<CapacitorComponent> componentType;
	private Chunk chunk;

	public ChunkSyncedCapacitorComponent(int maxEnergy, EnergyType type, Chunk chunk) {
		this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT, chunk);
	}

	public ChunkSyncedCapacitorComponent(int maxEnergy, EnergyType type, ComponentType<CapacitorComponent> componentType, Chunk chunk) {
		super(maxEnergy, type);
		this.componentType = componentType;
		this.chunk = chunk;
		listen(this::sync);
	}

	@Override
	public Chunk getChunk() {
		return chunk;
	}

	@Override
	public ComponentType<CapacitorComponent> getComponentType() {
		return componentType;
	}
}
