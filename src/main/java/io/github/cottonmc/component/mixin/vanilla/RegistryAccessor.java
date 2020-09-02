package io.github.cottonmc.component.mixin.vanilla;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Supplier;

@Mixin(Registry.class)
public interface RegistryAccessor {

    @Invoker("create")
    static <T, R extends MutableRegistry<T>> R create(RegistryKey<Registry<T>> registryKey, R registry, Supplier<T> defaultEntry, Lifecycle lifecycle) {
        throw new UnsupportedOperationException("mixin was not transformed");
    }
}
