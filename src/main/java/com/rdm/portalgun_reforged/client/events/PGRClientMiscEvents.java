package com.rdm.portalgun_reforged.client.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;

public class PGRClientMiscEvents {
	
	public static void onInputEvent(InputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		
		if (mc.screen != null) return;
		
	}

}
