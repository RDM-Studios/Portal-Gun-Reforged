package com.rdm.portalgun_reforged.common.network.packet.base;

import net.minecraft.network.PacketBuffer;

public abstract class BasePacket<T extends BasePacket<T>> {
	
	public abstract void encode(PacketBuffer buffer);
//	public abstract BasePacket decode(PacketBuffer buffer);
//	public abstract void handlePacket(T packet, Supplier<NetworkEvent.Context> ctx);
	
}
