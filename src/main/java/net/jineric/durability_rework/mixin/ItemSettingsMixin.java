package net.jineric.durability_rework.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jineric.durability_rework.DrConfig;
import net.jineric.durability_rework.access.ItemSettingsAccess;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.Settings.class)
public abstract class ItemSettingsMixin implements ItemSettingsAccess {
	
	//  GIVE ARMOR LEVELING
	@WrapOperation(
			method = "armor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/Item$Settings;maxDamage(I)Lnet/minecraft/item/Item$Settings;"
			)
	)
	private Item.Settings removeMaxDamage(Item.Settings instance, int maxDamage, Operation<Item.Settings> original) {
		if (DrConfig.MODE_UPGRADE && DrConfig.UPGRADE_EQUIPMENT) {
			return instance.durability$maxLevel(maxDamage);
		} else {
			return instance;
		}
	}
	
	//  GIVE ARMOR LEVELING
	@WrapOperation(
			method = "armor",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/Item$Settings;repairable(Lnet/minecraft/registry/tag/TagKey;)Lnet/minecraft/item/Item$Settings;"
			)
	)
	private Item.Settings removeRepairable(Item.Settings instance, TagKey<Item> repairIngredientsTag, Operation<Item.Settings> original) {
		if (DrConfig.MODE_UPGRADE && DrConfig.UPGRADE_EQUIPMENT) {
			return instance;
		} else {
			return original.call(instance, repairIngredientsTag);
		}
	}
}
