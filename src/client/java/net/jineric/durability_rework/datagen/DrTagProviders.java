package net.jineric.durability_rework.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jineric.durability_rework.DrConfig;
import net.jineric.durability_rework.registry.tag.DrBlockTags;
import net.jineric.durability_rework.registry.tag.DrEntityTypeTags;
import net.jineric.durability_rework.registry.tag.DrItemTags;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DrTagProviders {
	public static class DrItemTagProvider extends FabricTagProvider.ItemTagProvider {
		public DrItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, @Nullable BlockTagProvider blockTagProvider) {
			super(output, registriesFuture, blockTagProvider);
		}
		
		public DrItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//			this.valueLookupBuilder(DrItemTags.UPGRADES_STONE_EQUIPMENT).add(Items.COPPER_INGOT);
//			this.valueLookupBuilder(DrItemTags.UPGRADES_STONE_EQUIPMENT).add(Items.STONE);
			
			this.builder(DrItemTags.REPAIRS_WOODEN_ARMOR).forceAddTag(ItemTags.PLANKS);
			this.valueLookupBuilder(DrItemTags.TINDER_MATERIALS).add(Items.FEATHER, Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);
			
			this.valueLookupBuilder(DrItemTags.WOODEN_EQUIPMENT).add(Items.WOODEN_PICKAXE, Items.WOODEN_SWORD, Items.WOODEN_AXE, Items.WOODEN_HOE, Items.WOODEN_SHOVEL);
			this.valueLookupBuilder(DrItemTags.STONE_EQUIPMENT).add(Items.STONE_PICKAXE, Items.STONE_SWORD, Items.STONE_AXE, Items.STONE_HOE, Items.STONE_SHOVEL);
//		this.valueLookupBuilder(DrItemTags.COPPER_EQUIPMENT).add(Items.COPPER_PICKAXE, Items.COPPER_SWORD, Items.COPPER_AXE, Items.COPPER_HOE, Items.COPPER_SHOVEL);
			this.valueLookupBuilder(DrItemTags.IRON_EQUIPMENT).add(Items.IRON_PICKAXE, Items.IRON_SWORD, Items.IRON_AXE, Items.IRON_HOE, Items.IRON_SHOVEL);
			this.valueLookupBuilder(DrItemTags.GOLD_EQUIPMENT).add(Items.GOLDEN_PICKAXE, Items.GOLDEN_SWORD, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.GOLDEN_SHOVEL);
			this.valueLookupBuilder(DrItemTags.DIAMOND_EQUIPMENT).add(Items.DIAMOND_PICKAXE, Items.DIAMOND_SWORD, Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_SHOVEL);
			
			this.valueLookupBuilder(DrItemTags.STRIPPED_WOOD).add(
					Items.STRIPPED_ACACIA_WOOD, Items.STRIPPED_BIRCH_WOOD, Items.STRIPPED_CHERRY_WOOD,
					Items.STRIPPED_CRIMSON_HYPHAE, Items.STRIPPED_DARK_OAK_WOOD, Items.STRIPPED_JUNGLE_WOOD,
					Items.STRIPPED_MANGROVE_WOOD, Items.STRIPPED_OAK_WOOD, Items.STRIPPED_PALE_OAK_WOOD,
					Items.STRIPPED_SPRUCE_WOOD, Items.STRIPPED_WARPED_HYPHAE
			);
			this.valueLookupBuilder(DrItemTags.WOOD).add(
					Items.ACACIA_WOOD, Items.BIRCH_WOOD, Items.CHERRY_WOOD,
					Items.CRIMSON_HYPHAE, Items.DARK_OAK_WOOD, Items.JUNGLE_WOOD,
					Items.MANGROVE_WOOD, Items.OAK_WOOD, Items.PALE_OAK_WOOD,
					Items.SPRUCE_WOOD, Items.WARPED_HYPHAE
			).addTag(DrItemTags.STRIPPED_WOOD);
			
			this.valueLookupBuilder(DrItemTags.STRIPPED_LOGS).add(
					Items.STRIPPED_ACACIA_LOG, Items.STRIPPED_BIRCH_LOG, Items.STRIPPED_CHERRY_LOG,
					Items.STRIPPED_CRIMSON_HYPHAE, Items.STRIPPED_DARK_OAK_LOG, Items.STRIPPED_JUNGLE_LOG,
					Items.STRIPPED_MANGROVE_LOG, Items.STRIPPED_OAK_LOG, Items.STRIPPED_PALE_OAK_LOG,
					Items.STRIPPED_SPRUCE_LOG, Items.STRIPPED_WARPED_HYPHAE
			);
			this.valueLookupBuilder(DrItemTags.LOGS).add(
					Items.ACACIA_LOG, Items.BIRCH_LOG, Items.CHERRY_LOG,
					Items.CRIMSON_HYPHAE, Items.DARK_OAK_LOG, Items.JUNGLE_LOG,
					Items.MANGROVE_LOG, Items.OAK_LOG, Items.PALE_OAK_LOG,
					Items.SPRUCE_LOG, Items.WARPED_HYPHAE
			).addTag(DrItemTags.STRIPPED_LOGS);
			
			this.valueLookupBuilder(DrItemTags.CUT_COPPER)
					.add(Items.CUT_COPPER, Items.WAXED_CUT_COPPER)
					.add(Items.EXPOSED_CUT_COPPER, Items.WAXED_EXPOSED_CUT_COPPER)
					.add(Items.WEATHERED_CUT_COPPER, Items.WAXED_WEATHERED_CUT_COPPER)
					.add(Items.OXIDIZED_CUT_COPPER, Items.OXIDIZED_CUT_COPPER)
			;
		}
	}
	
	public static class DrBlockTagProvider extends FabricTagProvider.BlockTagProvider {
		public DrBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			if (DrConfig.MODIFY_TOOL_MINABLE) {
				this.valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).forceAddTag(BlockTags.SHOVEL_MINEABLE);
				this.valueLookupBuilder(BlockTags.AXE_MINEABLE).forceAddTag(BlockTags.HOE_MINEABLE);
			}
			
			this.valueLookupBuilder(BlockTags.HOE_MINEABLE).forceAddTag(BlockTags.CROPS);
			
			//  Level Boosts
			this.valueLookupBuilder(DrBlockTags.BOOSTS_WOODEN_EQUIPMENT_LEVEL).add();
			this.valueLookupBuilder(DrBlockTags.BOOSTS_STONE_EQUIPMENT_LEVEL).add();
			this.valueLookupBuilder(DrBlockTags.BOOSTS_COPPER_EQUIPMENT_LEVEL).add();
			this.valueLookupBuilder(DrBlockTags.BOOSTS_IRON_EQUIPMENT_LEVEL).add();
			this.valueLookupBuilder(DrBlockTags.BOOSTS_GOLD_EQUIPMENT_LEVEL).add();
			this.valueLookupBuilder(DrBlockTags.BOOSTS_DIAMOND_EQUIPMENT_LEVEL).add();
			this.valueLookupBuilder(DrBlockTags.BOOSTS_NETHERITE_EQUIPMENT_LEVEL).add();
		}
	}
	
	public static class DrEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
		public DrEntityTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			//  Non-farm animals which provide no value for killing, or are passive.
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON_NONE)
					.add(EntityType.ALLAY)
					.add(EntityType.ARMADILLO)
					.add(EntityType.AXOLOTL)
					.add(EntityType.BAT)
					.add(EntityType.BEE)
					.add(EntityType.CAMEL)
					.add(EntityType.CAT)
					.add(EntityType.DOLPHIN)
					.add(EntityType.DONKEY)
					.add(EntityType.FOX)
					.add(EntityType.FROG)
					.add(EntityType.GOAT)
					.add(EntityType.HORSE)
					.add(EntityType.LLAMA)
					.add(EntityType.MULE)
					.add(EntityType.OCELOT)
					.add(EntityType.PANDA)
					.add(EntityType.PARROT)
					.add(EntityType.POLAR_BEAR)
					.add(EntityType.SNIFFER)
					.add(EntityType.SNOW_GOLEM)
					.add(EntityType.STRIDER)
					.add(EntityType.TADPOLE)
					.add(EntityType.TRADER_LLAMA)
					.add(EntityType.TROPICAL_FISH)
					.add(EntityType.TURTLE)
					.add(EntityType.VILLAGER)
					.add(EntityType.WOLF)
			;
			//  Farm animals or easy to kill mobs
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON_LOW)
					.add(EntityType.CHICKEN)
					.add(EntityType.COD)
					.add(EntityType.COW)
					.add(EntityType.GLOW_SQUID)
					.add(EntityType.MOOSHROOM)
					.add(EntityType.PIG)
					.add(EntityType.PUFFERFISH)
					.add(EntityType.RABBIT)
					.add(EntityType.SALMON)
					.add(EntityType.SHEEP)
					.add(EntityType.SILVERFISH)
					.add(EntityType.SQUID)
					//  Wandering trader is only here because it's funny. Kill all traders.
					.add(EntityType.WANDERING_TRADER)
					.add(EntityType.ZOMBIE_HORSE)
			;
			//  Mobs that are hostile but relatively easy to kill
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON_MEDIUM)
					.add(EntityType.ZOMBIE)
					.add(EntityType.DROWNED)
					.add(EntityType.ZOMBIE_VILLAGER)
					.add(EntityType.ZOMBIFIED_PIGLIN)
					.add(EntityType.SKELETON)
					.add(EntityType.PILLAGER)
					.add(EntityType.PIGLIN)
					.add(EntityType.CREEPER)
					.add(EntityType.SPIDER)
					.add(EntityType.PHANTOM)
					.add(EntityType.SLIME)
					.add(EntityType.ENDERMITE)
					.add(EntityType.VEX)
			;
			//  Mobs that do high damage or provide some kind of difficulty to kill (like applying an affect to the player on attack)
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON_MEDIUM_PLUS)
					.add(EntityType.STRAY)
					.add(EntityType.BOGGED)
					.add(EntityType.WITCH)
					.add(EntityType.BLAZE)
					.add(EntityType.GUARDIAN)
					.add(EntityType.HUSK)
					.add(EntityType.WITHER_SKELETON)
					.add(EntityType.CAVE_SPIDER)
					.add(EntityType.VINDICATOR)
					.add(EntityType.GHAST)
					.add(EntityType.HOGLIN)
					.add(EntityType.ZOGLIN)
					.add(EntityType.BREEZE)
					.add(EntityType.MAGMA_CUBE)
					.add(EntityType.ENDERMAN)
			;
			//  Mobs with high health-pools that pose a major threat or are difficult to kill
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON_HIGH)
					.add(EntityType.IRON_GOLEM)
					.add(EntityType.RAVAGER)
					.add(EntityType.SHULKER)
					.add(EntityType.PIGLIN_BRUTE)
					.add(EntityType.EVOKER)
			;
			//  Reserved for "Boss" mobs or very rare mobs
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON_EXTREME)
					.add(EntityType.ELDER_GUARDIAN)
					.add(EntityType.ENDER_DRAGON)
					.add(EntityType.WARDEN)
			;
			//  Entities that can increase the level of a weapon
			this.valueLookupBuilder(DrEntityTypeTags.LEVELS_WEAPON)
					.addTag(DrEntityTypeTags.LEVELS_WEAPON_LOW)
					.addTag(DrEntityTypeTags.LEVELS_WEAPON_MEDIUM)
					.addTag(DrEntityTypeTags.LEVELS_WEAPON_MEDIUM_PLUS)
					.addTag(DrEntityTypeTags.LEVELS_WEAPON_HIGH)
					.addTag(DrEntityTypeTags.LEVELS_WEAPON_EXTREME)
			;
		}
	}
}