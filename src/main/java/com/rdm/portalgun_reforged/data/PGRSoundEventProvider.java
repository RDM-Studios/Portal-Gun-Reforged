package com.rdm.portalgun_reforged.data;

import java.util.ArrayList;
import java.util.List;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.registries.PGRSoundEvents;
import com.rdm.portalgun_reforged.data.baseprovider.SoundEventProvider;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class PGRSoundEventProvider extends SoundEventProvider {

	public PGRSoundEventProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addSoundEvents() {
		List<SoundEvent> sounds = new ArrayList<>();
		List<ResourceLocation> openSoundVariants = new ArrayList<>();
		
		for (RegistryObject<SoundEvent> soundEventGenEntry : PGRSoundEvents.PGR_SOUND_EVENTS.getEntries()) {
			sounds.add(soundEventGenEntry.get());
			openSoundVariants.addAll(List.of(PortalGunReforged.prefixRL("entity/portal/open_1"), PortalGunReforged.prefixRL("entity/portal/open_2"), PortalGunReforged.prefixRL("entity/portal/open_3")));
		}
		
		createSoundEvent(sounds)
				.addExtraSoundVariantFor(PGRSoundEvents.PORTAL_OPEN.get(), openSoundVariants);
	}
	
}
