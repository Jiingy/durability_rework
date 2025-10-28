package net.jineric.durability_rework.recipe.input;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record AssemblyRecipeInput(ItemStack material, ItemStack firstBase, ItemStack secondBase) implements RecipeInput {
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return switch (slot) {
			case 0 -> this.material;
			case 1 -> this.firstBase;
			case 2 -> this.secondBase;
			default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
		};
	}
	
	@Override
	public int size() {
		return 3;
	}
	
	@Override
	public boolean isEmpty() {
		return this.material.isEmpty()
				&& this.firstBase.isEmpty()
				&& this.secondBase.isEmpty();
	}
}
