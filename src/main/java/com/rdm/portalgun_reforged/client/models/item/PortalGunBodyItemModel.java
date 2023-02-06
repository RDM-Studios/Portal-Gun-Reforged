package com.rdm.portalgun_reforged.client.models.item;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.items.PortalGunBodyItem;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PortalGunBodyItemModel extends AnimatedGeoModel<PortalGunBodyItem> {
	private static final ResourceLocation MODEL_LOCATION = PortalGunReforged.prefixRL("geo/portal_device_body.geo.json");
	private static final ResourceLocation TEXTURE_LOCATION = PortalGunReforged.prefixRL("textures/item/portal_device_body.png");

	@Override
	public ResourceLocation getAnimationFileLocation(PortalGunBodyItem animatable) {
		return null;
	}

	@Override
	public ResourceLocation getModelLocation(PortalGunBodyItem object) {
		return MODEL_LOCATION;
	}

	@Override
	public ResourceLocation getTextureLocation(PortalGunBodyItem object) {
		return TEXTURE_LOCATION;
	}

}
