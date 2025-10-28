package net.jineric.durability_rework.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.data.family.EquipmentFamilies;
import net.jineric.durability_rework.data.family.EquipmentFamily;
import net.jineric.durability_rework.item.equipment.DrEquipmentAssetKeys;
import net.minecraft.block.WoodType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Comparator;

public class DrItemGroups {
	public static final Identifier DURABILITY_ITEMS_ID = DurabilityMain.ofDurability("durability_items");
	private static final RegistryKey<ItemGroup> MOD_ITEMS_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, DURABILITY_ITEMS_ID);
	
	RegistryEntryLookup<Item> itemRegistryEntryLookup = Registries.ITEM;
	
	public static ItemGroup DURABILITY_ITEMS_GROUP = FabricItemGroup.builder()
			.displayName(Text.literal("DurabilityMain Mod Items"))
			.icon(() -> new ItemStack(DrItems.WOODEN_CHESTPLATE))
			.entries(((displayContext, entries) -> {
				Registries.ITEM.stream()
						.filter(item -> Registries.ITEM.getId(item).getNamespace().equals("durability_rework"))
						.sorted(Comparator.comparing(DrItemGroups::getItemPath))
						.forEach(entries::add);
				
//				entries.add(DrItems.WORK_BENCH);
//
//				entries.add(DrItems.STONE_UPGRADE_SMITHING_TEMPLATE);
//				entries.add(DrItems.COPPER_UPGRADE_SMITHING_TEMPLATE);
//				entries.add(DrItems.IRON_UPGRADE_SMITHING_TEMPLATE);
//				entries.add(DrItems.GOLD_UPGRADE_SMITHING_TEMPLATE);
//				entries.add(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE);
//
//				entries.add(DrItems.WOODEN_HELMET);
//				entries.add(DrItems.WOODEN_CHESTPLATE);
//				entries.add(DrItems.WOODEN_LEGGINGS);
//				entries.add(DrItems.WOODEN_BOOTS);
				addWoodEquipment(entries, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
			}))
			.build();
	
	private static void addWoodEquipment(ItemGroup.Entries entries, ItemGroup.StackVisibility visibility) {
		EquipmentFamily family = EquipmentFamilies.WOODEN;
		
		family.getVariants().forEach((variant, item) -> {
			for (WoodType woodType : WoodType.stream().toList()) {
				String woodTypeName = woodType.name();
				ItemStack itemStack = new ItemStack(item);
				String id = "item.durability_rework." + woodTypeName + "_" + getItemPath(item);
				itemStack.set(DataComponentTypes.ITEM_NAME, Text.translatable(id));
				
				if (variant.isArmor()) {
					EquippableComponent.Builder builder = EquippableComponent.builder(EquipmentFamily.Variant.equipmentSlot(variant));
					RegistryKey<EquipmentAsset> assetKey = DrEquipmentAssetKeys.parseWoodenKey(woodTypeName);
					itemStack.set(DataComponentTypes.EQUIPPABLE, builder.model(assetKey).build());
				}
				
				entries.add(itemStack, visibility);
			}
		});
	}
	
	private static String getItemPath(Item item) {
		return Registries.ITEM.getId(item).getPath();
	}
	
	public static void registerItemGroups() {
		Registry.register(Registries.ITEM_GROUP, MOD_ITEMS_KEY, DURABILITY_ITEMS_GROUP);
	}
}
