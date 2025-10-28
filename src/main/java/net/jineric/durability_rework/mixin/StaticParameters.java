package net.jineric.durability_rework.mixin;

public class StaticParameters {
	
	private static Runnable ANVIL_CONTENTS_CHANGED_LISTENER = () -> {};
	
	public static void setAnvilContentsChangedListener(Runnable contentsChangedListener) {
		ANVIL_CONTENTS_CHANGED_LISTENER = contentsChangedListener;
	}
}
