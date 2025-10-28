package net.jineric.durability_rework.item;

import net.jineric.durability_rework.DrConfig;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.block.DrBlocks;
import net.jineric.durability_rework.item.equipment.DrArmorMaterials;
import net.jineric.durability_rework.item.equipment.DrEquipmentAssetKeys;
import net.jineric.durability_rework.item.equipment.DrToolMaterials;
import net.jineric.durability_rework.item.template.DrSmithingTemplateItem;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DrItems {
	// Misc
	public static final Item WORK_BENCH = register(DrBlocks.WORK_BENCH);
	public static final Item STONE_CRUCIBLE = register(DrBlocks.STONE_CRUCIBLE);
	public static final Item BOW_DRILL = register("bow_drill", BowDrillItem::new, new Item.Settings().maxCount(1));
	public static final Item TINDER = register(DrBlocks.TINDER);
	
	//  Armor
	public static final Item WOODEN_HELMET = register("wooden_helmet",
			new Item.Settings()
					.armor(DrArmorMaterials.WOODEN, EquipmentType.HELMET)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD).model(DrEquipmentAssetKeys.WOOD).build())
	);
	public static final Item WOODEN_CHESTPLATE = register("wooden_chestplate",
			new Item.Settings()
					.armor(DrArmorMaterials.WOODEN, EquipmentType.CHESTPLATE)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.CHEST).model(DrEquipmentAssetKeys.WOOD).build())
	);
	public static final Item WOODEN_LEGGINGS = register("wooden_leggings",
			new Item.Settings()
					.armor(DrArmorMaterials.WOODEN, EquipmentType.LEGGINGS)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.LEGS).model(DrEquipmentAssetKeys.WOOD).build())
	);
	public static final Item WOODEN_BOOTS = register("wooden_boots",
			new Item.Settings()
					.armor(DrArmorMaterials.WOODEN, EquipmentType.BOOTS)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.FEET).model(DrEquipmentAssetKeys.EMERALD).build())
	);
	
	//  EMERALD EQUIPMENT
	public static final Item EMERALD_HELMET = register("emerald_helmet",
			new Item.Settings()
					.armor(DrArmorMaterials.EMERALD, EquipmentType.HELMET)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD).model(DrEquipmentAssetKeys.EMERALD).build())
	);
	public static final Item EMERALD_CHESTPLATE = register("emerald_chestplate",
			new Item.Settings()
					.armor(DrArmorMaterials.EMERALD, EquipmentType.CHESTPLATE)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.CHEST).model(DrEquipmentAssetKeys.EMERALD).build())
	);
	public static final Item EMERALD_LEGGINGS = register("emerald_leggings",
			new Item.Settings()
					.armor(DrArmorMaterials.EMERALD, EquipmentType.LEGGINGS)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.LEGS).model(DrEquipmentAssetKeys.EMERALD).build())
	);
	public static final Item EMERALD_BOOTS = register("emerald_boots",
			new Item.Settings()
					.armor(DrArmorMaterials.EMERALD, EquipmentType.BOOTS)
					.component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.FEET).model(DrEquipmentAssetKeys.EMERALD).build())
	);
	public static final Item EMERALD_SWORD = register("emerald_sword", new Item.Settings().sword(DrToolMaterials.EMERALD, 3.0F, -2.4F));
	public static final Item EMERALD_SHOVEL = register("emerald_shovel", settings -> new ShovelItem(DrToolMaterials.EMERALD, 1.5F, -3.0F, settings));
	public static final Item EMERALD_PICKAXE = register("emerald_pickaxe", new Item.Settings().pickaxe(DrToolMaterials.EMERALD, 1.0F, -2.8F));
	public static final Item EMERALD_AXE = register("emerald_axe", settings -> new AxeItem(DrToolMaterials.EMERALD, 5.0F, -3.0F, settings));
	public static final Item EMERALD_HOE = register("emerald_hoe", settings -> new HoeItem(DrToolMaterials.EMERALD, -3.0F, 0.0F, settings));
	
	public static final Item FLINT_PICKAXE = register("flint_pickaxe", new Item.Settings().pickaxe(DrToolMaterials.FLINT, 1.0F, -2.8F));
	public static final Item DEEPSLATE_PICKAXE = register("deepslate_pickaxe", new Item.Settings().pickaxe(DrToolMaterials.DEEPSLATE, 1.0F, -2.8F));
	public static final Item AMETHYST_PICKAXE = register("amethyst_pickaxe", new Item.Settings().pickaxe(DrToolMaterials.AMETHYST, 1.0F, -2.8F));
	
	
	//  Templates
	public static final Item STONE_UPGRADE_SMITHING_TEMPLATE = register("stone_upgrade_smithing_template",
			DrSmithingTemplateItem::createStoneUpgrade,
			DrConfig.UPGRADE_TEMPLATE ? new Item.Settings().durability$maxLevel(2) : new Item.Settings()
	);
	public static final Item COPPER_UPGRADE_SMITHING_TEMPLATE = register("copper_upgrade_smithing_template",
			DrSmithingTemplateItem::createCopperUpgrade,
			DrConfig.UPGRADE_TEMPLATE ? new Item.Settings().durability$maxLevel(4) : new Item.Settings()
	);
	public static final Item IRON_UPGRADE_SMITHING_TEMPLATE = register("iron_upgrade_smithing_template",
			DrSmithingTemplateItem::createIronUpgrade,
			DrConfig.UPGRADE_TEMPLATE ? new Item.Settings().durability$maxLevel(8) : new Item.Settings()
	);
	public static final Item GOLD_UPGRADE_SMITHING_TEMPLATE = register("gold_upgrade_smithing_template",
			DrSmithingTemplateItem::createGoldUpgrade,
			DrConfig.UPGRADE_TEMPLATE ? new Item.Settings().durability$maxLevel(16) : new Item.Settings()
	);
	public static final Item DIAMOND_UPGRADE_SMITHING_TEMPLATE = register("diamond_upgrade_smithing_template",
			DrSmithingTemplateItem::createDiamondUpgrade,
			DrConfig.UPGRADE_TEMPLATE ? new Item.Settings().durability$maxLevel(32) : new Item.Settings()
	);
	
	
	// Registry Methods
	private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, DurabilityMain.ofDurability(id));
	}
	
	private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
		return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
	}
	
	private static Item register(Block block) {
		return register(block, BlockItem::new);
	}
	
	private static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
		return register(block, factory, new Item.Settings());
	}
	
	private static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
		return register(
				keyOf(block.getRegistryEntry().registryKey()), itemSettings -> factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey()
		);
	}
	
	public static Item register(String id, Function<Item.Settings, Item> factory) {
		return register(keyOf(id), factory, new Item.Settings());
	}
	
	public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
		return register(keyOf(id), factory, settings);
	}
	
	public static Item register(String id, Item.Settings settings) {
		return register(keyOf(id), Item::new, settings);
	}
	
	private static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
		Item item = factory.apply(settings.registryKey(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
		}
		
		return Registry.register(Registries.ITEM, key, item);
	}
	
	public static void init() {
	}
}
