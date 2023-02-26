package com.rdm.portalgun_reforged.client.models.entity;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.entities.BluePortalEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BluePortalEntityModel extends AnimatedGeoModel<BluePortalEntity> {
	private static final ResourceLocation MODEL_LOCATION = PortalGunReforged.prefixRL("geo/portal.geo.json");
	private static final ResourceLocation BLUE_PORTAL_TEXTURE_LOCATION = PortalGunReforged.prefixRL("textures/entity/portal_blue.png");

	@Override
	public ResourceLocation getAnimationFileLocation(BluePortalEntity animatable) {
		return null;
	}

	@Override
	public ResourceLocation getModelLocation(BluePortalEntity object) {
		return MODEL_LOCATION;
	}

	@Override
	public ResourceLocation getTextureLocation(BluePortalEntity object) {
		return BLUE_PORTAL_TEXTURE_LOCATION;
	}

}
