package com.rdm.portalgun_reforged.common.items.base;

import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AnimatableItem extends Item implements IAnimatable {

	public AnimatableItem(Properties pProperties) {
		super(pProperties);
	}

	@Override
	abstract public void registerControllers(AnimationData data);
	
	abstract public <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event);

	@Override
	abstract public AnimationFactory getFactory();
	
	abstract protected int getTransitioningTicks();
	
	protected String getControllerName() {
		String regName = getDescriptionId();
		
		char[] charSet = regName.toLowerCase().toCharArray();
		boolean found = false;
		
		for (int i = 0; i < charSet.length - 1; i++) {
			if (!found && Character.isLetter(charSet[i])) {
				charSet[i] = Character.toUpperCase(charSet[i]);
				found = true;
			} else if (Character.isWhitespace(charSet[i]) || charSet[i] == '.' || charSet[i] == '_') {
				found = false;
			}
		}
		
		String baseResult = String.valueOf(charSet);		
		String prunedRegistryName = baseResult.substring(regName.lastIndexOf(".") + 1).replaceAll("_", "");
		
		return prunedRegistryName.concat("Controller").replace(prunedRegistryName.charAt(0), Character.toLowerCase(prunedRegistryName.charAt(0)));
	}
	
}
