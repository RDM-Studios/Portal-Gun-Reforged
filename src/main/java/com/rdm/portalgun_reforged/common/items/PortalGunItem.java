package com.rdm.portalgun_reforged.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.config.PGRCommonConfig;
import com.rdm.portalgun_reforged.common.entities.BluePortalEntity;
import com.rdm.portalgun_reforged.common.entities.OrangePortalEntity;
import com.rdm.portalgun_reforged.common.items.base.AnimatableSyncableItem;
import com.rdm.portalgun_reforged.common.network.packet.EntityPositionUpdatePacket;
import com.rdm.portalgun_reforged.manager.PGRNetworkManager;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PortalGunItem extends AnimatableSyncableItem {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private AnimationController<PortalGunItem> mainController = new AnimationController<PortalGunItem>(this, getControllerName(), getTransitioningTicks(), this::mainPredicate);
	private Mode portalGunMode = Mode.NORMAL;
	private final CompoundNBT portalGunModeNBT = new CompoundNBT();
	private final ObjectArrayList<LivingEntity> liftedEntities = new ObjectArrayList<LivingEntity>();
	private BluePortalEntity bluePortal;
	private OrangePortalEntity orangePortal;

	public PortalGunItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	protected String getControllerName() {
		return "portalGunController";
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(mainController);
	}

	@Override
	public <E extends Item & IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	protected int getTransitioningTicks() {
		return 1;
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == 0x0) {
			final AnimationController<PortalGunItem> controller = GeckoLibUtil.getControllerForID(this.factory, id, getControllerName());
			assert controller != null;
			if (controller != null) {
				if (controller.getAnimationState() == AnimationState.Stopped) {
					controller.markNeedsReload();
					controller.setAnimation(new AnimationBuilder().addAnimation("portal_shoot", EDefaultLoopTypes.PLAY_ONCE));
				}
			}
		}
	}

	private void playSyncableAnimation(LivingEntity owner, Hand hand) {
		if (!owner.level.isClientSide) {
			final ItemStack stack = owner.getItemInHand(hand);
			final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) owner.level);
			final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> owner);
			GeckoLibNetwork.syncAnimation(target, this, id, 0x0);
		}
	}

	public CompoundNBT getModeNBT() {
		return portalGunModeNBT;
	}

	@Nullable
	public BluePortalEntity getFirstPortal() {
		return null;
	}

	@Nullable
	public BluePortalEntity getSecondPortal() {
		return null;
	}

	public byte getPortalGunState() {
		return portalGunMode.getState();
	}

	public void setPortalGunState(byte state) {
		portalGunMode.setState(state);
	}

	public void resetPortalGunState() {
		portalGunMode.setState((byte) 0);
	}

	public Mode getPortalGunMode() {
		return portalGunMode;
	}

	public void setPortalGunMode(Mode mode) {
		this.portalGunMode = mode;
	}

	public void resetPortalGunMode() {
		this.portalGunMode = Mode.NORMAL;
	}

	public void switchMode() {
		setPortalGunMode(getPortalGunMode().equals(Mode.NORMAL) ? Mode.CARRY_ENTITY : Mode.NORMAL); 
	}

	public RemovalMode getPortalGunEntityRemovalMode() {
		return PGRCommonConfig.COMMON.entityRemovalMode.get();
	}

	public ObjectArrayList<LivingEntity> getLiftedEntities() {
		return liftedEntities;
	}

	public int getAmountOfLiftedEntities() {
		return liftedEntities.size();
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return true;
	}

	@Override
	public boolean canAttackBlock(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer) {
		return false;
	}

	@Override
	public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
		return false;
	}

	public void resetPortals() {
		setPortalGunState((byte) 0);
	}

	@Override
	public boolean onEntitySwing(ItemStack portalGunStack, LivingEntity owner) {
		if (getPortalGunMode().equals(Mode.NORMAL)) {
			
			if (owner instanceof PlayerEntity) ((PlayerEntity) owner).getCooldowns().addCooldown(this, 4);			
		} else if (getPortalGunMode().equals(Mode.CARRY_ENTITY)) {
			if (owner instanceof PlayerEntity) {
				if (handleRemoveLiftedEntities(portalGunStack, (PlayerEntity) owner)) {
					playSyncableAnimation((PlayerEntity) owner, Hand.MAIN_HAND);
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		return ActionResultType.FAIL;
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack portalGunStack = pPlayer.getMainHandItem();

		if (getPortalGunMode().equals(Mode.NORMAL)) {

		} else if (getPortalGunMode().equals(Mode.CARRY_ENTITY)) {
			handleAddLiftingEntities(portalGunStack, pPlayer);
		}

		pPlayer.getCooldowns().addCooldown(this, 4);
		pPlayer.awardStat(Stats.ITEM_USED.get(this));
		return ActionResult.pass(portalGunStack);
	}

	@Override
	public void appendHoverText(ItemStack pStack, World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
		super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

		pTooltip.add(new StringTextComponent("Current Mode: ")
				.withStyle(TextFormatting.DARK_AQUA)
				.append(new StringTextComponent(getPortalGunMode().getName().toUpperCase())
						.withStyle(getPortalGunMode().equals(Mode.NORMAL) ? TextFormatting.AQUA : TextFormatting.GOLD).withStyle(TextFormatting.BOLD)));
	}

	public void spawnPortal(Mode portalGunMode, byte portalModeState) {

	}

	public void removePortals() {

	}

	private boolean handleAddLiftingEntities(ItemStack portalGunStack, PlayerEntity pPlayer) {
		double validReach = (pPlayer.getAttributeBaseValue(ForgeMod.REACH_DISTANCE.get()) * pPlayer.getAttributeBaseValue(ForgeMod.REACH_DISTANCE.get())) * 2;

		Vector3d viewVec = pPlayer.getViewVector(1.0F);
		Vector3d eyeVec = pPlayer.getEyePosition(1.0F);
		Vector3d targetVec = eyeVec.add(viewVec.x * validReach, viewVec.y * validReach, viewVec.z * validReach);

		AxisAlignedBB targetBB = pPlayer.getBoundingBox().expandTowards(viewVec.scale(validReach)).inflate(4.0D, 4.0D, 4.0D);
		EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(pPlayer.level, pPlayer, eyeVec, targetVec, targetBB, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);

		if (result == null || !(result.getEntity() instanceof LivingEntity) || result.getType() != RayTraceResult.Type.ENTITY) return false;

		LivingEntity target = (LivingEntity) result.getEntity();

		double distanceToTargetSqr = pPlayer.distanceToSqr(target);

		boolean isValidResult = (result != null ? target : null) != null && result.getType() == RayTraceResult.Type.ENTITY;
		if (!canLiftEntity(target)) return false;

		if (isValidResult) {
			if (validReach >= distanceToTargetSqr) {
				if (getAmountOfLiftedEntities() > 1) setPortalGunState((byte) 2);
				else setPortalGunState((byte) 1);
				if (!liftedEntities.contains(target) && getAmountOfLiftedEntities() < 10) {
					liftedEntities.add(target);

					portalGunModeNBT.putUUID("targetEntityLifted" + liftedEntities.indexOf(target), target.getUUID());
					portalGunModeNBT.putInt("liftedEntitiesAmount", getAmountOfLiftedEntities());

					PortalGunReforged.LOGGER.debug("[MOVED ENTITIES AMOUNT]: " + getAmountOfLiftedEntities());
				}
			}
		}

		// Extra check
		if (liftedEntities.isEmpty() || getPortalGunMode() != Mode.CARRY_ENTITY || pPlayer.getMainHandItem() != portalGunStack) setPortalGunState((byte) 0);
		return true;
	}

	private boolean handleRemoveLiftedEntities(ItemStack portalGunStack, PlayerEntity pPlayer) {
		if (getPortalGunEntityRemovalMode().equals(RemovalMode.CONSECUTIVE)) {
			if (portalGunStack == null) return false;
			if (!liftedEntities.isEmpty()) {
				LivingEntity target = liftedEntities.get(liftedEntities.size() - 1);

				if (target == null) return false;

				PGRNetworkManager.sendPacketToServer(new EntityPositionUpdatePacket(target.getId(), target.blockPosition()));
				portalGunModeNBT.remove("targetEntityLifted" + liftedEntities.indexOf(target));
				liftedEntities.remove(liftedEntities.size() - 1);
				PortalGunReforged.LOGGER.debug("[MOVED ENTITIES AMOUNT]: " + getAmountOfLiftedEntities());
			}
		} else if (getPortalGunEntityRemovalMode().equals(RemovalMode.POINT_AT)) {
			double validReach = (pPlayer.getAttributeBaseValue(ForgeMod.REACH_DISTANCE.get()) * pPlayer.getAttributeBaseValue(ForgeMod.REACH_DISTANCE.get())) * 2;

			Vector3d viewVec = pPlayer.getViewVector(1.0F);
			Vector3d eyeVec = pPlayer.getEyePosition(1.0F);
			Vector3d targetVec = eyeVec.add(viewVec.x * validReach, viewVec.y * validReach, viewVec.z * validReach);

			AxisAlignedBB targetBB = pPlayer.getBoundingBox().expandTowards(viewVec.scale(validReach)).inflate(4.0D, 4.0D, 4.0D);
			EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(pPlayer.level, pPlayer, eyeVec, targetVec, targetBB, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);

			if (result == null || !(result.getEntity() instanceof LivingEntity) || result.getType() != RayTraceResult.Type.ENTITY) return false;

			LivingEntity target = (LivingEntity) result.getEntity();

			boolean isValidResult = (result != null ? target : null) != null && result.getType() == RayTraceResult.Type.ENTITY;
			if (!liftedEntities.contains(target)) return false;

			if (isValidResult) {
				liftedEntities.remove(target);
				PGRNetworkManager.sendPacketToServer(new EntityPositionUpdatePacket(target.getId(), target.blockPosition()));
				PortalGunReforged.LOGGER.debug("[MOVED ENTITIES AMOUNT]: " + getAmountOfLiftedEntities());
			}
		}
		// Account for extra cases
		if (!liftedEntities.isEmpty()) {			
			for (int i = 0; i < liftedEntities.size() -1; i++) {
				LivingEntity target = liftedEntities.get(i);

				if (target.isDeadOrDying() || target == null || !target.isAlive()) {
					PGRNetworkManager.sendPacketToServer(new EntityPositionUpdatePacket(target.getId(), target.blockPosition()));
					if (portalGunModeNBT.contains("targetEntityLifted" + liftedEntities.indexOf(target))) portalGunModeNBT.remove("targetEntityLifted" + liftedEntities.indexOf(target));
					liftedEntities.remove(i);
				}
			}
		}
		if (liftedEntities.isEmpty()) setPortalGunState((byte) 0);
		return true;
	}

	private boolean canLiftEntity(LivingEntity target) {
		boolean sizeCheck = target.getBbWidth() < 3.0F || target.getBbHeight() < 4.125F;
		return sizeCheck;
	}

	@Override
	public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
		super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);

		if (pEntity.tickCount % 20 == 0) {
			PortalGunReforged.LOGGER.debug("+-----------------------------------------------+");
			PortalGunReforged.LOGGER.debug("[MODE]: " + getPortalGunMode());
			PortalGunReforged.LOGGER.debug("[STATE]: " + getPortalGunState());
			PortalGunReforged.LOGGER.debug("+-----------------------------------------------+");
		}

		if (pEntity instanceof PlayerEntity) {
			PlayerEntity owner = (PlayerEntity) pEntity;

			if (owner.getMainHandItem() != pStack || !(owner.getMainHandItem().getItem() instanceof PortalGunItem) || !owner.inventory.contains(pStack) || !owner.isAlive() || owner.isSpectator() || getPortalGunMode() != Mode.CARRY_ENTITY) {
				setPortalGunState((byte) 0);
				if (!liftedEntities.isEmpty()) {
					for (LivingEntity target : liftedEntities) {
						PGRNetworkManager.sendPacketToServer(new EntityPositionUpdatePacket(target.getId(), target.blockPosition()));
					}
					liftedEntities.clear();
				}
			}

			if (owner.getMainHandItem() == pStack) {
				if (!liftedEntities.isEmpty()) {
					RayTraceResult targetRayTraceResult = Minecraft.getInstance().getCameraEntity().pick(10.0F, 0.0F, false);
					Vector3d resultPos = targetRayTraceResult.getLocation();
					BlockPos targetBlockPos = new BlockPos(resultPos.x, resultPos.y, resultPos.z);

					for (LivingEntity t : liftedEntities) {
						if (t != null) {
							PGRNetworkManager.sendPacketToServer(new EntityPositionUpdatePacket(t.getId(), targetBlockPos));
						}
					}
				}
			}
		}

	}

	private void removeAllPortals() {
		if (portalGunModeNBT.contains("firstPortalUUID")) portalGunModeNBT.remove("firstPortalUUID");
		if (portalGunModeNBT.contains("secondPortalUUID")) portalGunModeNBT.remove("secondPortalUUID");
	}

	public void moveToUntil(LivingEntity target, double x, double y, double z, boolean condition) {
		if (!condition) target.moveTo(x, y, z);
	}

	public enum Mode {
		NORMAL("normal", (byte) 0),
		CARRY_ENTITY("carry_entity", (byte) 0);

		private final String name;
		private byte state;

		Mode(String name, byte state) {
			this.name = name;
			this.state = state;
		}

		public String getName() {
			return name;
		}

		public byte getState() {
			return state;
		}

		public void setState(byte state) {
			this.state = state;
		}
	}

	public enum RemovalMode {
		CONSECUTIVE("Consecutively remove entities in the order they were picked up in."),
		POINT_AT("Remove entities directly within the line of sight of the portal gun user.");

		private final String description;

		RemovalMode(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
}
