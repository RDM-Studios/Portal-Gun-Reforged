package com.rdm.portalgun_reforged.common.items;

import com.rdm.portalgun_reforged.common.items.base.AnimatableSyncableItem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PortalGunItem extends AnimatableSyncableItem {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private AnimationController<PortalGunItem> mainController = new AnimationController<PortalGunItem>(this, getControllerName(), getTransitioningTicks(), this::mainPredicate);
	private byte portalGunState = 0;
	private Mode portalGunMode = Mode.NORMAL;
	
	public PortalGunItem(Properties pProperties) {
		super(pProperties);
		GeckoLibNetwork.registerSyncable(this);
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
		
	}
	
	public byte getPortalGunState() {
		return portalGunState;
	}
	
	public void setPortalGunState(byte state) {
		this.portalGunState = state;
	}
	
	public Mode getPortalGunMode() {
		return portalGunMode;
	}
	
	public void setPortalGunMode(Mode mode) {
		this.portalGunMode = mode;
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return true;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack portalGunStack = pPlayer.getMainHandItem();
		
		
		pPlayer.getCooldowns().addCooldown(this, 10);
		pPlayer.awardStat(Stats.ITEM_USED.get(this));
		return ActionResult.pass(portalGunStack);
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
	}
}
