package net.jineric.durability_rework.access;

import net.minecraft.item.Item;

public interface ItemSettingsAccess {
	
	default Item.Settings durability$maxLevel(int max) {
		throw new RuntimeException("ItemSettingsAccess failed");
	}
}
