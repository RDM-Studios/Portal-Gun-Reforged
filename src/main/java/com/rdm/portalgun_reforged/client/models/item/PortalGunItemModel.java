package com.rdm.portalgun_reforged.client.models.item;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.items.PortalGunItem;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PortalGunItemModel extends AnimatedGeoModel<PortalGunItem> {
	private static final ResourceLocation ANIMATION_LOCATION = PortalGunReforged.prefixRL("animations/aperture_science_handheld_dual-portal_device.animation.json");
	private static final ResourceLocation MODEL_LOCATION = PortalGunReforged.prefixRL("geo/aperture_science_handheld_dual-portal_device.geo.json");
	private static final ResourceLocation TEXTURE_LOCATION = PortalGunReforged.prefixRL("textures/item/aperture_science_handheld_dual-portal_device.png");
	
	@Override
	public ResourceLocation getAnimationFileLocation(PortalGunItem animatable) {
		return ANIMATION_LOCATION;
	}

	@Override
	public ResourceLocation getModelLocation(PortalGunItem object) {
		return MODEL_LOCATION;
	}

	@Override
	public ResourceLocation getTextureLocation(PortalGunItem object) {
		return TEXTURE_LOCATION;
	}

}
