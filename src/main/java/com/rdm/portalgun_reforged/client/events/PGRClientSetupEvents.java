package com.rdm.portalgun_reforged.client.events;

import com.rdm.portalgun_reforged.client.renderers.entity.BluePortalEntityRenderer;
import com.rdm.portalgun_reforged.client.renderers.entity.OrangePortalEntityRenderer;
import com.rdm.portalgun_reforged.common.registries.PGREntityTypes;
import com.rdm.portalgun_reforged.common.registries.PGRKeyBindings;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class PGRClientSetupEvents {
	
	public static void onFMLClientSetup(final FMLClientSetupEvent event) {
		//Register custom renderers
		RenderingRegistry.registerEntityRenderingHandler(PGREntityTypes.BLUE_PORTAL.get(), BluePortalEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(PGREntityTypes.ORANGE_PORTAL.get(), OrangePortalEntityRenderer::new);
		
		//Register custom keybinds
		ClientRegistry.registerKeyBinding(PGRKeyBindings.REMOVE_OLD_PORTALS);
		ClientRegistry.registerKeyBinding(PGRKeyBindings.SWITCH_PORTAL_GUN_MODE);
	}

}
