package com.rdm.portalgun_reforged.common.config;

import org.apache.commons.lang3.tuple.Pair;

import com.rdm.portalgun_reforged.common.items.PortalGunItem.RemovalMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class PGRCommonConfig {
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	
	static {
		final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
	}
	
	public static class Common {
		public final EnumValue<RemovalMode> entityRemovalMode;
		public final IntValue portalTeleportationRange;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.push("Items");
			builder.push("Portal Gun");
			entityRemovalMode = builder
					.comment("When in CARRY_ENTITY mode, define the portal gun's behaviour discarding entities."
							+ "\n"
							+ "Consecutive: " + RemovalMode.CONSECUTIVE.getDescription()
							+ "\n"
							+ "Point At: " + RemovalMode.POINT_AT.getDescription())
					.defineEnum("Entity Removal Mode", RemovalMode.CONSECUTIVE);
			builder.pop(2);
			
			builder.push("Portals");
			portalTeleportationRange = builder
					.comment("The range at which portals will teleport entities.")
					.defineInRange("Portal Teleportation Range", 1600, 0, Integer.MAX_VALUE);
			builder.pop();
		}
	}
}
