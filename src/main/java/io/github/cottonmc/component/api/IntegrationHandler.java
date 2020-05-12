package io.github.cottonmc.component.api;

import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Supplier;

public class IntegrationHandler {
	public static void runIfPresent(String modid, Supplier<Runnable> toRun) {
		if (FabricLoader.getInstance().isModLoaded(modid)) {
			toRun.get().run();
		}
	}
}
