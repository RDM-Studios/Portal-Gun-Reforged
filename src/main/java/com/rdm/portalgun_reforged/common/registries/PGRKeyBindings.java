package com.rdm.portalgun_reforged.common.registries;

import java.awt.event.KeyEvent;

import com.rdm.portalgun_reforged.PortalGunReforged;

import net.minecraft.client.settings.KeyBinding;

public class PGRKeyBindings {
	public static final KeyBinding REMOVE_OLD_PORTALS = createKeyBinding("remove_old_portals", KeyEvent.VK_R);
	public static final KeyBinding SWITCH_PORTAL_GUN_MODE = createKeyBinding("switch_portal_gun_mode", KeyEvent.VK_V);
	
	private static final KeyBinding createKeyBinding(String name, int key) {
		return new KeyBinding("key.".concat(PortalGunReforged.MODID).concat("." + name), key, "key.category.".concat(PortalGunReforged.MODID));
	}

}
