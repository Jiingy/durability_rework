package net.jineric.durability_rework.access;

public interface ItemStackAccess {
	
	default boolean durability$isLeveled() {
		throw new RuntimeException("ItemStackAccess.durability$isLeveled failed");
	}
	
	default boolean durability$isUpgradable() {
		throw new RuntimeException("ItemStackAccess.durability$isUpgradable failed");
	}
	
	default void durability$setLevel(int level) {
		throw new RuntimeException("ItemStackAccess.durability$getLevel failed");
	}
	
	default int durability$getLevel() {
		throw new RuntimeException("ItemStackAccess.durability$getLevel failed");
	}
	
	default int durability$getMaxLevel() {
		throw new RuntimeException("ItemStackAccess.durability$getMaxLevel failed");
	}
	
	default int durability$getRemainingLevel() {
		throw new RuntimeException("ItemStackAccess.durability$getRemainingLevel failed");
	}
}
