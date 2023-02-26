package com.rdm.portalgun_reforged.common.entities;

import java.util.Optional;

import javax.annotation.Nullable;

import com.rdm.portalgun_reforged.common.config.PGRCommonConfig;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class BluePortalEntity extends Entity implements IAnimatable {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private static final DataParameter<Integer> TICK_COUNT = EntityDataManager.defineId(BluePortalEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> AMOUNT_TELEPORTED_ENTITIES = EntityDataManager.defineId(BluePortalEntity.class, DataSerializers.INT);
	private static final DataParameter<Optional<BlockPos>> DESTINATION_POS = EntityDataManager.defineId(BluePortalEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
	private final IntArrayList teleportedEntities = new IntArrayList();
	
	public BluePortalEntity(EntityType<?> pType, World pLevel) {
		super(pType, pLevel);
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(TICK_COUNT, tickCount);
		this.entityData.define(AMOUNT_TELEPORTED_ENTITIES, 0);
		this.entityData.define(DESTINATION_POS, Optional.ofNullable(BlockPos.ZERO));
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundNBT pCompound) {
		setTimeAlive(pCompound.getInt("timeAlive"));
//		if (pCompound.getUUID("portalUUID") != null) setUUID(pCompound.getUUID("portalUUID"));
		setAmountOfEntitiesTeleported(pCompound.getInt("amountOfTeleportedEntities"));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT pCompound) {
		pCompound.putInt("timeAlive", getTimeAlive());
//		if (getUUID() != null) pCompound.putUUID("portalUUID", getUUID());
		pCompound.putInt("amountOfTeleportedEntities", getAmountOfEntitiesTeleported());
		
	}
	
	public int getTimeAlive() {
		return this.entityData.get(TICK_COUNT);
	}
	
	public void setTimeAlive(int timeAlive) {
		this.entityData.set(TICK_COUNT, timeAlive);
	}
	
	public int getAmountOfEntitiesTeleported() {
		return this.entityData.get(AMOUNT_TELEPORTED_ENTITIES);
	}
	
	public void setAmountOfEntitiesTeleported(int amountTeleported) {
		this.entityData.set(AMOUNT_TELEPORTED_ENTITIES, amountTeleported);
	}
	
	public void incrementAmountOfEntitiesTeleported() {
		setAmountOfEntitiesTeleported(getAmountOfEntitiesTeleported() + 1);
	}
	
	public void incrementAmountOfEntitiesTeleported(int amount) {
		setAmountOfEntitiesTeleported(getAmountOfEntitiesTeleported() + amount);
	}
	
	public void decrementAmountOfEntitiesTeleported() {
		setAmountOfEntitiesTeleported(getAmountOfEntitiesTeleported() - 1);
	}
	
	public void decrementAmountOfEntitiesTeleported(int amount) {
		setAmountOfEntitiesTeleported(getAmountOfEntitiesTeleported() - amount);
	}
	
	public void resetAmountOfEntitiesTeleported() {
		setAmountOfEntitiesTeleported(0);
	}
	
	@Nullable
	public BlockPos getDestinationPos() {
		return this.entityData.get(DESTINATION_POS).orElse(null);
	}
	
	public void setDestinationPos(BlockPos destPos) {
		this.entityData.set(DESTINATION_POS, Optional.of(destPos));
	}
	
	public boolean isValidSpawnPos(BlockPos targetPos) {
		return true;
	}
	
	@Override
	public void playerTouch(PlayerEntity pEntity) {
		if (getDestinationPos() != null) {
			if (pEntity.isAlive() && pEntity != null && isValidDestinationPos(getDestinationPos())) pEntity.moveTo(getDestinationPos(), pEntity.yRot, pEntity.xRot);
		}
	}
	
	public boolean isValidDestinationPos(BlockPos to) {
		BlockPos origin = blockPosition();
		
		if (to.getY() < 0) return false;
		if (!level.isAreaLoaded(origin, 5) || !level.isAreaLoaded(to, 5)) return false;
		if (origin.distSqr(to.getX(), to.getY(), to.getZ(), true) > PGRCommonConfig.COMMON.portalTeleportationRange.get()) return false;
		
		return true;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public void setDeltaMovement(double pX, double pY, double pZ) {
	}
	
	@Override
	public Vector3d getDeltaMovement() {
		return Vector3d.ZERO;
	}

	@Override
	public void registerControllers(AnimationData data) {
		
	}
	
	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		return false;
	}
	
	@Override
	public boolean causeFallDamage(float pFallDistance, float pDamageMultiplier) {
		return false;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

}