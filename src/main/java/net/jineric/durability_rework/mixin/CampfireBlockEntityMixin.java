package net.jineric.durability_rework.mixin;

import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.block.campfire.CampfireScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Clearable;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity implements NamedScreenHandlerFactory, Nameable, Clearable {
	private Text customName;
	private Inventory inventory2 = new SimpleInventory();
	private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
	
	public CampfireBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new CampfireScreenHandler(syncId, playerInventory);
	}
	
	@Override
	public Text getName() {
		return this.customName != null ? this.customName : Text.of(DurabilityMain.ofDurability("container.campfire"));
	}
	
	@Override
	public Text getDisplayName() {
		return Text.of(DurabilityMain.ofDurability("container.campfire"));
	}
}
