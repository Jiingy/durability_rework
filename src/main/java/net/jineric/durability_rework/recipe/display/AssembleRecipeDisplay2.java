package net.jineric.durability_rework.recipe.display;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.display.SlotDisplay;

import java.util.List;
import java.util.Optional;

public record AssembleRecipeDisplay2<T extends Recipe<?>>(SlotDisplay optionDisplay, Optional<RecipeEntry<T>> recipe) {
	
	public static <T extends Recipe<?>> PacketCodec<RegistryByteBuf, AssembleRecipeDisplay2<T>> codec() {
		return PacketCodec.tuple(
				SlotDisplay.PACKET_CODEC,
				AssembleRecipeDisplay2::optionDisplay,
				display -> new AssembleRecipeDisplay2<>(display, Optional.empty())
		);
	}
	
	public record GroupEntry<T extends Recipe<?>>(ItemStack material, ItemStack firstBase, AssembleRecipeDisplay2<T> recipe) {
		
		public static <T extends Recipe<?>> PacketCodec<RegistryByteBuf, GroupEntry<T>> codec() {
			return PacketCodec.tuple(
					ItemStack.PACKET_CODEC,
					GroupEntry::material,
					ItemStack.PACKET_CODEC,
					GroupEntry::firstBase,
					AssembleRecipeDisplay2.codec(),
					GroupEntry::recipe,
					GroupEntry::new
			);
		}
	}
	
	public record Grouping<T extends Recipe<?>> (List<GroupEntry<T>> entries) {
		
//		public static <T extends Recipe<?>> PacketCodec<RegistryByteBuf, Grouping<T>> codec() {
//			return PacketCodec.tuple(
//					GroupEntry.codec().collect(PacketCodecs.toList()),
//					Grouping::entries,
//					Grouping::new
//			);
//		}
		
		public static <T extends Recipe<?>> Grouping<T> empty() {
			return new Grouping<>(List.of());
		}
		
//		public boolean contains(ItemStack stack) {
//			return this.entries.stream().anyMatch(entry -> entry.input.test(stack));
//		}
//
//		public Grouping<T> filter(ItemStack stack) {
//			return new Grouping<>(this.entries.stream().filter(entry -> entry.input.test(stack)).toList());
//		}
		
		public boolean isEmpty() {
			return this.entries.isEmpty();
		}
		
		public int size() {
			return this.entries.size();
		}
	}
}
