package com.rdm.portalgun_reforged.client.events;

import com.rdm.portalgun_reforged.common.items.PortalGunItem;
import com.rdm.portalgun_reforged.common.network.packet.PortalGunSwitchModePacket;
import com.rdm.portalgun_reforged.common.registries.PGRKeyBindings;
import com.rdm.portalgun_reforged.manager.PGRNetworkManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent;

public class PGRClientMiscEvents {
	
	public static void onInput(InputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity clientPlayer = mc.player;
		
		if (mc.screen != null) return;
		
		if (clientPlayer.getMainHandItem().getItem() instanceof PortalGunItem) {
			PortalGunItem portalGun = (PortalGunItem) clientPlayer.getMainHandItem().getItem();
			if (PGRKeyBindings.SWITCH_PORTAL_GUN_MODE.consumeClick()) {
				mc.gui.setOverlayMessage(new StringTextComponent("Current Mode: " + portalGun.getPortalGunMode().getName().toUpperCase()), false);
				PGRNetworkManager.sendPacketToServer(new PortalGunSwitchModePacket(portalGun));
			}
		}
	}

}
