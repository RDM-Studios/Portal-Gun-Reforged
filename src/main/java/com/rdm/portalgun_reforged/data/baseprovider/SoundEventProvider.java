package com.rdm.portalgun_reforged.data.baseprovider;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.rdm.portalgun_reforged.data.builder.SoundEventBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.SoundEvent;

public abstract class SoundEventProvider implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final ObjectArrayList<SoundEventBuilder> events = new ObjectArrayList<SoundEventBuilder>();
	public final DataGenerator generator;

	public SoundEventProvider(DataGenerator generator) {
		this.generator = generator;
	}

	@Override
	public final void run(DirectoryCache cache) throws IOException {
		this.events.clear();
		this.addSoundEvents();
		
		for (SoundEventBuilder soundEventEntry : events) {
			for (int i = 0; i < events.size(); i++) {
				IDataProvider.save(GSON, cache, soundEventEntry.serialize(new JsonObject()), generator.getOutputFolder().resolve("assets/portalgun_reforged/sounds.json"));
			}
		}
	}

	protected abstract void addSoundEvents();

	/**
	 * Can only be called once, otherwise overrides original file with new values (dumb)
	 * @param baseSound base sound
	 * @return new sound event builder
	 */
	protected SoundEventBuilder createSoundEvent(List<SoundEvent> baseSound) {
		SoundEventBuilder eventBuilder = new SoundEventBuilder(baseSound);
		if (!events.contains(eventBuilder)) events.add(eventBuilder);
		return eventBuilder;
	}

	@Override
	public String getName() {
		return "Sound Events";
	}

}
