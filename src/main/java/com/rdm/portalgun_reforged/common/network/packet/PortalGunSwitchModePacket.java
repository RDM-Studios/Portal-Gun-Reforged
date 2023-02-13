package com.rdm.portalgun_reforged.common.network.packet;

import java.util.function.Supplier;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.items.PortalGunItem;
import com.rdm.portalgun_reforged.common.network.packet.base.BasePacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PortalGunSwitchModePacket extends BasePacket<PortalGunSwitchModePacket> {
	
	public PortalGunSwitchModePacket() {
	}

	@Override
	public void encode(PacketBuffer buffer) {
	}

	public static PortalGunSwitchModePacket decode(PacketBuffer buffer) {
		return new PortalGunSwitchModePacket();
	}

	public static boolean handlePacket(PortalGunSwitchModePacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity serverPlayer = ctx.get().getSender();
			
			if (serverPlayer.getMainHandItem().getItem() instanceof PortalGunItem) {
				PortalGunItem portalGun = (PortalGunItem) serverPlayer.getMainHandItem().getItem();
				portalGun.switchMode();
			}
			
			PortalGunReforged.LOGGER.debug("Packet Sent and Recieved!");
		});
		
		ctx.get().setPacketHandled(true);
		return true;
	}

}
