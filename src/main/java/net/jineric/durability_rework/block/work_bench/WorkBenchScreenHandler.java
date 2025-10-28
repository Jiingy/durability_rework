package net.jineric.durability_rework.block.work_bench;

import net.jineric.durability_rework.DrScreenHandlerType;
import net.jineric.durability_rework.block.DrBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class WorkBenchScreenHandler extends ScreenHandler {
	private final ScreenHandlerContext context;
	private final Slot material;
	private final Slot firstBase;
	private final Slot secondBase;
	private final Slot outputSlot;
	private final PlayerEntity player;
	
	private final Inventory input = new SimpleInventory(3) {
		@Override
		public void markDirty() {
			super.markDirty();
			WorkBenchScreenHandler.this.onContentChanged(this);
		}
	};
	
	private final Inventory output = new SimpleInventory(1) {
		@Override
		public void markDirty() {
			super.markDirty();
		}
	};
	
	public WorkBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}
	
	public WorkBenchScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(DrScreenHandlerType.WORK_BENCH_SCREEN_HANDLER, syncId);
		this.context = context;
		this.player = playerInventory.player;
		this.material = this.addSlot(new Slot(this.input, 0, 19, 33));
		this.firstBase = this.addSlot(new Slot(this.input, 1, 40, 33));
		this.secondBase = this.addSlot(new Slot(this.input, 2, 61, 33));
		this.outputSlot = this.addSlot(new Slot(this.output, 0, 114, 33) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});
		
		this.addPlayerInventorySlots(playerInventory);
	}
	
	private void addPlayerInventorySlots(PlayerInventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slotIndex = this.slots.get(slot);
		if (slotIndex.hasStack()) {
			ItemStack itemStackFromSlotIndex = slotIndex.getStack();
			itemStack = itemStackFromSlotIndex.copy();
		}
		return itemStack;
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.context.run((world, pos) -> this.dropInventory(player, this.input));
	}
	
	public Slot getMaterial() {
		return material;
	}
	
	public Slot getOutputSlot() {
		return outputSlot;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(this.context, player, DrBlocks.WORK_BENCH);
	}
}
