package com.rdm.portalgun_reforged.client.models.entity;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.entities.OrangePortalEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OrangePortalEntityModel extends AnimatedGeoModel<OrangePortalEntity> {
	private static final ResourceLocation MODEL_LOCATION = PortalGunReforged.prefixRL("geo/portal.geo.json");
	private static final ResourceLocation ORANGE_PORTAL_TEXTURE_LOCATION = PortalGunReforged.prefixRL("textures/entity/portal_orange.png");

	@Override
	public ResourceLocation getAnimationFileLocation(OrangePortalEntity animatable) {
		return null;
	}

	@Override
	public ResourceLocation getModelLocation(OrangePortalEntity object) {
		return MODEL_LOCATION;
	}

	@Override
	public ResourceLocation getTextureLocation(OrangePortalEntity object) {
		return ORANGE_PORTAL_TEXTURE_LOCATION;
	}

}
