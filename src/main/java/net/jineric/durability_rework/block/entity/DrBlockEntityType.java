package net.jineric.durability_rework.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.block.DrBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DrBlockEntityType {
	public static final BlockEntityType<CrucibleBlockEntity> CRUCIBLE = FabricBlockEntityTypeBuilder.create(CrucibleBlockEntity::new, DrBlocks.STONE_CRUCIBLE).build();
	
	public static void registerBlockEntityTypes() {
		register("crucible", CRUCIBLE);
	}
	
	public static BlockEntityType<?> register(String id, BlockEntityType<?> blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, DurabilityMain.ofDurability(id), blockEntityType);
	}
}
