package net.jineric.durability_rework.recipe;

import net.jineric.durability_rework.recipe.input.AssemblyRecipeInput;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.world.World;

import java.util.Optional;

public interface AssembleRecipe extends Recipe<AssemblyRecipeInput> {
	@Override
	default RecipeType<? extends Recipe<AssemblyRecipeInput>> getType() {
		return DrRecipeType.ASSEMBLY_RECIPE;
	}
	
	@Override
	RecipeSerializer<? extends AssembleRecipe> getSerializer();
	
	default boolean matches(AssemblyRecipeInput input, World world) {
		return this.material().test(input.material())
				&& Ingredient.matches(this.firstBase(), input.firstBase())
				&& Ingredient.matches(this.secondBase(), input.secondBase());
	};
	
	Ingredient material();
	
	Optional<Ingredient> firstBase();
	
	Optional<Ingredient> secondBase();
	
	@Override
	default RecipeBookCategory getRecipeBookCategory() {
		//WRONG
		return RecipeBookCategories.SMITHING;
	}
}
