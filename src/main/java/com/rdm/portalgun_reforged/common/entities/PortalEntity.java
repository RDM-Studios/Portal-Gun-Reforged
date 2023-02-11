package com.rdm.portalgun_reforged.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PortalEntity extends Entity {

	public PortalEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {		
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {		
	}
	
	public boolean isValidSpawnPos() {
		return true;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}