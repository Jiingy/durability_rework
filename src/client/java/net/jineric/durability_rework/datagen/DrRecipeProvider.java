package net.jineric.durability_rework.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.access.ShapedRecipeJsonBuilderAccess;
import net.jineric.durability_rework.component.DrDataComponentTypes;
import net.jineric.durability_rework.data.family.EquipmentFamilies;
import net.jineric.durability_rework.data.family.EquipmentFamily;
import net.jineric.durability_rework.data.family.EquipmentVariants;
import net.jineric.durability_rework.item.DrItems;
import net.jineric.durability_rework.item.equipment.DrEquipmentAssetKeys;
import net.jineric.durability_rework.registry.tag.DrItemTags;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodType;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.data.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class DrRecipeProvider extends FabricRecipeProvider {
	public static boolean USE_VANILLA = false;
	
	public DrRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
		return new RecipeGenerator(wrapperLookup, recipeExporter) {
			public RegistryEntryLookup<Item> itemLookup;
			
			@Override
			public void generate() {
				this.itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);
				
//				this.offer2x2CompactingRecipe(RecipeCategory.TOOLS, DrItems.FIRE_DRILL, Items.STICK);
				
				this.createShapeless(RecipeCategory.MISC, Items.STRING, 4)
						.input(ItemTags.WOOL)
						.criterion("has_wool", this.conditionsFromTag(ItemTags.WOOL))
						.group("string")
						.offerTo(recipeExporter);
				
				this.createShaped(RecipeCategory.DECORATIONS, DrItems.TINDER)
						.input('T', DrItemTags.TINDER_MATERIALS)
						.pattern("TT")
						.pattern("TT")
						.criterion("has_tinder_material", this.conditionsFromTag(DrItemTags.TINDER_MATERIALS))
						.offerTo(recipeExporter);
				
				this.createShaped(RecipeCategory.TOOLS, DrItems.BOW_DRILL)
						.input('B', Items.BOW).input('S', Items.STICK).input('L', ItemTags.LOGS_THAT_BURN)
						.pattern("B")
						.pattern("S")
						.pattern("L")
						.criterion("has_flammable_log", this.conditionsFromTag(ItemTags.LOGS_THAT_BURN))
						.offerTo(recipeExporter);
				
				CookingRecipeJsonBuilder.createCampfireCooking(this.ingredientFromTag(ItemTags.LOGS_THAT_BURN), RecipeCategory.MISC, Items.CHARCOAL, 0.15F, 800)
						.criterion("has_log", this.conditionsFromTag(ItemTags.LOGS_THAT_BURN))
						.offerTo(recipeExporter, "charcoal_from_campfire_cooking");
				
//				ItemStack input = new ItemStack(Items.GOLD_INGOT, 3);
//				input.set(DataComponentTypes.ITEM_MODEL, Identifier.of("gold_ingot"));
//				AssembleEquipmentRecipeJsonBuilder.create(
//								Ingredient.ofItem(Items.GOLD_INGOT),
//								Ingredient.ofItem(Items.STICK),
//								Ingredient.ofItem(Items.DIAMOND),
//								RecipeCategory.TOOLS,
//								new ItemStack(Items.GOLDEN_PICKAXE, 1)
//						)
//						.criterion("has_gold_ingot", this.conditionsFromItem(Items.GOLD_INGOT))
//						.offerTo(recipeExporter, getItemPath(Items.GOLDEN_PICKAXE) + "_from_assemble")
//				;
				
				
				this.createShaped(RecipeCategory.MISC, DrItems.STONE_UPGRADE_SMITHING_TEMPLATE, 1)
						.input('M', Items.SMOOTH_STONE).input('S', Items.STONE)
						.input('W', DrItemTags.WOOD).input('L', DrItemTags.LOGS)
						.pattern("LML")
						.pattern("LSL")
						.pattern("WWW")
						.criterion("has_cobblestone", this.conditionsFromItem(Items.COBBLESTONE))
						.offerTo(recipeExporter, getItemPath(DrItems.STONE_UPGRADE_SMITHING_TEMPLATE))
				;// Wood -> Stone
				
				this.createShaped(RecipeCategory.MISC, DrItems.COPPER_UPGRADE_SMITHING_TEMPLATE, 1)
						.input('F', Items.SMOOTH_STONE).input('T', DrItemTags.CUT_COPPER).input('E', Items.STONE)
						.pattern("FTF")
						.pattern("FEF")
						.pattern("FFF")
						.criterion("has_copper_ingot", this.conditionsFromItem(Items.COPPER_INGOT))
						.offerTo(recipeExporter, getItemPath(DrItems.COPPER_UPGRADE_SMITHING_TEMPLATE))
				;// Stone -> Copper
				
				this.createShaped(RecipeCategory.MISC, DrItems.IRON_UPGRADE_SMITHING_TEMPLATE, 1)
						.input('F', ItemTags.COPPER).input('T', Items.IRON_BLOCK).input('E', Items.DEEPSLATE)
						.pattern("FTF")
						.pattern("FEF")
						.pattern("FFF")
//						.group(getItemPath(DrItems.IRON_UPGRADE_SMITHING_TEMPLATE))
						.criterion("has_iron_ingot", this.conditionsFromItem(Items.IRON_INGOT))
						.offerTo(recipeExporter, getItemPath(DrItems.IRON_UPGRADE_SMITHING_TEMPLATE))
				;// COPPER -> Iron
				
				this.createShaped(RecipeCategory.MISC, DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE, 1)
						.input('F', Items.IRON_INGOT).input('T', Items.DIAMOND_BLOCK).input('E', Items.DEEPSLATE)
						.pattern("FTF")
						.pattern("FEF")
						.pattern("FFF")
//						.group(getItemPath(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
						.criterion("has_" + getItemPath(Items.DIAMOND), this.conditionsFromItem(Items.DIAMOND))
						.offerTo(recipeExporter, getItemPath(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
				;// Iron -> Diamond
				
				EquipmentFamilies.streamVariants((k, v, s) -> {
					if (k.genRecipes()) {
						if (k == EquipmentFamilies.WOODEN) {
							this.offerWoodenEquipmentVariantRecipes(k, v);
						} else {
							USE_VANILLA = true;
//							this.offerFamilyVariantOverrides(k, v, v.getCraftingLayout());
							USE_VANILLA = false;
						}
						
						if (!v.isArmor()) {
//							this.generateEquipmentFamilyUpgradeRecipes(k, v, s);
						}
					}
					if (k.equals(EquipmentFamilies.DIAMOND)) {
						USE_VANILLA = true;
//						this.offerFamilyVariantOverrides(k, v, v.getCraftingLayout());
						USE_VANILLA = false;
					}
				});
				
				USE_VANILLA = true;
				this.offerCampfireRecipe();
				
				this.createShaped(RecipeCategory.MISC, Blocks.SMITHING_TABLE)
						.input('S', Items.SMOOTH_STONE).input('W', ItemTags.PLANKS)
						.pattern("SS")
						.pattern("WW")
						.pattern("WW")
						.criterion("has_planks", this.conditionsFromTag(ItemTags.PLANKS))
						.offerTo(recipeExporter);
				
				USE_VANILLA = false;
				//  END OF generate()
			}

            private void offerCampfireRecipe() {
                ShapedRecipeJsonBuilder builder = this.createShaped(RecipeCategory.DECORATIONS, Blocks.CAMPFIRE);
                ((ShapedRecipeJsonBuilderAccess) builder).durability_rework$componentChanges(
                        ComponentChanges.builder().add(
                                DataComponentTypes.BLOCK_STATE,
                                BlockStateComponent.DEFAULT.with(Properties.LIT, false)
                                ).build()
                );
                builder.input('S', Items.STICK).input('T', DrItems.TINDER).input('L', ItemTags.LOGS)
                        .pattern("SSS")
		                .pattern("LTL")
		                .pattern("LLL")
                        .criterion("has_stick", this.conditionsFromItem(Items.STICK))
                        .criterion("has_tinder", this.conditionsFromItem(DrItems.TINDER))
                        .offerTo(recipeExporter);
            }
			
			private void prepareEquipmentBuilder(EquipmentFamily family, EquipmentFamily.Variant variant,List<String> patterns) {
				Item output = family.getVariantItem(variant);
				ShapedRecipeJsonBuilder builder = this.createShaped(equipmentItemCategory(output.getDefaultStack()), output);
				if (family.getMaterialTag() != null) {
					builder.input('M', family.getMaterialTag());
				} else {
					builder.input('M', family.getMaterial());
				}
				if (!variant.isArmor()) {
					builder.input('S', Items.STICK);
				}
				for (String pattern : patterns) {
					builder.pattern(pattern);
				}
				builder.criterion("has_" + getItemPath(output), this.conditionsFromItem(output));
				builder.group(getItemPath(output));
				builder.offerTo(recipeExporter);
			}
			
			private void offerWoodenEquipmentVariantRecipes(EquipmentFamily family, EquipmentFamily.Variant variant) {
				Map<Character, Ingredient> inputs = new HashMap<>();
				Item material = family.getMaterial();
				Item variantItem = family.getVariantItem(variant);
				ItemStack variantStack = variantItem.getDefaultStack();
				
				WoodType.stream().forEach(woodType -> {
					String woodTypeName = woodType.name();
					String woodTypeVariant = woodTypeName + "_" + getItemPath(variantItem);
					RegistryKey<Recipe<?>> recipeKey = RegistryKey.of(RegistryKeys.RECIPE, DurabilityMain.ofDurability(woodTypeVariant));
					Item plank = Registries.ITEM.get(Identifier.of(woodTypeName + "_planks"));
					//  Sets the translation of the item to match the wood type
					variantStack.set(DataComponentTypes.ITEM_NAME, Text.translatable("item.durability_rework." + woodTypeVariant));
					
					//  Add material to ingredients
					inputs.put('M', Ingredient.ofItem(plank));
					
					//  If the variant is armor, the equippable component is added
					if (variant.isArmor()) {
						EquippableComponent.Builder builder = EquippableComponent.builder(EquipmentFamily.Variant.equipmentSlot(variant));
						RegistryKey<EquipmentAsset> assetKey = DrEquipmentAssetKeys.parseWoodenKey(woodTypeName);
						variantStack.set(DataComponentTypes.EQUIPPABLE, builder.model(assetKey).build());
					} else {
						inputs.put('S', Ingredient.ofItem(Items.STICK));
					}
					
					//  Build the recipe advancement
					Advancement.Builder builder = recipeExporter.getAdvancementBuilder()
							.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
							.criterion("has_" + getItemPath(material), this.conditionsFromItem(plank))
							.rewards(AdvancementRewards.Builder.recipe(recipeKey))
							.criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
					
					RecipeCategory category = variant.isArmor() || variant == EquipmentVariants.SWORD ? RecipeCategory.COMBAT : RecipeCategory.TOOLS;
					recipeExporter.accept(
							RegistryKey.of(RegistryKeys.RECIPE, DurabilityMain.ofDurability(woodTypeVariant)),
							new ShapedRecipe(
									getItemPath(variantItem),
									CraftingRecipeJsonBuilder.toCraftingCategory(variant.variantCategory()),
									RawShapedRecipe.create(
											inputs,
											variant.getCraftingLayout()
									),
									variantStack
							),
							//  ADVANCEMENT BROKEN
							builder.build(DurabilityMain.ofDurability(
									recipeKey.getValue().getPath()).withPrefixedPath("recipes/" + category.getName() + "/")
							)
					);
				});
			}
			
			public void generateEquipmentFamilyUpgradeRecipes(EquipmentFamily family, EquipmentFamily.Variant variant, Item item) {
				if (getEquipmentUpgrade(family, variant) != Items.AIR) {
					Item output = getEquipmentUpgrade(family, variant);
//					this.offerUpgradeEquipmentSmithing(
//							family.getSmithingTemplate(),
//							item,
//							family.getUpgradeMaterial(),
//							//ItemTags.WOODEN_TOOL_MATERIALS,
//							output,
//							ComponentChanges.builder().add(DataComponentTypes.ITEM_NAME, Text.translatable(output.getTranslationKey()))
//					);
//				} else {
					throw new RuntimeException("Could not generate equipment family upgrade recipes due to not finding the item to upgrade to");
				}
			}
			
			private Item getEquipmentUpgrade(EquipmentFamily family, EquipmentFamily.Variant variant) {
				Item output = Items.AIR;
				String itemPath = Registries.ITEM.getId(family.getMaterial()).getPath();
				switch(itemPath) {
					case "oak_planks" -> output = EquipmentFamilies.STONE.getVariantItem(variant);
					case "stone" -> output = EquipmentFamilies.COPPER.getVariantItem(variant);
					case "copper_ingot" -> output = EquipmentFamilies.IRON.getVariantItem(variant);
					case "iron_ingot" -> output = EquipmentFamilies.GOLDEN.getVariantItem(variant);
					case "gold_ingot" -> output = EquipmentFamilies.DIAMOND.getVariantItem(variant);
				}
				return output;
			}
			
			public void offerEquipmentTierUpgradeSmithingRecipe(Item template, Item input, Item addition, Item output) {
				this.offerEquipmentTierUpgradeSmithingRecipe(template, input, addition, output, ComponentChanges.builder());
			}
			
			public void offerEquipmentTierUpgradeSmithingRecipe(Item template, Item input, Item addition, Item output, ComponentChanges.Builder componentChanges) {
				RegistryKey<Recipe<?>> recipeKey = RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(getItemPath(output) + "_smithing_upgrade"));
				recipeExporter.accept(
						recipeKey,
						new SmithingTransformRecipe(
								Optional.of(Ingredient.ofItem(template)),
								DefaultCustomIngredients.components(
										Ingredient.ofItem(input),
										ComponentChanges.builder()
												.add(DrDataComponentTypes.LEVEL, input.getDefaultStack().get(DrDataComponentTypes.MAX_LEVEL))
												.build()
								),
								Optional.of(Ingredient.ofItem(addition)),
								new TransmuteRecipeResult(
										Registries.ITEM.getEntry(output),
										1,
										componentChanges.add(DrDataComponentTypes.LEVEL, 0).build()
								)
						),
						//  ADD ADVANCEMENT
						null
				);
			}
			
			public void offerEquipmentUpgradeSmithingRecipe(Item template, Item input, TagKey<Item> materialTag, Item output) {
				SmithingTransformRecipeJsonBuilder.create(
						Ingredient.ofItem(template),
						DefaultCustomIngredients.components(
								Ingredient.ofItem(input),
								ComponentChanges.builder()
										.add(DrDataComponentTypes.LEVEL, input.getDefaultStack().get(DrDataComponentTypes.MAX_LEVEL))
										.build()
						),
						this.ingredientFromTag(materialTag),
						input.getDefaultStack().contains(DataComponentTypes.WEAPON) ? RecipeCategory.COMBAT : RecipeCategory.TOOLS,
						output
				).criterion("has_upgrade_material", this.conditionsFromTag(materialTag)).offerTo(recipeExporter, getItemPath(output) + "_smithing_upgrade");
			}
			
			public void offerSmithingTemplateRecipe() {
				this.createShaped(RecipeCategory.MISC, DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE, 1)
						.input('F', Items.IRON_INGOT).input('T', Items.DIAMOND_BLOCK).input('E', Items.DEEPSLATE)
						.pattern("FTF")
						.pattern("FEF")
						.pattern("FFF")
//						.group(getItemPath(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE))
						.criterion("has_" + getItemPath(Items.DIAMOND), this.conditionsFromItem(Items.DIAMOND))
						.offerTo(recipeExporter, getItemPath(DrItems.DIAMOND_UPGRADE_SMITHING_TEMPLATE));
			}
			
			public void offerSmithingTemplateRecipe(Ingredient material) {
				this.createShaped(RecipeCategory.MISC, DrItems.STONE_UPGRADE_SMITHING_TEMPLATE, 1)
						.input('M', Items.SMOOTH_STONE).input('S', Items.STONE)
						.input('W', DrItemTags.WOOD).input('L', DrItemTags.LOGS)
						.pattern("LML")
						.pattern("LSL")
						.pattern("WWW")
						.criterion("has_cobblestone", this.conditionsFromItem(Items.COBBLESTONE))
						.offerTo(recipeExporter, getItemPath(DrItems.STONE_UPGRADE_SMITHING_TEMPLATE));
			}
			
		};//    Pre-made Recipe Builders
	}//  END OF RecipeGenerator

	public static RecipeCategory equipmentItemCategory(EquipmentFamily family, EquipmentFamily.Variant variant) {
		boolean combatWeapon = family.getVariantItem(variant).getDefaultStack().isIn(ItemTags.WEAPON_ENCHANTABLE)
				&& !family.getVariantItem(variant).getDefaultStack().isIn(ItemTags.AXES);
		if (combatWeapon || variant.isArmor()) {
			return RecipeCategory.COMBAT;
		} else {
			return RecipeCategory.TOOLS;
		}
	}
	
	public static RecipeCategory equipmentItemCategory(ItemStack item) {
		//  THIS SUCKS LMAO!!!!
		boolean combatItem = item.getDefaultComponents().contains(DataComponentTypes.EQUIPPABLE) || item.getItemName().toString().contains("sword");
		if (combatItem) {
			return RecipeCategory.COMBAT;
		} else {
			return RecipeCategory.TOOLS;
		}
	}
	
	@Override
	protected Identifier getRecipeIdentifier(Identifier identifier) {
		if (USE_VANILLA) {
			return Identifier.ofVanilla(identifier.getPath());
		} else {
			return Identifier.of(output.getModId(), identifier.getPath());
		}
	}
	
	@Override
	public String getName() {
		return "Recipes";
	}
}
