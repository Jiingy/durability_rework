package net.jineric.durability_rework.recipe;

import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface DrRecipeSerializer<T extends Recipe<?>> {
	RecipeSerializer<AssembleEquipmentRecipe> ASSEMBLE = register("assemble", new AssembleEquipmentRecipe.Serializer());
//	RecipeSerializer<AbstractShapelessDrCookingRecipe> SMELTING = register("smelting", new AbstractCookingRecipe.Serializer());
	RecipeSerializer<CrucibleSmeltingRecipe> CRUCIBLE_SMELTING = register("crucible_smelting", new CrucibleSmeltingRecipe.Serializer());
	
	RecipeSerializer<WoodenEquipmentRecipe> EQUIPMENT_VARIANT = register("equipment_variant", new SpecialCraftingRecipe.SpecialRecipeSerializer<>(WoodenEquipmentRecipe::new));
	
	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
	}
	
	static void initialize() {
	}
}
