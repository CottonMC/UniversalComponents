package io.github.cottonmc.component.mixin.vanilla;

import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(Registry.class)
public interface RegistryAccessor {

    @Invoker("create")
    static <T, R extends MutableRegistry<T>> R create(RegistryKey<Registry<T>> registryKey, R registry, Supplier<T> defaultEntry) {
        throw new UnsupportedOperationException("mixin was not transformed");
    }
}
