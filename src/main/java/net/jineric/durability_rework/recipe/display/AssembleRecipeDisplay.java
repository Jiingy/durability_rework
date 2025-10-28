package net.jineric.durability_rework.recipe.display;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;

public record AssembleRecipeDisplay<T extends Recipe<?>>(SlotDisplay material, SlotDisplay firstBase, SlotDisplay secondBase, SlotDisplay result, SlotDisplay craftingStation)
		implements RecipeDisplay {
	
	public static final MapCodec<AssembleRecipeDisplay> CODEC = RecordCodecBuilder.mapCodec(
			assembleRecipeDisplayInstance -> assembleRecipeDisplayInstance.group(
					SlotDisplay.CODEC.fieldOf("material").forGetter(AssembleRecipeDisplay::material),
					SlotDisplay.CODEC.fieldOf("firstBase").forGetter(AssembleRecipeDisplay::firstBase),
					SlotDisplay.CODEC.fieldOf("secondBase").forGetter(AssembleRecipeDisplay::secondBase),
					SlotDisplay.CODEC.fieldOf("result").forGetter(AssembleRecipeDisplay::result),
					SlotDisplay.CODEC.fieldOf("craftingStation").forGetter(AssembleRecipeDisplay::craftingStation)
			)
			.apply(assembleRecipeDisplayInstance, AssembleRecipeDisplay::new)
	);
	
	public static PacketCodec<RegistryByteBuf, AssembleRecipeDisplay> PACKET_CODEC = PacketCodec.tuple(
			SlotDisplay.PACKET_CODEC, AssembleRecipeDisplay::material,
			SlotDisplay.PACKET_CODEC, AssembleRecipeDisplay::firstBase,
			SlotDisplay.PACKET_CODEC, AssembleRecipeDisplay::secondBase,
			SlotDisplay.PACKET_CODEC, AssembleRecipeDisplay::result,
			SlotDisplay.PACKET_CODEC, AssembleRecipeDisplay::craftingStation,
			AssembleRecipeDisplay::new
	);
	
	public static final Serializer<AssembleRecipeDisplay> SERIALIZER = new Serializer<>(CODEC, PACKET_CODEC);
	
	@Override
	public Serializer<? extends RecipeDisplay> serializer() {
		return SERIALIZER;
	}
}
