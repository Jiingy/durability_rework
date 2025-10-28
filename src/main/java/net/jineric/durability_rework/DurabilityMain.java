package net.jineric.durability_rework;

import net.fabricmc.api.ModInitializer;
import net.jineric.durability_rework.block.DrBlocks;
import net.jineric.durability_rework.block.entity.DrBlockEntityType;
import net.jineric.durability_rework.component.DrDataComponentTypes;
import net.jineric.durability_rework.item.DrItemGroups;
import net.jineric.durability_rework.item.DrItems;
import net.jineric.durability_rework.recipe.DrRecipeSerializer;
import net.jineric.durability_rework.recipe.DrRecipeType;
import net.jineric.durability_rework.recipe.display.AssembleRecipeDisplay;
import net.jineric.durability_rework.registry.tag.DrBlockTags;
import net.jineric.durability_rework.registry.tag.DrItemTags;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DurabilityMain implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("durability_rework");
	public static final String MOD_ID = "durability_rework";
	
	public static Identifier ofDurability(String path) {
		return Identifier.of(MOD_ID, path);
	}
	
	@Override
	public void onInitialize() {
		Registry.register(Registries.RECIPE_DISPLAY, "assemble", AssembleRecipeDisplay.SERIALIZER);
		DrItemTags.init();
		DrBlockTags.init();
		DrBlocks.init();
		DrItems.init();
		DrItemGroups.registerItemGroups();
		DrRecipeType.init();
		DrDataComponentTypes.initDataComponentTypes();
		DrRecipeSerializer.initialize();
		DrBlockEntityType.registerBlockEntityTypes();
	}
}