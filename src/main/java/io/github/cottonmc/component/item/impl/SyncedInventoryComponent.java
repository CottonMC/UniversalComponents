package io.github.cottonmc.component.item.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.cottonmc.component.UniversalComponents;
import io.github.cottonmc.component.item.InventoryComponent;

public class SyncedInventoryComponent<T extends ComponentProvider> extends SimpleInventoryComponent implements AutoSyncedComponent {
    private final ComponentKey<? extends InventoryComponent> key;
    private final T provider;

    public SyncedInventoryComponent(int slots, T provider) {
        this(slots, UniversalComponents.INVENTORY_COMPONENT, provider);
    }

    public SyncedInventoryComponent(int slots, ComponentKey<? extends InventoryComponent> key, T provider) {
        super(slots);
        this.key = key;
        this.provider = provider;

        this.listen(() -> getKey().sync(getProvider()));
    }

    public ComponentKey<? extends InventoryComponent> getKey() {
        return key;
    }

    public T getProvider() {
        return provider;
    }
}
