package net.jineric.durability_rework.block;

import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.block.crucible.CrucibleBlock;
import net.jineric.durability_rework.block.work_bench.WorkBenchBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

import static net.minecraft.block.Blocks.CRAFTING_TABLE;

public class DrBlocks {
	public static final Block WORK_BENCH = register("work_bench", WorkBenchBlock::new, AbstractBlock.Settings.copy(CRAFTING_TABLE));
	public static final Block STONE_CRUCIBLE = register("stone_crucible", CrucibleBlock::new, AbstractBlock.Settings.copy(CRAFTING_TABLE));
	public static final Block TINDER = register("tinder", AbstractBlock.Settings.copy(Blocks.HAY_BLOCK));
	
	public static Block register(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		Block block = factory.apply(settings.registryKey(key));
		return Registry.register(Registries.BLOCK, key, block);
	}
	
	private static RegistryKey<Block> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.BLOCK, DurabilityMain.ofDurability(id));
	}
	
	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return register(keyOf(id), factory, settings);
	}
	
	private static Block register(String id, AbstractBlock.Settings settings) {
		return register(id, Block::new, settings);
	}
	
	public static void init() {
	}
}
