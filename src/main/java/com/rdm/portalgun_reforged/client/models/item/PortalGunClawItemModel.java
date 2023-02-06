package com.rdm.portalgun_reforged.client.models.item;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.items.PortalGunClawItem;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PortalGunClawItemModel extends AnimatedGeoModel<PortalGunClawItem> {
	private static final ResourceLocation MODEL_LOCATION = PortalGunReforged.prefixRL("geo/portal_device_claw.geo.json");
	private static final ResourceLocation TEXTURE_LOCATION = PortalGunReforged.prefixRL("textures/item/portal_device_claw.png");

	@Override
	public ResourceLocation getAnimationFileLocation(PortalGunClawItem animatable) {
		return null;
	}

	@Override
	public ResourceLocation getModelLocation(PortalGunClawItem object) {
		return MODEL_LOCATION;
	}

	@Override
	public ResourceLocation getTextureLocation(PortalGunClawItem object) {
		return TEXTURE_LOCATION;
	}

}
