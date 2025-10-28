package net.jineric.durability_rework;

import net.fabricmc.api.ClientModInitializer;
import net.jineric.durability_rework.block.campfire.CampfireScreen;
import net.jineric.durability_rework.block.crucible.CrucibleScreen;
import net.jineric.durability_rework.block.work_bench.WorkBenchScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class DurabilityClient implements ClientModInitializer {
	
	@Override
	public void onInitializeClient() {
		HandledScreens.register(DrScreenHandlerType.CAMPFIRE_SCREEN_HANDLER, CampfireScreen::new);
		HandledScreens.register(DrScreenHandlerType.CRUCIBLE_SCREEN_HANDLER, CrucibleScreen::new);
		HandledScreens.register(DrScreenHandlerType.WORK_BENCH_SCREEN_HANDLER, WorkBenchScreen::new);
	}
}