package com.rdm.portalgun_reforged.common.network.packet;

import java.util.function.Supplier;

import com.rdm.portalgun_reforged.common.items.PortalGunItem;
import com.rdm.portalgun_reforged.common.items.PortalGunItem.Mode;
import com.rdm.portalgun_reforged.common.network.packet.base.BasePacket;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PortalGunSwitchModePacket extends BasePacket<PortalGunSwitchModePacket> {
	private final PortalGunItem portalGun;
	
	public PortalGunSwitchModePacket(PortalGunItem portalGun) {
		this.portalGun = portalGun;
	}

	@Override
	public void encode(PacketBuffer buffer) {
		buffer.writeItem(portalGun.getDefaultInstance());
	}

	public static PortalGunSwitchModePacket decode(PacketBuffer buffer) {
		return new PortalGunSwitchModePacket((PortalGunItem) buffer.readItem().getItem());
	}

	public static void handlePacket(PortalGunSwitchModePacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			PortalGunItem.Mode portalGunMode = packet.portalGun.getPortalGunMode();
			
			//TODO Inefficient, needs refactoring
			packet.portalGun.setPortalGunMode(portalGunMode.equals(Mode.NORMAL) ? Mode.CARRY_ENTITY : Mode.NORMAL);
		});
		
		ctx.get().setPacketHandled(true);
	}

}
