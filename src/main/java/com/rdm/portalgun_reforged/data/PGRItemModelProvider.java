package com.rdm.portalgun_reforged.data;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.items.base.AnimatableItem;
import com.rdm.portalgun_reforged.common.registries.PGRItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class PGRItemModelProvider extends ItemModelProvider {

	public PGRItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, PortalGunReforged.MODID, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		registerItemModels();
	}
	
	private void registerItemModels() {
        final ModelFile parentGenerated = getExistingFile(mcLoc("item/generated"));
        final ExistingModelFile parentHandheld = getExistingFile(mcLoc("item/handheld"));
		
		for (RegistryObject<Item> itemGenEntry : PGRItems.PGR_ITEMS.getEntries()) {
			String itemGenEntryPath = itemGenEntry.getId().getPath();
			Item item = itemGenEntry.get();
			
			PortalGunReforged.LOGGER.debug(item.getDescriptionId());
			
            if (!existingFileHelper.exists(getItemResourceLocation(itemGenEntryPath), TEXTURE) || existingFileHelper.exists(getItemResourceLocation(itemGenEntryPath), MODEL) || item instanceof AnimatableItem) continue;
            getBuilder(itemGenEntryPath).parent(item.getMaxDamage(ItemStack.EMPTY) > 0 && !(item instanceof ArmorItem) ? parentHandheld : parentGenerated).texture("layer0", ItemModelProvider.ITEM_FOLDER + "/" + itemGenEntryPath);
		}
	}
	
	private static ResourceLocation getItemResourceLocation(String name) {
		return PortalGunReforged.prefixRL("item/".concat(name));
	}

}
