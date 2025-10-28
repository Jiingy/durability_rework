package net.jineric.durability_rework.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jineric.durability_rework.DrConfig;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Items.class)
public abstract class ItemsMixin {

	@WrapOperation(
			method = "<clinit>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/Item$Settings;maxDamage(I)Lnet/minecraft/item/Item$Settings;"
			)
	)
	private static Item.Settings changeDurabilityToLevel(Item.Settings instance, int maxDamage, Operation<Item.Settings> original) {
		if (DrConfig.MODE_UPGRADE && DrConfig.UPGRADE_EQUIPMENT) {
			return instance.durability$maxLevel(maxDamage);
		}
		return original.call(instance, maxDamage);
	}
}
