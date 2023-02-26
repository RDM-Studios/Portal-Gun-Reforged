package com.rdm.portalgun_reforged.client.renderers.entity;

import com.rdm.portalgun_reforged.client.models.entity.OrangePortalEntityModel;
import com.rdm.portalgun_reforged.common.entities.OrangePortalEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class OrangePortalEntityRenderer extends GeoProjectilesRenderer<OrangePortalEntity> {

	public OrangePortalEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new OrangePortalEntityModel());
	}

}
