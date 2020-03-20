package io.github.cottonmc.component.util;

import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Supplier;

public class IntegrationHandler {
	public static void runIfPresent(String modid, Runnable toRun) {
		if (FabricLoader.getInstance().isModLoaded(modid)) {
			toRun.run();
		}
	}
}
