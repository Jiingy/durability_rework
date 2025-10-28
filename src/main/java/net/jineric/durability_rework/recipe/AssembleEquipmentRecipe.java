package net.jineric.durability_rework.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jineric.durability_rework.item.DrItems;
import net.jineric.durability_rework.recipe.display.AssembleRecipeDisplay;
import net.jineric.durability_rework.recipe.input.AssemblyRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AssembleEquipmentRecipe implements AssembleRecipe {
	final Ingredient material;
	final Optional<Ingredient> firstBase;
	final Optional<Ingredient> secondBase;
	final ItemStack result;
	@Nullable
	private IngredientPlacement ingredientPlacement;
	
	public AssembleEquipmentRecipe(Ingredient material, Optional<Ingredient> firstBase, Optional<Ingredient> secondBase, ItemStack result) {
		this.material = material;
		this.firstBase = firstBase;
		this.secondBase = secondBase;
		this.result = result;
	}
	
	public ItemStack craft(AssemblyRecipeInput input, RegistryWrapper.WrapperLookup registries) {
		return result.copy();
	}
	
	@Override
	public Ingredient material() {
		return this.material;
	}
	
	@Override
	public Optional<Ingredient> firstBase() {
		return this.firstBase;
	}
	
	@Override
	public Optional<Ingredient> secondBase() {
		return this.secondBase;
	}
	
	@Override
	public RecipeSerializer<AssembleEquipmentRecipe> getSerializer() {
		return DrRecipeSerializer.ASSEMBLE;
	}
	
	@Override
	public IngredientPlacement getIngredientPlacement() {
		if (this.ingredientPlacement == null) {
			this.ingredientPlacement = IngredientPlacement.forMultipleSlots(List.of(Optional.of(this.material), this.firstBase, this.secondBase));
		}
		return this.ingredientPlacement;
	}
	
	@Override
	public List<RecipeDisplay> getDisplays() {
		return List.of(
				new AssembleRecipeDisplay<>(
						this.material.toDisplay(),
						Ingredient.toDisplay(this.firstBase),
						Ingredient.toDisplay(this.secondBase),
						new SlotDisplay.StackSlotDisplay(this.result),
						new SlotDisplay.ItemSlotDisplay(DrItems.WORK_BENCH)
				)
		);
	}
	
	public static class Serializer implements RecipeSerializer<AssembleEquipmentRecipe> {
		private static final MapCodec<AssembleEquipmentRecipe> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Ingredient.CODEC.fieldOf("material").forGetter(recipe -> recipe.material),
						Ingredient.CODEC.optionalFieldOf("firstBase").forGetter(recipe -> recipe.firstBase),
						Ingredient.CODEC.optionalFieldOf("secondBase").forGetter(recipe -> recipe.secondBase),
						ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
				)
				.apply(instance, AssembleEquipmentRecipe::new)
		);
		public static final PacketCodec<RegistryByteBuf, AssembleEquipmentRecipe> PACKET_CODEC = PacketCodec.tuple(
				Ingredient.PACKET_CODEC,
				recipe -> recipe.material,
				Ingredient.OPTIONAL_PACKET_CODEC,
				recipe -> recipe.firstBase,
				Ingredient.OPTIONAL_PACKET_CODEC,
				recipe -> recipe.secondBase,
				ItemStack.PACKET_CODEC,
				recipe -> recipe.result,
				AssembleEquipmentRecipe::new
		);
		
		@Override
		public MapCodec<AssembleEquipmentRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, AssembleEquipmentRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
}
