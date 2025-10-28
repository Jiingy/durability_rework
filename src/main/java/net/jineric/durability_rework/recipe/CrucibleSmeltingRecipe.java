package net.jineric.durability_rework.recipe;

import com.mojang.serialization.MapCodec;
import net.jineric.durability_rework.recipe.input.CrucibleSmeltingInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public class CrucibleSmeltingRecipe implements Recipe<CrucibleSmeltingInput> {
	final String group;
	final ItemStack result;
	final List<Ingredient> ingredients;
	
	public CrucibleSmeltingRecipe(String group, ItemStack result, List<Ingredient> ingredients) {
		this.group = group;
		this.result = result;
		this.ingredients = ingredients;
	}
	
	@Override
	public RecipeSerializer<? extends Recipe<CrucibleSmeltingInput>> getSerializer() {
		return DrRecipeSerializer.CRUCIBLE_SMELTING;
	}
	
	@Override
	public String getGroup() {
		return this.group;
	}
	
	@Override
	public IngredientPlacement getIngredientPlacement() {
		return null;
	}
	
	@Override
	public boolean matches(CrucibleSmeltingInput input, World world) {
		return false;
	}
	
	@Override
	public ItemStack craft(CrucibleSmeltingInput input, RegistryWrapper.WrapperLookup registries) {
		return this.result.copy();
	}
	
	@Override
	public RecipeType<? extends Recipe<CrucibleSmeltingInput>> getType() {
		return null;
	}
	
	@Override
	public RecipeBookCategory getRecipeBookCategory() {
		return null;
	}
	
	
	public static class Serializer implements RecipeSerializer<CrucibleSmeltingRecipe> {
		
		@Override
		public MapCodec<CrucibleSmeltingRecipe> codec() {
			return null;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, CrucibleSmeltingRecipe> packetCodec() {
			return null;
		}
	}
}
