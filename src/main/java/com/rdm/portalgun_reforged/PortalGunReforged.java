package com.rdm.portalgun_reforged;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import com.rdm.portalgun_reforged.manager.PGRModManager;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import software.bernie.geckolib3.GeckoLib;

@Mod(PortalGunReforged.MODID)
public class PortalGunReforged {
	public static final String MODNAME = "Portal Gun Reforged";
	public static final String MODID = "portalgun_reforged";
	public static ArtifactVersion VERSION = null;
	public static final Logger LOGGER = LogManager.getLogger();
	public static IEventBus MOD_BUS;
	public static IEventBus FORGE_BUS;
	public static PortalGunReforged INSTANCE;

	public PortalGunReforged() {
		INSTANCE = this;
		
		GeckoLib.initialize();
		
		MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		FORGE_BUS = MinecraftForge.EVENT_BUS;
				
		Optional<? extends ModContainer> mod = ModList.get().getModContainerById(MODID);
		if (mod.isPresent()) {
			IModInfo modInfo = mod.get().getModInfo();
			VERSION = modInfo.getVersion();
		} else {
			LOGGER.warn("Could not get mod version from mod info");
		}

		if (MOD_BUS != null && FORGE_BUS != null) PGRModManager.registerAll();
	}
	
	public static ResourceLocation prefixRL(String tString) {
		return new ResourceLocation(MODID, tString);
	}
	
	public static String prefix(String tString) {
		return MODID.concat(":".concat(tString));
	}
}
