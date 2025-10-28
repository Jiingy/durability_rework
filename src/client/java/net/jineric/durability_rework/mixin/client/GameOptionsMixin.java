package net.jineric.durability_rework.mixin.client;

import net.jineric.durability_rework.option.DrGameOptions;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
	
	@Inject(
			method = "acceptProfiledOptions",
			at = @At(
					value = "HEAD"
			)
	)
	private void keepModdedGameOptionValues(GameOptions.OptionVisitor visitor, CallbackInfo ci) {
		visitor.accept("durability_rework.itemLevelBarMode", DrGameOptions.getItemLevelBarMode());
	}
}
