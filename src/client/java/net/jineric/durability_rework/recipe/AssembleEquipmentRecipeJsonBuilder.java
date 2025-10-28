package net.jineric.durability_rework.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class AssembleEquipmentRecipeJsonBuilder {
	private final Ingredient material;
	private final Ingredient firstBase;
	private final Ingredient secondBase;
	private final RecipeCategory category;
	private final ItemStack result;
	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
	
	public AssembleEquipmentRecipeJsonBuilder(
			Ingredient material, Ingredient firstBase, Ingredient secondBase,
			RecipeCategory category, ItemStack result
	) {
		this.category = category;
		this.material = material;
		this.firstBase = firstBase;
		this.secondBase = secondBase;
		this.result = result;
	}
	
	public static AssembleEquipmentRecipeJsonBuilder create(
			Ingredient material, Ingredient firstBase, Ingredient secondBase,
			RecipeCategory category, ItemStack result
	) {
		return new AssembleEquipmentRecipeJsonBuilder(material, firstBase, secondBase, category, result);
	}
	
	public AssembleEquipmentRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
		this.criteria.put(name, criterion);
		return this;
	}
	
	public void offerTo(RecipeExporter exporter, String recipeId) {
		this.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, Identifier.of(recipeId)));
	}
	
	public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
		this.validate(recipeKey);
      Advancement.Builder builder = exporter.getAdvancementBuilder()
              .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey))
              .rewards(AdvancementRewards.Builder.recipe(recipeKey))
              .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
      this.criteria.forEach(builder::criterion);
		AssembleEquipmentRecipe recipeJsonBuilder = new AssembleEquipmentRecipe(
				this.material, Optional.of(this.firstBase), Optional.of(this.secondBase), this.result
		);
      exporter.accept(
			  recipeKey,
		      recipeJsonBuilder,
		      builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + this.category.getName() + "/"))
      );
	}
	
	private void validate(RegistryKey<Recipe<?>> recipeId) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + recipeId.getValue());
		}
	}
}
