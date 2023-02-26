package com.rdm.portalgun_reforged.client.renderers.entity;

import com.rdm.portalgun_reforged.client.models.entity.BluePortalEntityModel;
import com.rdm.portalgun_reforged.common.entities.BluePortalEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BluePortalEntityRenderer extends GeoProjectilesRenderer<BluePortalEntity> {

	public BluePortalEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new BluePortalEntityModel());
	}

}
