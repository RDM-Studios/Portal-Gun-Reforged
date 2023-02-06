package com.rdm.portalgun_reforged.data;

import java.util.function.Consumer;

import com.rdm.portalgun_reforged.PortalGunReforged;
import com.rdm.portalgun_reforged.common.registries.PGRItems;

import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

public class PGRRecipeProvider extends RecipeProvider {

	public PGRRecipeProvider(DataGenerator pGenerator) {
		super(pGenerator);
	}
	
	@Override
	public String getName() {
		return PortalGunReforged.MODNAME.concat(": Recipes");
	}
	
	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		// Portal Gun Items: Shaped Recipes
		ShapedRecipeBuilder.shaped(PGRItems.PORTAL_GUN.get())
				.define('B', PGRItems.PORTAL_DEVICE_BODY.get())
				.define('C', PGRItems.PORTAL_DEVICE_CLAW.get())
				.pattern("CB")
				.unlockedBy("has_" + PGRItems.PORTAL_DEVICE_BODY.get(), has(PGRItems.PORTAL_DEVICE_BODY.get()))
				.unlockedBy("has_" + PGRItems.PORTAL_DEVICE_CLAW.get(), has(PGRItems.PORTAL_DEVICE_CLAW.get()))
				.save(consumer);
		
		ShapedRecipeBuilder.shaped(PGRItems.PORTAL_DEVICE_CLAW.get())
				.define('S', Blocks.STONE)
				.define('Q', Blocks.QUARTZ_BLOCK)
				.define('D', Items.DIAMOND)
				.pattern("SS ")
				.pattern(" DS")
				.pattern("SSQ")
				.unlockedBy("has_" + Blocks.STONE.asItem(), has(Blocks.STONE))
				.unlockedBy("has_" + Blocks.QUARTZ_BLOCK.asItem(), has(Blocks.QUARTZ_BLOCK))
				.unlockedBy("has_" + Items.DIAMOND.asItem(), has(Items.DIAMOND))
				.save(consumer);
		
		ShapedRecipeBuilder.shaped(PGRItems.PORTAL_DEVICE_BODY.get())
				.define('S', Blocks.STONE)
				.define('Q', Blocks.QUARTZ_BLOCK)
				.define('G', Blocks.GLASS)
				.define('N', Items.NETHER_STAR)
				.define('R', Items.REDSTONE)
				.pattern("GQQ")
				.pattern("NRQ")
				.pattern("SSS")
				.unlockedBy("has_" + Blocks.STONE.asItem(), has(Blocks.STONE))
				.unlockedBy("has_" + Blocks.QUARTZ_BLOCK.asItem(), has(Blocks.QUARTZ_BLOCK))
				.unlockedBy("has_" + Blocks.GLASS.asItem(), has(Blocks.GLASS))
				.unlockedBy("has_" + Items.NETHER_STAR.asItem(), has(Items.NETHER_STAR))
				.unlockedBy("has_" + Items.REDSTONE.asItem(), has(Items.REDSTONE))
				.save(consumer);
	}

}
