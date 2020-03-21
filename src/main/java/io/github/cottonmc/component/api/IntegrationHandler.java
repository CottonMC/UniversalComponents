package io.github.cottonmc.component.api;

import net.fabricmc.loader.api.FabricLoader;

public class IntegrationHandler {
	public static void runIfPresent(String modid, Runnable toRun) {
		if (FabricLoader.getInstance().isModLoaded(modid)) {
			toRun.run();
		}
	}
}
