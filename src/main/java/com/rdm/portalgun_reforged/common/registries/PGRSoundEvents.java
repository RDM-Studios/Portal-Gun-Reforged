package com.rdm.portalgun_reforged.common.registries;

import com.rdm.portalgun_reforged.PortalGunReforged;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PGRSoundEvents {
	public static final DeferredRegister<SoundEvent> PGR_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PortalGunReforged.MODID);
	
	public static final RegistryObject<SoundEvent> PORTAL_CLOSE_1 = registerSoundEvent("entity.portal.close_1", "entity.portal.close_1");
	public static final RegistryObject<SoundEvent> PORTAL_CLOSE_2 = registerSoundEvent("entity.portal.close_2", "entity.portal.close_2");
	
	public static final RegistryObject<SoundEvent> PORTAL_OPEN_1 = registerSoundEvent("entity.portal.open_1", "entity.portal.open_1");
	public static final RegistryObject<SoundEvent> PORTAL_OPEN_2 = registerSoundEvent("entity.portal.open_2", "entity.portal.open_2");
	public static final RegistryObject<SoundEvent> PORTAL_OPEN_3 = registerSoundEvent("entity.portal.open_3", "entity.portal.open_3");
	
	public static final RegistryObject<SoundEvent> PORTAL_SHOOT_1 = registerSoundEvent("entity.portal.shoot_1", "entity.portal.shoot_1");
	public static final RegistryObject<SoundEvent> PORTAL_SHOOT_2 = registerSoundEvent("entity.portal.shoot_2", "entity.portal.shoot_2");
	
	private static RegistryObject<SoundEvent> registerSoundEvent(String regName, String soundEventLoc) {
		RegistryObject<SoundEvent> soundEventRegObj = PGR_SOUND_EVENTS.register(regName, () -> new SoundEvent(PortalGunReforged.prefixRL(soundEventLoc)));		
		return soundEventRegObj;
	}
}
