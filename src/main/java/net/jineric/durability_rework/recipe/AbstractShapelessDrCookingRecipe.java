package net.jineric.durability_rework.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;

import java.util.List;

public abstract class AbstractShapelessDrCookingRecipe extends ShapelessRecipe {
//	private final CookingRecipeCategory category;
	final List<Ingredient> ingredients;
	private final float experience;
	private final int cookingTime;
	
	public AbstractShapelessDrCookingRecipe(String group, CraftingRecipeCategory category, ItemStack result, List<Ingredient> ingredients, List<Ingredient> ingredients1, float experience, int cookingTime) {
		super(group, category, result, ingredients);
//		this.category = category;
		this.ingredients = ingredients1;
		this.experience = experience;
		this.cookingTime = cookingTime;
	}
	
	@Override
	public abstract RecipeSerializer<ShapelessRecipe> getSerializer();
	
	@Override
	public abstract RecipeType<CraftingRecipe> getType();
	
	public float getExperience() {
		return experience;
	}
	
	public int getCookingTime() {
		return cookingTime;
	}
	
	protected abstract Item getCookerItem();
}
