package com.rdm.portalgun_reforged.common.registries;

import com.rdm.portalgun_reforged.PortalGunReforged;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class PGRItemGroups {
	
	public static final ItemGroup PGR_ITEMS = new ItemGroup(PortalGunReforged.MODID.concat(".portal_gun_items")) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(PGRItems.PORTAL_GUN.get());
		}
	};

}
