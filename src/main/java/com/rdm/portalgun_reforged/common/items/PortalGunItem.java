package com.rdm.portalgun_reforged.common.items;

import com.rdm.portalgun_reforged.common.items.base.AnimatableItem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PortalGunItem extends AnimatableItem {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private AnimationController<PortalGunItem> mainController = new AnimationController<PortalGunItem>(this, getControllerName(), getTransitioningTicks(), this::mainPredicate);

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
	public <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
		
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
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
		return true;
	}
	
	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
		return false;
	}
	
	@Override
	public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
		ItemStack portalGunStack = pPlayer.getMainHandItem();
		
		
		return ActionResult.pass(portalGunStack);
	}

}
