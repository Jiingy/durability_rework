package net.jineric.durability_rework.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.jineric.durability_rework.block.DrBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DrLootTableProviders {
	
	public static class DrBlockLootTableProvider extends FabricBlockLootTableProvider {
		protected DrBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
			super(dataOutput, registryLookup);
		}
		
		@Override
		public void generate() {
            //  Vanilla
            this.addDrop(Blocks.CAMPFIRE);
            //  Modded
			this.addDrop(DrBlocks.WORK_BENCH);
			this.addDrop(DrBlocks.STONE_CRUCIBLE);
			this.addDrop(DrBlocks.TINDER);
		}
	}
}
