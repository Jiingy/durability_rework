package net.jineric.durability_rework.mixin.access_overrides;

import net.jineric.durability_rework.access.ItemSettingsAccess;
import net.jineric.durability_rework.component.DrDataComponentTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.Settings.class)
public abstract class ItemSettingsOverrides implements ItemSettingsAccess {
	@Shadow
	public abstract <T> Item.Settings component(ComponentType<T> type, T value);
	
	@Override
	public Item.Settings durability$maxLevel(int max) {
		this.component(DrDataComponentTypes.LEVEL, 0);
		this.component(DrDataComponentTypes.MAX_LEVEL, max);
		this.component(DataComponentTypes.MAX_STACK_SIZE, 1);
		return ((Item.Settings) (Object) this);
	}
}
