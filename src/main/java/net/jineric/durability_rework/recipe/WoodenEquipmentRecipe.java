package net.jineric.durability_rework.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WoodenEquipmentRecipe extends SpecialCraftingRecipe {
	
	private CraftingRecipeInput input;
	private ItemStack output;
	
	
	public WoodenEquipmentRecipe(CraftingRecipeCategory category) {
		super(category);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		return false;
//		this.input = input;
//		boolean matchSlots = false;
//		if (input.getWidth() == 3) {
//			if (input.getHeight() == 3) {
//				if (this.isChestplate()) {
//					this.output = new ItemStack(DrItems.WOODEN_CHESTPLATE);
//					matchSlots = true;
//				}
//				if (this.isLeggings()) {
//					this.output = new ItemStack(DrItems.WOODEN_LEGGINGS);
//					matchSlots = true;
//
//				}
//			} else if (input.getHeight() == 2) {
//				if (this.isHelmet()) {
//					this.output = new ItemStack(DrItems.WOODEN_HELMET);
//					matchSlots = true;
//
//				}
//				if (this.isBoots()) {
//					this.output = new ItemStack(DrItems.WOODEN_BOOTS);
//					matchSlots = true;
//
//				}
//			}
//		}
//
//		if (matchSlots) {
//			for (int x=0; x < input.getWidth(); x++) {
//				for (int y=0; y < input.getHeight(); y++) {
//					ItemStack slotItem = input.getStackInSlot(x, y);
//					if (!slotItem.isIn(ItemTags.PLANKS) && !slotItem.isEmpty()) {
//						return false;
//					}
//				}
//			}
//			for (ItemStack requiredItem : this.requiredItems()) {
//				if (!requiredItem.isOf(this.requiredItems().getFirst().getItem())) {
//					return false;
//				}
//			}
//			return true;
//		}
//		return false;
	}
	
	private boolean isHelmet() {
		return this.input.getStackInSlot(1, 1).isEmpty()
				&& this.requiredItems().size() == 5;
	}
	
	private boolean isChestplate() {
		return this.input.getStackInSlot(1, 0).isEmpty() && this.requiredItems().size() == 8;
	}
	
	private boolean isLeggings() {
		return this.input.getStackInSlot(1, 1).isEmpty()
				&& this.input.getStackInSlot(1, 2).isEmpty()
				&& this.requiredItems().size() == 7;
	}
	
	private boolean isBoots() {
		return this.input.getStackInSlot(1, 0).isEmpty()
				&& this.input.getStackInSlot(1, 1).isEmpty()
				&& this.requiredItems().size() == 4;
	}
	
	private List<ItemStack> requiredItems() {
		List<ItemStack> materials = new ArrayList<>();
		for (ItemStack stack : this.input.getStacks()) {
			if (!stack.isEmpty()) {
				materials.add(stack);
			}
		}
		return materials;
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
		
		return this.output;
	}
	
	@Override
	public RecipeSerializer<? extends SpecialCraftingRecipe> getSerializer() {
		return DrRecipeSerializer.EQUIPMENT_VARIANT;
	}
	
	@Override
	public CraftingRecipeCategory getCategory() {
		return CraftingRecipeCategory.EQUIPMENT;
	}
}
