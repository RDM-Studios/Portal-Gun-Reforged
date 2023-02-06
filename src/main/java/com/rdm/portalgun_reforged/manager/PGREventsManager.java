package com.rdm.portalgun_reforged.manager;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.client.events.PGRClientSetupEvents;
import com.rdm.portalgun_reforged.common.events.PGRCommonSetupEvents;

import net.minecraftforge.eventbus.api.IEventBus;

public class PGREventsManager {
	
	public static void registerEvents() {
		registerClientEvents(PortalGunReforged.MOD_BUS, PortalGunReforged.FORGE_BUS);
		registerCommonEvents(PortalGunReforged.MOD_BUS, PortalGunReforged.FORGE_BUS);
		PortalGunReforged.FORGE_BUS.register(PortalGunReforged.INSTANCE);
	}
	
	private static void registerClientEvents(IEventBus modBus, IEventBus forgeBus) {
		// Setup
		modBus.addListener(PGRClientSetupEvents::onFMLClientSetupEvent);
		
		// Misc
	}
	
	private static void registerCommonEvents(IEventBus modBus, IEventBus forgeBus) {
		// Setup
		modBus.addListener(PGRCommonSetupEvents::onEntityAttributeCreation);
		modBus.addListener(PGRCommonSetupEvents::onFMLCommonSetup);
		modBus.addListener(PGRCommonSetupEvents::onGatherDataGen);
		
		// Misc
	}

}
