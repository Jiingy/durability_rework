package net.jineric.durability_rework.block.campfire;

import net.jineric.durability_rework.DrScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class CampfireScreenHandler extends ScreenHandler {
	private final Inventory inventory = new SimpleInventory(5);
	
	public CampfireScreenHandler(int syncId, PlayerInventory inventory) {
		this(syncId, inventory, ScreenHandlerContext.EMPTY);
	}
	
	public CampfireScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
		super(DrScreenHandlerType.CAMPFIRE_SCREEN_HANDLER, syncId);
		int offset = 40;
		
		
		
		for (int index = 0; index < 4; index++) {
			boolean even = (index + 1) % 2 == 0;
			Slot slot = new Slot(this.inventory, index, even ? 60 + offset : 60, index > 1 ? 15 + offset : 15);
			
			int final_index = index;
			this.addSlot(new Slot(this.inventory, index, even ? 60 + offset : 60, index > 1 ? 15 + offset : 15) {
				@Override
				public boolean canInsert(ItemStack stack) {
					System.out.println(this.inventory.getStack(final_index - 1).isEmpty());
					return !this.inventory.getStack(final_index - 1).isEmpty();
					
//					RegistryKey<World> worldRegistryKey = this.inventory.getViewingUsers().getFirst().asLivingEntity().getEntityWorld().getRegistryKey();
//					ServerWorld serverWorld = inventory.getViewingUsers().getFirst().asLivingEntity().getEntityWorld().getServer().getWorld(worldRegistryKey);
//					if (serverWorld != null) {
//						serverWorld.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SingleStackRecipeInput(stack), serverWorld);
//					}
				}
				
				@Override
				public int getMaxItemCount() {
					return 1;
				}
			});
		}
		this.addSlot(new Slot(this.inventory, 4, 80, 35));
		this.addPlayerSlots(inventory, 8, 84);
	}
	
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		return null;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
