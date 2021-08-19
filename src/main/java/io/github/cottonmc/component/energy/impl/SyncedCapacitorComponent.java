package io.github.cottonmc.component.energy.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.energy.CapacitorComponent;
import io.github.cottonmc.component.energy.type.EnergyType;

public class SyncedCapacitorComponent<T extends ComponentProvider> extends SimpleCapacitorComponent implements AutoSyncedComponent {
    private final ComponentKey<? extends CapacitorComponent> key;
    private final T provider;

    public SyncedCapacitorComponent(int maxEnergy, EnergyType type, T provider) {
        this(maxEnergy, type, UniversalComponents.CAPACITOR_COMPONENT, provider);
    }

    public SyncedCapacitorComponent(int maxEnergy, EnergyType type, ComponentKey<? extends CapacitorComponent> key, T provider) {
        super(maxEnergy, type);
        this.key = key;
        this.provider = provider;

        this.listen(() -> getKey().sync(getProvider()));
    }

    public ComponentKey<? extends CapacitorComponent> getKey() {
        return key;
    }

    public T getProvider() {
        return provider;
    }
}
