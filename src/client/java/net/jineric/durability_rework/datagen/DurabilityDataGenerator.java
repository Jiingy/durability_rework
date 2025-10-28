package net.jineric.durability_rework.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.jineric.durability_rework.DurabilityMain;
import net.minecraft.registry.RegistryBuilder;

public class DurabilityDataGenerator implements DataGeneratorEntrypoint {
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		//  Put data gen for wooden families here
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(DrModelProvider::new);
		pack.addProvider(DrRecipeProvider::new);
		pack.addProvider(DrLootTableProviders.DrBlockLootTableProvider::new);
		pack.addProvider(DrTagProviders.DrItemTagProvider::new);
		pack.addProvider(DrTagProviders.DrBlockTagProvider::new);
		pack.addProvider(DrTagProviders.DrEntityTagProvider::new);
		pack.addProvider(DrLanguageProvider::new);
	}
	
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
	}
	
	@Override
	public String getEffectiveModId() {
		return DurabilityMain.MOD_ID;
	}
}
