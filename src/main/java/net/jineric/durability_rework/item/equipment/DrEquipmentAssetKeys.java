package net.jineric.durability_rework.item.equipment;

import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;

public interface DrEquipmentAssetKeys {
	RegistryKey<EquipmentAsset> WOOD = EquipmentAssetKeys.register("wood");
	RegistryKey<EquipmentAsset> OAK_WOOD = EquipmentAssetKeys.register("oak_wood");
	RegistryKey<EquipmentAsset> SPRUCE_WOOD = EquipmentAssetKeys.register("spruce_wood");
	RegistryKey<EquipmentAsset> BIRCH_WOOD = EquipmentAssetKeys.register("birch_wood");
	RegistryKey<EquipmentAsset> JUNGLE_WOOD = EquipmentAssetKeys.register("jungle_wood");
	RegistryKey<EquipmentAsset> ACACIA_WOOD = EquipmentAssetKeys.register("acacia_wood");
	RegistryKey<EquipmentAsset> DARK_OAK_WOOD = EquipmentAssetKeys.register("dark_oak_wood");
	RegistryKey<EquipmentAsset> MANGROVE_WOOD = EquipmentAssetKeys.register("mangrove_wood");
	RegistryKey<EquipmentAsset> CHERRY_WOOD = EquipmentAssetKeys.register("cherry_wood");
	RegistryKey<EquipmentAsset> PALE_OAK_WOOD = EquipmentAssetKeys.register("pale_oak_wood");
	RegistryKey<EquipmentAsset> BAMBOO_WOOD = EquipmentAssetKeys.register("bamboo_wood");
	RegistryKey<EquipmentAsset> CRIMSON_WOOD = EquipmentAssetKeys.register("crimson_wood");
	RegistryKey<EquipmentAsset> WARPED_WOOD = EquipmentAssetKeys.register("warped_wood");
	RegistryKey<EquipmentAsset> EMERALD = EquipmentAssetKeys.register("emerald");
	
	static RegistryKey<EquipmentAsset> parseWoodenKey(String woodType) {
		RegistryKey<EquipmentAsset> assetKey;
		switch (woodType) {
			case "oak" -> assetKey = DrEquipmentAssetKeys.OAK_WOOD;
			case "spruce" -> assetKey = DrEquipmentAssetKeys.SPRUCE_WOOD;
			case "birch" -> assetKey = DrEquipmentAssetKeys.BIRCH_WOOD;
			case "jungle" -> assetKey = DrEquipmentAssetKeys.JUNGLE_WOOD;
			case "acacia" -> assetKey = DrEquipmentAssetKeys.ACACIA_WOOD;
			case "dark_oak" -> assetKey = DrEquipmentAssetKeys.DARK_OAK_WOOD;
			case "mangrove" -> assetKey = DrEquipmentAssetKeys.MANGROVE_WOOD;
			case "cherry" -> assetKey = DrEquipmentAssetKeys.CHERRY_WOOD;
			case "pale_oak" -> assetKey = DrEquipmentAssetKeys.PALE_OAK_WOOD;
			case "bamboo" -> assetKey = DrEquipmentAssetKeys.BAMBOO_WOOD;
			case "crimson" -> assetKey = DrEquipmentAssetKeys.CRIMSON_WOOD;
			case "warped" -> assetKey = DrEquipmentAssetKeys.WARPED_WOOD;
			default -> assetKey = DrEquipmentAssetKeys.WOOD;
		}
		return assetKey;
	}
}