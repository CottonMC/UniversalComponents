package io.github.cottonmc.component.fluid.impl;

import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.util.sync.EntitySyncedComponent;
import net.minecraft.entity.Entity;

/**
 * Synced tank component for adding fluid storage onto an entity.
 */
public class EntitySyncedTankComponent extends SimpleTankComponent implements EntitySyncedComponent {
	private ComponentType<TankComponent> type;
	private Entity entity;

	/**
	 * Create an entity tank component that works with {@link io.github.cottonmc.component.fluid.TankComponentHelper} access methods.
	 * @param size How many slots this tank should have.
	 * @param maxCapacity The max amount of fluid allowed in a single slot.
	 * @param entity The entity this tank is on.
	 */
	public EntitySyncedTankComponent(int size, Fraction maxCapacity, Entity entity) {
		this(size, maxCapacity, UniversalComponents.TANK_COMPONENT, entity);
	}

	/**
	 * Create a custom entity tank component accessible through your own component type.
	 * @param size How many slots this tank should have.
	 * @param maxCapacity The max amount of fluid allowed in a single slot.
	 * @param type The component type this should be accessed with.
	 * @param entity The entity this tank is on.
	 */
	public EntitySyncedTankComponent(int size, Fraction maxCapacity, ComponentType<TankComponent> type, Entity entity) {
		super(size, maxCapacity);
		this.type = type;
		this.entity = entity;
		listen(this::sync);
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public ComponentType<?> getComponentType() {
		return type;
	}
}
