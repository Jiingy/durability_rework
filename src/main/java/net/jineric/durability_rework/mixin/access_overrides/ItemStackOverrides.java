package net.jineric.durability_rework.mixin.access_overrides;

import net.jineric.durability_rework.access.ItemStackAccess;
import net.jineric.durability_rework.component.DrDataComponentTypes;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackOverrides implements ItemStackAccess, ComponentHolder {
	@Shadow @Nullable public abstract <T> T set(ComponentType<T> type, @Nullable T value);
	
	@Override
	public boolean durability$isLeveled() {
		return this.durability$isUpgradable() && this.getOrDefault(DrDataComponentTypes.LEVEL, 0) > 0;
	}
	
	@Override
	public boolean durability$isUpgradable() {
		return this.contains(DrDataComponentTypes.MAX_LEVEL);
	}
	
	@Override
	public void durability$setLevel(int level) {
		this.set(DrDataComponentTypes.LEVEL, MathHelper.clamp(level, 0, this.durability$getMaxLevel()));
	}
	
	@Override
	public int durability$getLevel() {
		return this.getOrDefault(DrDataComponentTypes.LEVEL, 0);
	}
	
	@Override
	public int durability$getMaxLevel() {
		return this.getOrDefault(DrDataComponentTypes.MAX_LEVEL, 0);
	}
	
	@Override
	public int durability$getRemainingLevel() {
		return this.durability$getMaxLevel() - this.durability$getLevel();
	}
}
