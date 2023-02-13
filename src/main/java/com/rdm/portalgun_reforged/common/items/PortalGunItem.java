package com.rdm.portalgun_reforged.common.items;

import java.util.List;

import com.rdm.portalgun_reforged.common.entities.PortalEntity;
import com.rdm.portalgun_reforged.common.items.base.AnimatableSyncableItem;
import com.rdm.portalgun_reforged.common.registries.PGREntityTypes;
import com.rdm.portalgun_reforged.common.registries.PGRSoundEvents;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.SoundCategory;
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
	private final CompoundNBT portalGunModeNBT = getDefaultInstance().getOrCreateTag();
	private final ObjectArrayList<LivingEntity> liftedEntities = new ObjectArrayList<LivingEntity>();

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
	public boolean onEntitySwing(ItemStack portalGunStack, LivingEntity owner) {
		playSyncableAnimation(owner, Hand.MAIN_HAND);
		owner.level.playSound((PlayerEntity) null, owner.blockPosition(), PGRSoundEvents.PORTAL_SHOOT_1.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
		if (owner instanceof PlayerEntity) ((PlayerEntity) owner).getCooldowns().addCooldown(this, 4);
		return true;
	}
	
	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack portalGunStack = pPlayer.getMainHandItem();

		if (getPortalGunMode().equals(Mode.NORMAL) && !portalGunModeNBT.contains("secondPortalUUID")) {
	//		setPortalGunState((byte) 2);
			playSyncableAnimation(pPlayer, pHand);
			PortalEntity portal = (PortalEntity) PGREntityTypes.PORTAL.get().create(pLevel);
	//		portalGunModeNBT.putUUID("secondPortalUUID", portal.getUUID());
			pLevel.playSound(pPlayer, pPlayer.blockPosition(), PGRSoundEvents.PORTAL_SHOOT_2.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
			
		} else if (getPortalGunMode().equals(Mode.CARRY_ENTITY)) handleLiftingEntities(portalGunStack, pPlayer);
		
//		portalGunStack.setTag(portalGunModeNBT);
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
	
	private boolean handleLiftingEntities(ItemStack portalGunStack, PlayerEntity pPlayer) {
		double validReach = (pPlayer.getAttributeBaseValue(ForgeMod.REACH_DISTANCE.get()) * pPlayer.getAttributeBaseValue(ForgeMod.REACH_DISTANCE.get())) * 2;

		Vector3d viewVec = pPlayer.getViewVector(1.0F);
		Vector3d eyeVec = pPlayer.getEyePosition(1.0F);
		Vector3d targetVec = eyeVec.add(viewVec.x * validReach, viewVec.y * validReach, viewVec.z * validReach);

		AxisAlignedBB bb = pPlayer.getBoundingBox().expandTowards(viewVec.scale(validReach)).inflate(4.0D, 4.0D, 4.0D);
		EntityRayTraceResult result = ProjectileHelper.getEntityHitResult(pPlayer.level, pPlayer, eyeVec, targetVec, bb, EntityPredicates.NO_CREATIVE_OR_SPECTATOR);

		if (result == null || !(result.getEntity() instanceof LivingEntity) || result.getType() != RayTraceResult.Type.ENTITY) return false;

		LivingEntity target = (LivingEntity) result.getEntity();

		double distanceToTargetSqr = pPlayer.distanceToSqr(target);

		boolean isValidResult = (result != null ? target : null) != null && result.getType() == RayTraceResult.Type.ENTITY;

		if (isValidResult) {
			if (validReach >= distanceToTargetSqr) {
				if (getAmountOfLiftedEntities() > 1) setPortalGunState((byte) 2);
				else setPortalGunState((byte) 1);
				if (!liftedEntities.contains(target)) liftedEntities.add(target);
				portalGunModeNBT.putUUID("targetEntityLifted" + liftedEntities.indexOf(target), target.getUUID());
				portalGunModeNBT.putInt("liftedEntitiesAmount", getAmountOfLiftedEntities());
			}
		}
		
		return true;
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
}
