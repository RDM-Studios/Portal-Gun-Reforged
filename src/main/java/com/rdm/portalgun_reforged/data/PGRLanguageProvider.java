package com.rdm.portalgun_reforged.data;

import static com.rdm.portalgun_reforged.common.registries.PGRItemGroups.PGR_ITEMS;
import static com.rdm.portalgun_reforged.common.registries.PGRKeyBindings.REMOVE_OLD_PORTALS;
import static com.rdm.portalgun_reforged.common.registries.PGRKeyBindings.SWITCH_PORTAL_GUN_MODE;

import java.util.List;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.registries.PGRItems;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class PGRLanguageProvider extends LanguageProvider {
	
	public PGRLanguageProvider(DataGenerator gen) {
		super(gen, PortalGunReforged.MODID, "en_us");
	}
	
	@Override
	public String getName() {
		return PortalGunReforged.MODNAME.concat(": Localization (en_us)");
	}
	
	private static ObjectArrayList<String> getLowerCaseListedWords() {
		ObjectArrayList<String> lcw = new ObjectArrayList<String>();
		
		lcw.addAll(List.of("Of", "And"));
		
		return lcw;
	}
	
	// https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string
	private static String formatString(String input) {
		char[] charSet = input.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < charSet.length - 1; i++) {
			if (!found && Character.isLetter(charSet[i])) {
				charSet[i] = Character.toUpperCase(charSet[i]);
				found = true;
			} else if (Character.isWhitespace(charSet[i]) || charSet[i] == '.' || charSet[i] == '_' || charSet[i] == '-') {
				found = false;
			}
		}
		
		String baseResult = String.valueOf(charSet);
		
		for (String lcw : getLowerCaseListedWords()) if (baseResult.contains(lcw)) baseResult = baseResult.replaceAll(lcw, lcw.toLowerCase());
		
		return baseResult;
	}
	
	private void localizeGeneralRegistryNames(String registryName) {		
		if (registryName.startsWith(" ") || registryName.isEmpty() || registryName == null) return;
		if (!registryName.contains(".")) return;
		
		String regNameTemp = registryName;
		String formatted = formatString(regNameTemp);
		String regName = formatted.substring(formatted.lastIndexOf(".") + 1).replaceAll("_", " ");
		
		add(registryName, regName);
	}
	
	private void translateItemGroups() {
		ObjectArrayList<ItemGroup> pgrItemGroups = new ObjectArrayList<ItemGroup>(1);
		
		// Lazy 100 (real)
		if (pgrItemGroups.isEmpty()) {
			pgrItemGroups.addAll(List.of(PGR_ITEMS));
		}
		
		for (ItemGroup tab : pgrItemGroups) {
			String regName = tab.getDisplayName().toString();
			int endCharIndex = regName.indexOf("',");
			regName = regName.substring(27, endCharIndex);
			
			localizeGeneralRegistryNames(regName);
		}
	}
	
	private void translateItems() {
		for (RegistryObject<Item> itemGenEntry : PGRItems.PGR_ITEMS.getEntries()) {
			Item item = itemGenEntry.get();
			String regName = item.getDescriptionId();
			
			localizeGeneralRegistryNames(regName);
		}
	}
	
	private void translateKeyBindings() {
		ObjectArrayList<KeyBinding> pgrKeyBindings = new ObjectArrayList<KeyBinding>(1);
		
		// Lazy 100 part 2 (real)
		if (pgrKeyBindings.isEmpty()) {
			pgrKeyBindings.addAll(List.of(REMOVE_OLD_PORTALS, SWITCH_PORTAL_GUN_MODE));
		}
		
		for (KeyBinding keyBinding : pgrKeyBindings) {
			String regName = keyBinding.getName();
			
			localizeGeneralRegistryNames(regName);
		}
		
		add("key.category.portalgun_reforged", "Portal Gun Reforged");
	}

	@Override
	protected void addTranslations() {
		translateItemGroups();
		translateItems();
		translateKeyBindings();
	}

}
