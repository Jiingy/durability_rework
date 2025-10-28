package net.jineric.durability_rework.recipe;

import net.jineric.durability_rework.DurabilityMain;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DrRecipeType<T extends Recipe<?>> {
	public static final RecipeType<AssembleRecipe> ASSEMBLY_RECIPE = register("assembly");
	public static final RecipeType<AbstractShapelessDrCookingRecipe> SMELTING = register("smelting");
	public static final RecipeType<CrucibleSmeltingRecipe> CRUCIBLE_SMELTING = register("crucible_smelting");
	
	static <T extends Recipe<?>> RecipeType<T> register(String id) {
		return Registry.register(Registries.RECIPE_TYPE, DurabilityMain.ofDurability(id), new RecipeType<T>() {
			public String toString() {
				return id;
			}
		});
	}
	
	public static void init() {
	}
}
