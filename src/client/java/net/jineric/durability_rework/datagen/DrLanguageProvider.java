package net.jineric.durability_rework.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.impl.util.StringUtil;
import net.jineric.durability_rework.data.family.EquipmentFamilies;
import net.jineric.durability_rework.item.DrItems;
import net.minecraft.block.WoodType;
import net.minecraft.registry.RegistryWrapper;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class DrLanguageProvider extends FabricLanguageProvider {
	protected DrLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}
	
	@Override
	public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder tb) {
		EquipmentFamilies.WOODEN.getVariants().forEach((variant, item) -> {
			for (WoodType woodType : WoodType.stream().toList()) {
				String materialPath = woodType.name();
				String key = "item.durability_rework." + materialPath + "_wooden_" + variant;
				String value = WordUtils.capitalizeFully(materialPath.replace("_", " "));
				tb.add(
						key,
						value + " Wooden " + StringUtil.capitalize(variant.toString())
				);
			}
		});
		//  Upgrade Template Items
		tb.add(DrItems.STONE_UPGRADE_SMITHING_TEMPLATE, "Stone Upgrade");
		tb.add(DrItems.COPPER_UPGRADE_SMITHING_TEMPLATE, "Copper Upgrade");
		tb.add(DrItems.IRON_UPGRADE_SMITHING_TEMPLATE, "Iron Upgrade");
		tb.add(DrItems.GOLD_UPGRADE_SMITHING_TEMPLATE, "Gold Upgrade");
		tb.add(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE, "Diamond Upgrade");
		tb.add("item.durability_rework.smithing_template.stone_upgrade.applies_to", "Stone Equipment");
		tb.add("item.durability_rework.smithing_template.copper_upgrade.applies_to", "Copper Equipment");
		tb.add("item.durability_rework.smithing_template.iron_upgrade.applies_to", "Iron Equipment");
		tb.add("item.durability_rework.smithing_template.gold_upgrade.applies_to", "Gold Equipment");
		tb.add("item.durability_rework.smithing_template.diamond_upgrade.applies_to", "Diamond Equipment");
		tb.add("item.durability_rework.smithing_template.stone_upgrade.ingredients", "Stone");
		tb.add("item.durability_rework.smithing_template.copper_upgrade.ingredients", "Copper Ingot");
		tb.add("item.durability_rework.smithing_template.iron_upgrade.ingredients", "Iron Ingot");
		tb.add("item.durability_rework.smithing_template.gold_upgrade.ingredients", "Gold Ingot");
		tb.add("item.durability_rework.smithing_template.diamond_upgrade.ingredients", "Diamond");
		
		//  Other
		tb.add("container.jineric.work_bench", "Work Bench");
		tb.add("item.level", "Level: %s / %s");
		tb.add("item.level.max", "Level: Max");
		
		tb.add("item.durability_rework.smithing_template.stone_upgrade.base_slot_description", "Add wooden armor, weapon, or tool");
		tb.add("item.durability_rework.smithing_template.stone_upgrade.additions_slot_description", "Add Stone");
		tb.add("item.durability_rework.smithing_template.iron_upgrade.base_slot_description", "Add stone weapon or tool");
		tb.add("item.durability_rework.smithing_template.iron_upgrade.additions_slot_description", "Add Iron Ingot");
		tb.add("item.durability_rework.smithing_template.diamond_upgrade.base_slot_description", "Add iron armor, weapon, or tool");
		tb.add("item.durability_rework.smithing_template.diamond_upgrade.additions_slot_description", "Add Diamond");
		
		//  Game Options
		tb.add("options.durability_rework.itemLevelBarMode", "Item Level Bar");
		tb.add("options.durability_rework.itemLevelBar.always", "Always");
		tb.add("options.durability_rework.itemLevelBar.hover", "Hover");
		tb.add("options.durability_rework.itemLevelBar.never", "Never");
	}
}
