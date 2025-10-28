package net.jineric.durability_rework.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jineric.durability_rework.DrConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	@WrapOperation(
			method = "damageEquipment",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"
			)
	)
	private boolean levelEquipmentSlotsOnDamage(
			ItemStack instance, Operation<Boolean> original,
			@Local()EquipmentSlot equipmentSlot,
			@Local()ItemStack itemStack,
			@Local(ordinal = 0)int i
	) {
		if (!DrConfig.MODE_UPGRADE) {
			return instance.isDamageable();
		}
		return instance.durability$isUpgradable();
	}
}
