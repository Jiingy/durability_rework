package net.jineric.durability_rework.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.block.DrBlocks;
import net.jineric.durability_rework.data.family.EquipmentFamilies;
import net.jineric.durability_rework.data.family.EquipmentFamily;
import net.jineric.durability_rework.item.DrItems;
import net.minecraft.block.WoodType;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.ComponentSelectProperty;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class DrModelProvider extends FabricModelProvider {
	public ItemModelOutput output;
	ItemModelGenerator itemModelGenerator;
	public BiConsumer<Identifier, ModelSupplier> modelCollector;
	public DrModelProvider(FabricDataOutput output) {
		super(output);
	}
	
	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleState(DrBlocks.WORK_BENCH);
		blockStateModelGenerator.registerSimpleState(DrBlocks.STONE_CRUCIBLE);
	}
	
	@Override
	public void generateItemModels(ItemModelGenerator img) {
		this.output = img.output;
		this.itemModelGenerator = img;
		this.modelCollector = img.modelCollector;

		this.registerWoodEquipmentFamily(EquipmentFamilies.WOODEN);
		img.register(DrItems.EMERALD_HELMET, Models.GENERATED);
		img.register(DrItems.EMERALD_CHESTPLATE, Models.GENERATED);
		img.register(DrItems.EMERALD_LEGGINGS, Models.GENERATED);
		img.register(DrItems.EMERALD_BOOTS, Models.GENERATED);
		img.register(DrItems.EMERALD_PICKAXE, Models.HANDHELD);
		img.register(DrItems.EMERALD_AXE, Models.HANDHELD);
		img.register(DrItems.EMERALD_SWORD, Models.HANDHELD);
		img.register(DrItems.EMERALD_SHOVEL, Models.HANDHELD);
		img.register(DrItems.EMERALD_HOE, Models.HANDHELD);
		img.register(DrItems.FLINT_PICKAXE, Models.HANDHELD);
		img.register(DrItems.DEEPSLATE_PICKAXE, Models.HANDHELD);
		img.register(DrItems.AMETHYST_PICKAXE, Models.HANDHELD);
		
		img.register(DrItems.STONE_CRUCIBLE, Models.GENERATED);
		img.register(DrItems.BOW_DRILL, Models.GENERATED);
		img.register(DrItems.STONE_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		img.register(DrItems.COPPER_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		img.register(DrItems.IRON_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		img.register(DrItems.GOLD_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
		img.register(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE, Models.GENERATED);
	}
	
	public final Identifier uploadArmorWithTrim(Identifier id, Identifier layer0, Identifier layer1) {
		return Models.GENERATED_TWO_LAYERS.upload(id, TextureMap.layered(layer0, layer1), this.modelCollector);
	}
	
	public final void registerWoodEquipmentFamily(EquipmentFamily family) {
		family.getVariants().forEach((variant, item) -> registerWoodEquipmentFamily(family, variant, item));
	}
	
	public final void registerWoodEquipmentFamily(EquipmentFamily family, EquipmentFamily.Variant variant, Item item) {
		List<SelectItemModel.SwitchCase<Text>> textList = new ArrayList<>();
		
		for (WoodType woodType : WoodType.stream().toList()) {
			String woodTypeName = woodType.name();
			String typeWoodenItem = woodTypeName + "_" + RecipeGenerator.getItemPath(item);
			Identifier itemId = getPrefixItemModelId(item, woodTypeName  + "_");
			Identifier layer0 = TextureMap.getId(item);
			
			//  Generates armor item models with trim overlay
//			List<SelectItemModel.SwitchCase<RegistryKey<ArmorTrimMaterial>>> list
//					= new ArrayList<>(ItemModelGenerator.TRIM_MATERIALS.size()
//			);
//			for (ItemModelGenerator.TrimMaterial trimMaterial : ItemModelGenerator.TRIM_MATERIALS) {
//				Identifier identifier4 = itemId.withSuffixedPath("_" + trimMaterial.assets().base().suffix() + "_trim");
//				Identifier layer1 = trimIdPrefix.withSuffixedPath("_" + trimMaterial.assets().getAssetId(equipmentKey).suffix());
//				ItemModel.Unbaked unbaked;
//				this.uploadArmorWithTrim(identifier4, layer0, layer1);
//				unbaked = ItemModels.basic(identifier4);
//				list.add(ItemModels.switchCase(trimMaterial.materialKey, unbaked));
//			}
			if (variant.isArmor()) {
				Models.GENERATED.upload(
						getPrefixItemModelId(item, woodTypeName  + "_"),
						TextureMap.layer0(itemId),
						this.modelCollector
				);
			} else {
				Models.HANDHELD.upload(
						getPrefixItemModelId(item, woodTypeName  + "_"),
						TextureMap.layer0(itemId),
						this.modelCollector
				);
			}

			ItemModel.Unbaked unbaked2 = ItemModels.basic(itemId);
			
			String translation = "item.durability_rework." + typeWoodenItem;
			textList.add(
					ItemModels.switchCase(
							Text.translatable(translation),
							ItemModels.basic(getPrefixItemModelId(item, woodTypeName  + "_"))
					)
			);
		}
		
		if (variant.isArmor()) {
			Models.GENERATED.upload(item, TextureMap.layer0(item), this.modelCollector);
		}
		
		this.output.accept(
				item,
				ItemModels.select(
						new ComponentSelectProperty<>(DataComponentTypes.ITEM_NAME),
						ItemModels.basic(Registries.ITEM.getId(item).withPrefixedPath("item/")),
						textList
				)
		);
	}
	
	public static Identifier getPrefixItemModelId(Item item, String prefix) {
		String path = Registries.ITEM.getId(item).getPath();
		Identifier identifier = DurabilityMain.ofDurability(path);
		return identifier.withPrefixedPath("item/" + prefix);
	}
}
