package com.rdm.portalgun_reforged.manager;

public class PGRModManager {
	
	public static void registerAll() {
		PGREventsManager.registerEvents();
		PGRegistryManager.registerRegistries();
	}

}
