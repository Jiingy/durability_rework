package net.jineric.durability_rework.recipe.input;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public class CrucibleSmeltingInput implements RecipeInput {
	public final List<ItemStack> stacks;
	
	public CrucibleSmeltingInput(List<ItemStack> stacks) {
		this.stacks = stacks;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.stacks.get(slot);
	}
	
	@Override
	public int size() {
		return 9;
	}
}
