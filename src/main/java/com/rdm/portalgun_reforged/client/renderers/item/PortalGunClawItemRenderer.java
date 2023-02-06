package com.rdm.portalgun_reforged.client.renderers.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.client.models.item.PortalGunClawItemModel;
import com.rdm.portalgun_reforged.common.items.PortalGunClawItem;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PortalGunClawItemRenderer extends GeoItemRenderer<PortalGunClawItem> {
	private static final ResourceLocation TEXTURE_LOCATION = PortalGunReforged.prefixRL("textures/item/portal_device_claw.png");

	public PortalGunClawItemRenderer() {
		super(new PortalGunClawItemModel());
	}
	
	@Override
	public void renderByItem(ItemStack itemStack, TransformType pTransformType, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayLightIn) {
		super.renderByItem(itemStack, pTransformType, matrixStack, bufferIn, combinedLightIn, combinedOverlayLightIn);
	}
	
	@Override
	public ResourceLocation getTextureLocation(PortalGunClawItem instance) {
		return TEXTURE_LOCATION;
	}
}
