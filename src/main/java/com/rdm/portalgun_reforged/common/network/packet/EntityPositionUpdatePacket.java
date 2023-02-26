package com.rdm.portalgun_reforged.common.network.packet;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.rdm.portalgun_reforged.common.items.PortalGunItem;
import com.rdm.portalgun_reforged.common.network.packet.base.BasePacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

public class EntityPositionUpdatePacket extends BasePacket<EntityPositionUpdatePacket> {
	private final int targetID;
	private final BlockPos targetPos;
	
	public EntityPositionUpdatePacket(int target, BlockPos targetPos) {
		this.targetID = target;
		this.targetPos = targetPos;
	}
	
	@Override
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(targetID);
		buffer.writeBlockPos(targetPos);
	}
	
	public static EntityPositionUpdatePacket decode(PacketBuffer buffer) {
		return new EntityPositionUpdatePacket(buffer.readInt(), buffer.readBlockPos());
	}
	
	public static boolean handlePacket(EntityPositionUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity owner = ctx.get().getSender();
			World world = owner.level;			
			@Nullable 
			Entity target = world.getEntity(packet.targetID);
			
			if (owner != null && owner.isAlive() && !owner.isSpectator()) {
				if (owner.getMainHandItem().getItem() instanceof PortalGunItem) {
					PortalGunItem portalGun = (PortalGunItem) owner.getMainHandItem().getItem();
				//	assert (portalGun.getPortalGunMode().equals(Mode.CARRY_ENTITY)) && (portalGun.getPortalGunState() == (byte) 1 || portalGun.getPortalGunState() == (byte) 2);
					
					if (target != null && target instanceof LivingEntity && target.isAlive()) {
						target.setNoGravity(portalGun.getLiftedEntities().contains(target) && portalGun.getPortalGunState() != (byte) 0);
						portalGun.moveToUntil((LivingEntity) target, packet.targetPos.getX(), packet.targetPos.getY(), packet.targetPos.getZ(), (portalGun.getPortalGunState() != (byte) 1 && portalGun.getPortalGunState() != (byte) 2) || !portalGun.getLiftedEntities().contains(target) || owner.getMainHandItem().getItem() != portalGun);
						((LivingEntity) target).addEffect(new EffectInstance(Effects.GLOWING, 20, 1));
					}
				}
			}
		//	PortalGunReforged.LOGGER.debug("Packet Sent and Recieved!");
		});
		
		ctx.get().setPacketHandled(true);
		return true;
	}

}
