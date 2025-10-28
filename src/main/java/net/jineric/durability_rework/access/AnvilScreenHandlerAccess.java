package net.jineric.durability_rework.access;

import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.recipe.display.CuttingRecipeDisplay;

public interface AnvilScreenHandlerAccess {
	
	default int getSelectedRecipe() {
		throw new RuntimeException("AnvilScreenHandlerAccess failed!");
	}
	
	default CuttingRecipeDisplay.Grouping<StonecuttingRecipe> getAvailableRecipes() {
		throw new RuntimeException("AnvilScreenHandlerAccess failed!");
	}
	
	default int getAvailableRecipeCount() {
		throw new RuntimeException("AnvilScreenHandlerAccess failed!");
	}
	
	default boolean canCraft() {
		throw new RuntimeException("AnvilScreenHandlerAccess failed!");
	}
}
