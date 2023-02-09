package com.rdm.portalgun_reforged.common.items.base;

import software.bernie.geckolib3.network.ISyncable;

public abstract class AnimatableSyncableItem extends AnimatableItem implements ISyncable {

	public AnimatableSyncableItem(Properties pProperties) {
		super(pProperties);
	}
	
	@Override
	abstract public void onAnimationSync(int id, int state);

}
