package net.jineric.durability_rework;

import net.jineric.durability_rework.block.campfire.CampfireScreenHandler;
import net.jineric.durability_rework.block.crucible.CrucibleScreenHandler;
import net.jineric.durability_rework.block.work_bench.WorkBenchScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class DrScreenHandlerType {
	private static final Identifier CAMPFIRE = DurabilityMain.ofDurability("campfire");
	private static final Identifier CRUCIBLE = DurabilityMain.ofDurability("crucible");
	private static final Identifier WORK_BENCH = DurabilityMain.ofDurability("work_bench");
	
	public static ScreenHandlerType<CampfireScreenHandler> CAMPFIRE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, CAMPFIRE, new ScreenHandlerType<>(CampfireScreenHandler::new, null));
	public static ScreenHandlerType<CrucibleScreenHandler> CRUCIBLE_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, CRUCIBLE, new ScreenHandlerType<>(CrucibleScreenHandler::new, null));
	public static ScreenHandlerType<WorkBenchScreenHandler> WORK_BENCH_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, WORK_BENCH, new ScreenHandlerType<>(WorkBenchScreenHandler::new, null));
	
}
