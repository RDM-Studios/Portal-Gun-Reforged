package com.rdm.portalgun_reforged.manager;

public class PGRModManager {
	
	public static void registerAll() {
		PGRConfigManager.registerConfigs();
		PGREventsManager.registerEvents();
		PGRegistryManager.registerRegistries();
	}

}
