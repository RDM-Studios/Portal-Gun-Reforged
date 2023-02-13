package com.rdm.portalgun_reforged.manager;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.network.packet.PortalGunSwitchModePacket;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PGRNetworkManager {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(PortalGunReforged.prefixRL("channel"), () ->
			      PROTOCOL_VERSION, 
			      PROTOCOL_VERSION::equals,
			      PROTOCOL_VERSION::equals);

	public static void registerPackets() {
		int id = 0;
		registerCTSPackets(id);
		registerSTCPackets(id);
		PortalGunReforged.LOGGER.debug("Registered packets!");
	}
	
	private static void registerCTSPackets(int id) {
		CHANNEL.messageBuilder(PortalGunSwitchModePacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
			.decoder(PortalGunSwitchModePacket::decode)
		    .encoder(PortalGunSwitchModePacket::encode)
		    .consumer(PortalGunSwitchModePacket::handlePacket)
			.add();
	}
	
	private static void registerSTCPackets(int id) {
		
	}

	public static void sendPacketToServer(Object packet) {
		CHANNEL.sendToServer(packet);
	}
	
	public static void sendPacketToClient(Object packet) {
		CHANNEL.sendTo(packet, Minecraft.getInstance().getConnection().getConnection(), NetworkDirection.PLAY_TO_CLIENT);
	}
}
