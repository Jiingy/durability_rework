package net.jineric.durability_rework.registry.tag;

import net.jineric.durability_rework.DurabilityMain;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class DrBlockTags {
	public static final TagKey<Block> INCORRECT_FOR_EMERALD_TOOL = registerItemTag("incorrect_for_emerald_tool");
	
	public static final TagKey<Block> BOOSTS_WOODEN_EQUIPMENT_LEVEL = registerItemTag("boosts_wooden_equipment_level");
	public static final TagKey<Block> BOOSTS_STONE_EQUIPMENT_LEVEL = registerItemTag("boosts_stone_equipment_level");
	public static final TagKey<Block> BOOSTS_COPPER_EQUIPMENT_LEVEL = registerItemTag("boosts_copper_equipment_level");
	public static final TagKey<Block> BOOSTS_IRON_EQUIPMENT_LEVEL = registerItemTag("boosts_iron_equipment_level");
	public static final TagKey<Block> BOOSTS_GOLD_EQUIPMENT_LEVEL = registerItemTag("boosts_gold_equipment_level");
	public static final TagKey<Block> BOOSTS_DIAMOND_EQUIPMENT_LEVEL = registerItemTag("boosts_diamond_equipment_level");
	public static final TagKey<Block> BOOSTS_NETHERITE_EQUIPMENT_LEVEL = registerItemTag("boosts_netherite_equipment_level");
	
	private static TagKey<Block> registerItemTag(String id) {
		return TagKey.of(Registries.BLOCK.getKey(), DurabilityMain.ofDurability(id));
	}
	
	public static void init() {
	}
}
