package com.rdm.portalgun_reforged.manager;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.config.PGRCommonConfig;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class PGRConfigManager {
	
	public static void registerConfigs() {
		registerClientConfig();
		registerCommonConfig();
		registerServerConfig();
		PortalGunReforged.LOGGER.debug("Registered configs!");
	}
	
	
	private static void registerClientConfig() {
		
	}
	
	private static void registerCommonConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PGRCommonConfig.COMMON_SPEC);
	}
	
	private static void registerServerConfig() {
		
	}
	
	private static void registerConfigFolder() {
		
	}

}
