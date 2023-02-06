package com.rdm.portalgun_reforged.common.items;

import com.rdm.portalgun_reforged.common.items.base.AnimatableItem;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PortalGunClawItem extends AnimatableItem {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private AnimationController<PortalGunClawItem> mainController = new AnimationController<PortalGunClawItem>(this, getControllerName(), getTransitioningTicks(), this::mainPredicate);

	public PortalGunClawItem(Properties pProperties) {
		super(pProperties);
	}
	
	@Override
	protected String getControllerName() {
		return "portalGunClawController";
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
		return PlayState.CONTINUE;
	}

	@Override
	protected int getTransitioningTicks() {
		return 1;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(mainController);		
	}
}
