package com.rdm.portalgun_reforged.common.items;

import com.rdm.portalgun_reforged.common.items.base.AnimatableItem;

import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class PortalGunBodyItem extends AnimatableItem {
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private AnimationController<PortalGunBodyItem> mainController = new AnimationController<PortalGunBodyItem>(this, getControllerName(), getTransitioningTicks(), this::mainPredicate);

	public PortalGunBodyItem(Properties pProperties) {
		super(pProperties);
	}
	
	@Override
	protected String getControllerName() {
		return "portalGunBodyController";
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
		return 0;
	}

}
