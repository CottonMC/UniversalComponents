package io.github.cottonmc.component.api;

import net.fabricmc.loader.api.FabricLoader;

public class IntegrationHandler {
	//TODO: figure out why this doesn't actually work - probably because it has to load the class to set up the lambda
	public static void runIfPresent(String modid, Runnable toRun) {
		if (FabricLoader.getInstance().isModLoaded(modid)) {
			toRun.run();
		}
	}
}
