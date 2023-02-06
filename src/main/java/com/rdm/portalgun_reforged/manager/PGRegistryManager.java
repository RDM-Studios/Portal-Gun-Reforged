package com.rdm.portalgun_reforged.manager;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.registries.PGRItems;
import com.rdm.portalgun_reforged.common.registries.PGRSoundEvents;

public class PGRegistryManager {
	
	public static void registerRegistries() {
		PGRItems.PGR_ITEMS.register(PortalGunReforged.MOD_BUS);
		PGRSoundEvents.PGR_SOUND_EVENTS.register(PortalGunReforged.MOD_BUS);
	}

}