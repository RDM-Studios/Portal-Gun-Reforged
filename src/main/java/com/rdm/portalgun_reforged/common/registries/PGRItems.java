package com.rdm.portalgun_reforged.common.registries;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.client.renderers.item.PortalGunBodyItemRenderer;
import com.rdm.portalgun_reforged.client.renderers.item.PortalGunClawItemRenderer;
import com.rdm.portalgun_reforged.client.renderers.item.PortalGunItemRenderer;
import com.rdm.portalgun_reforged.common.items.PortalGunBodyItem;
import com.rdm.portalgun_reforged.common.items.PortalGunClawItem;
import com.rdm.portalgun_reforged.common.items.PortalGunItem;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PGRItems {
	public static final DeferredRegister<Item> PGR_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PortalGunReforged.MODID);
	
	public static final RegistryObject<PortalGunItem> PORTAL_GUN = registerItem("aperture_science_handheld_dual-portal_device", new PortalGunItem(new Properties().tab(PGRItemGroups.PGR_ITEMS).stacksTo(1).rarity(Rarity.EPIC).fireResistant().setISTER(() -> PortalGunItemRenderer::new)));
	public static final RegistryObject<PortalGunClawItem> PORTAL_DEVICE_CLAW = registerItem("portal_device_claw", new PortalGunClawItem(new Properties().tab(PGRItemGroups.PGR_ITEMS).stacksTo(1).rarity(Rarity.RARE).fireResistant().setISTER(() -> PortalGunClawItemRenderer::new)));
	public static final RegistryObject<PortalGunBodyItem> PORTAL_DEVICE_BODY = registerItem("portal_device_body", new PortalGunBodyItem(new Properties().tab(PGRItemGroups.PGR_ITEMS).stacksTo(1).rarity(Rarity.RARE).fireResistant().setISTER(() -> PortalGunBodyItemRenderer::new)));
	
	private static <I extends Item> RegistryObject<I> registerItem(String regName, I item) {
		RegistryObject<I> itemRegObj = PGR_ITEMS.register(regName, () -> item);		
		return itemRegObj;
	}
}
