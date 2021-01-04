package io.github.cottonmc.component.fluid.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.fluid.TankComponent;
import io.github.fablabsmc.fablabs.api.fluidvolume.v1.Fraction;

public class SyncedTankComponent<T extends ComponentProvider> extends SimpleTankComponent implements AutoSyncedComponent {
    private final ComponentKey<? extends TankComponent> key;
    private final T provider;

    public SyncedTankComponent(int size, Fraction capacity, T provider) {
        this(size, capacity, UniversalComponents.TANK_COMPONENT, provider);
    }

    public SyncedTankComponent(int size, Fraction capacity, ComponentKey<? extends TankComponent> key, T provider) {
        super(size, capacity);
        this.key = key;
        this.provider = provider;

        this.listen(() -> getKey().sync(getProvider()));
    }

    public ComponentKey<? extends TankComponent> getKey() {
        return key;
    }

    public T getProvider() {
        return provider;
    }
}
