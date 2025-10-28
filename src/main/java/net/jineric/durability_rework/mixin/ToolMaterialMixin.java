package net.jineric.durability_rework.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jineric.durability_rework.DrConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ToolMaterial.class)
public abstract class ToolMaterialMixin {
	
	@WrapOperation(
			method = "applyBaseSettings",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/Item$Settings;maxDamage(I)Lnet/minecraft/item/Item$Settings;"
			)
	)
	private Item.Settings removeMaxDamage(Item.Settings instance, int maxDamage, Operation<Item.Settings> original) {
		if (DrConfig.MODE_UPGRADE && DrConfig.UPGRADE_EQUIPMENT) {
			return instance.durability$maxLevel(maxDamage);
		} else {
			return original.call(instance, maxDamage);
		}
	}
	
//	@WrapOperation(
//			method = "applyBaseSettings",
//			at = @At(
//					value = "INVOKE",
//					target = "Lnet/minecraft/item/Item$Settings;repairable(Lnet/minecraft/registry/tag/TagKey;)Lnet/minecraft/item/Item$Settings;"
//			)
//	)
//	private Item.Settings removeMaxDamage(Item.Settings instance, TagKey<Item> repairIngredientsTag, Operation<Item.Settings> original) {
//		if (DrConfig.MODE_UPGRADE && DrConfig.UPGRADE_EQUIPMENT) {
//			return instance;
//		} else {
//			return original.call(instance, repairIngredientsTag);
//		}
//	}
}
