package com.rdm.portalgun_reforged.common.events;

import com.rdm.portalgun_reforged.data.PGRItemModelProvider;
import com.rdm.portalgun_reforged.data.PGRLanguageProvider;
import com.rdm.portalgun_reforged.data.PGRRecipeProvider;
import com.rdm.portalgun_reforged.data.PGRSoundEventProvider;
import com.rdm.portalgun_reforged.manager.PGRNetworkManager;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class PGRCommonSetupEvents {
	
	public static void onFMLCommonSetup(final FMLCommonSetupEvent event) {
		PGRNetworkManager.registerPackets();
	}
	
	public static void onEntityAttributeCreation(final EntityAttributeCreationEvent event) {
		
	}
	
	public static void onGatherDataGen(final GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();
		final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		
		if (event.includeClient()) {
			dataGenerator.addProvider(new PGRItemModelProvider(dataGenerator, existingFileHelper));
		}	
		
		dataGenerator.addProvider(new PGRLanguageProvider(dataGenerator));
		dataGenerator.addProvider(new PGRSoundEventProvider(dataGenerator));
		
		if (event.includeServer()) {
			dataGenerator.addProvider(new PGRRecipeProvider(dataGenerator));
		}
	}

}
