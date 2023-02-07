package com.rdm.portalgun_reforged.client.events;

import com.rdm.portalgun_reforged.common.registries.PGRKeyBindings;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PGRClientSetupEvents {
	
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
		//Register custom keybinds
		ClientRegistry.registerKeyBinding(PGRKeyBindings.REMOVE_OLD_PORTALS);
		ClientRegistry.registerKeyBinding(PGRKeyBindings.SWITCH_PORTAL_GUN_MODE);
	}

}
