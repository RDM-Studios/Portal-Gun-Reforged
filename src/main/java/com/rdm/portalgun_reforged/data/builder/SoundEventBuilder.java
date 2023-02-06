package com.rdm.portalgun_reforged.data.builder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rdm.portalgun_reforged.PortalGunReforged;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;

public class SoundEventBuilder {
	private final List<SoundEvent> sounds;
	private final Map<SoundEvent, ResourceLocation> soundVariantMap = new LinkedHashMap<>();

	public SoundEventBuilder(List<SoundEvent> baseSound) {
		this.sounds = baseSound;
	}
	
	public SoundEventBuilder addExtraSoundVariantFor(SoundEvent event, List<ResourceLocation> paths) {
		for (ResourceLocation path : paths) {
			soundVariantMap.put(event, path);
		}
		
		return this;
	}

	@SuppressWarnings("deprecation")
	public JsonObject serialize(JsonObject mainObj) {
		for (SoundEvent baseSound : sounds) {
			JsonElement regNameObj = new JsonObject();

			mainObj.add(baseSound.getRegistryName().getPath(), regNameObj);
			regNameObj.getAsJsonObject().addProperty("subtitle", "subtitle." + PortalGunReforged.MODID + "." + baseSound.getRegistryName().getPath());

			JsonArray soundArray = new JsonArray();
			
			regNameObj.getAsJsonObject().add("sounds", soundArray);
			soundArray.add(Registry.SOUND_EVENT.getKey(baseSound).toString().replace('.', '/'));
			
			if (!soundVariantMap.isEmpty()) {
				for (Entry<SoundEvent, ResourceLocation> soundEventEntry : soundVariantMap.entrySet()) {
					if (soundEventEntry.getKey().equals(baseSound)) soundArray.add(soundEventEntry.getValue().getPath());
				}
			}
		}
		
		return mainObj;
	}
}
