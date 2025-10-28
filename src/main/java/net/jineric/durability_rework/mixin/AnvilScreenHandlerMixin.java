package net.jineric.durability_rework.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.jineric.durability_rework.DrConfig;
import net.jineric.durability_rework.access.AnvilScreenHandlerAccess;
import net.jineric.durability_rework.recipe.AssembleRecipe;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.display.CuttingRecipeDisplay;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.StringHelper;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler implements AnvilScreenHandlerAccess {
	@Shadow private int repairItemUsage;
	@Shadow private boolean keepSecondSlot;
	@Shadow @Final private Property levelCost;
	@Shadow private @Nullable String newItemName;
	@Unique final Property selectedRecipe = Property.create();
	@Unique private CuttingRecipeDisplay.Grouping<AssembleRecipe> availableRecipes = CuttingRecipeDisplay.Grouping.empty();
	
	public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
		super(type, syncId, playerInventory, context, forgingSlotsManager);
	}
	
	//  MODIFIED ANVIL
	@ModifyReturnValue(
			method = "canTakeOutput",
			at = @At("RETURN")
	)
	private boolean removeExperienceRequirement(boolean original, PlayerEntity player, boolean present) {
		return player.isCreative() || !this.input.isEmpty();
	}
	
	
	/**
	 * @author Jiingy
	 * @reason Testing
	 */
	@Overwrite
	public void onTakeOutput(PlayerEntity player, ItemStack stack) {
		
		if (this.repairItemUsage > 0) {
			ItemStack itemStack = this.input.getStack(1);
			if (!itemStack.isEmpty() && itemStack.getCount() > this.repairItemUsage) {
				itemStack.decrement(this.repairItemUsage);
				this.input.setStack(1, itemStack);
			} else {
				this.input.setStack(1, ItemStack.EMPTY);
			}
		} else if (!this.keepSecondSlot) {
			this.input.setStack(1, ItemStack.EMPTY);
		}
		
		if (player instanceof ServerPlayerEntity serverPlayerEntity
				&& !StringHelper.isBlank(this.newItemName)
				&& !this.input.getStack(0).getName().getString().equals(this.newItemName)) {
			serverPlayerEntity.getTextStream().filterText(this.newItemName);
		}
		
		this.input.setStack(0, ItemStack.EMPTY);
		this.context.run((world, pos) -> {
			BlockState blockState = world.getBlockState(pos);
			if (!player.isInCreativeMode() && blockState.isIn(BlockTags.ANVIL) && player.getRandom().nextFloat() < 0.12F) {
				BlockState blockState2 = AnvilBlock.getLandingState(blockState);
				if (blockState2 == null) {
					world.removeBlock(pos, false);
					world.syncWorldEvent(WorldEvents.ANVIL_DESTROYED, pos, 0);
				} else {
					world.setBlockState(pos, blockState2, Block.NOTIFY_LISTENERS);
					world.syncWorldEvent(WorldEvents.ANVIL_USED, pos, 0);
				}
			} else {
				world.syncWorldEvent(WorldEvents.ANVIL_USED, pos, 0);
			}
		});
	}
	
	@Inject(
			method = "updateResult",
			at = @At("HEAD"),
			cancellable = true
	)
	private void replaceAnvilUpdateResultFunctionality(CallbackInfo ci) {
		if (DrConfig.MODIFY_VANILLA_ANVIL) {
			this.updateResultLeveled();
			ci.cancel();
		}
	}
	
	@Unique
	public void updateResultLeveled() {
		ItemStack input0 = this.input.getStack(0);
		this.keepSecondSlot = false;
		int i = 0;
		if (
				!input0.isEmpty()
//				&& EnchantmentHelper.canHaveEnchantments(input0)
		) {
			ItemStack output = input0.copy();
			ItemStack input1 = this.input.getStack(1);
//			ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(EnchantmentHelper.getEnchantments(output));
			this.repairItemUsage = 0;
			if (!input1.isEmpty()) {
				System.out.println(output.durability$isUpgradable());
				System.out.println(input0.canRepairWith(input1));
				if (output.durability$isUpgradable() && input0.canRepairWith(input1)) {
					int maxLevel = output.durability$getMaxLevel();

					if (output.durability$getLevel() >= maxLevel) {
						this.output.setStack(0, output);
						return;
					}
					
					int repairIterations;
//					for (repairIterations = 0; k > 0 && repairIterations < input1.getCount(); repairIterations++) {
//						int n = output.durability$getLevel() + k;
//						output.durability$setLevel(n);
//						i++;
//						k = Math.min(output.durability$getLevel(), output.durability$getMaxLevel() / 4);
//					}
//					this.repairItemUsage = repairIterations;
				}
				
				else {
					if (!output.isOf(input1.getItem()) || !output.durability$isUpgradable()) {
						this.output.setStack(0, ItemStack.EMPTY);
						return;
					}
					
					if (output.durability$isUpgradable()) {
						int input0LevelRemainder = input0.durability$getMaxLevel() - input0.durability$getLevel();
						int input1LevelRemainder = input1.durability$getMaxLevel() - input1.durability$getLevel();
						int n = input1LevelRemainder + output.durability$getMaxLevel() * 12 / 100;
						int o = input0LevelRemainder + n;
						int p = output.durability$getMaxLevel() - o;
						if (p < 0) {
							p = 0;
						}
						
						if (p < output.durability$getLevel()) {
							output.durability$setLevel(p);
							i += 2;
						}
					}
				}
			}
			
			if (i <= 0) {
//				output = ItemStack.EMPTY;
			}
			
			if (!this.player.isInCreativeMode()) {
//				output = ItemStack.EMPTY;
			}
			
//			if (!output.isEmpty()) {
//				EnchantmentHelper.set(output, builder.build());
//			}
			
			this.output.setStack(0, output);
			this.sendContentUpdates();
		} else {
			this.output.setStack(0, ItemStack.EMPTY);
		}
	}
	
	
	
	// REPLACED ANVIL
	@ModifyArgs(
			method = "getForgingSlotsManager",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/slot/ForgingSlotsManager$Builder;input(IIILjava/util/function/Predicate;)Lnet/minecraft/screen/slot/ForgingSlotsManager$Builder;",
					ordinal = 0
			)
	)
	private static void modifyInputSlot0Position(Args args) {
		if (!DrConfig.VANILLA_ANVIL) {
			args.set(1, 11);
			args.set(2, 17);
		}
	}
	
	@ModifyArgs(
			method = "getForgingSlotsManager",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/slot/ForgingSlotsManager$Builder;input(IIILjava/util/function/Predicate;)Lnet/minecraft/screen/slot/ForgingSlotsManager$Builder;",
					ordinal = 1
			)
	)
	private static void modifyInputSlot1Position(Args args) {
		if (!DrConfig.VANILLA_ANVIL) {
			args.set(1, 11);
			args.set(2, 53);
		}
	}
	
	@ModifyArgs(
			method = "getForgingSlotsManager",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/slot/ForgingSlotsManager$Builder;output(III)Lnet/minecraft/screen/slot/ForgingSlotsManager$Builder;"
			)
	)
	private static void modifyOutputSlotPosition(Args args) {
		if (!DrConfig.VANILLA_ANVIL) {
			args.set(1, 143);
			args.set(2, 35);
		}
	}
	
	@Inject(
			method = "updateResult",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/screen/Property;set(I)V",
					ordinal = 0
			), cancellable = true
	)
	private void craftPickaxe(
			CallbackInfo ci,
			@Local(ordinal = 0) ItemStack slot0
	) {
		ItemStack slot0Copy = slot0.copy();
		ItemStack slot1 = this.input.getStack(1);
		if (
				slot0.isOf(Items.STICK) && slot0.getCount() >= 3
				&& slot1.isOf(Items.IRON_INGOT) && slot1.getCount() >= 22
		) {
			this.output.setStack(2, Items.IRON_PICKAXE.getDefaultStack());
			ci.cancel();
		}
	}
	@ModifyReturnValue(method = "canTakeOutput", at = @At("RETURN"))
	private boolean modify(boolean original, PlayerEntity player) {
		return true;
	}
	
//  OVERRIDES
	
	@Override
	public int getSelectedRecipe() {
		return this.selectedRecipe.get();
	}
	
//	@Override
//	public CuttingRecipeDisplay.Grouping<AssembleRecipe> getAvailableRecipes() {
//		return this.availableRecipes;
//	}
	
	@Override
	public int getAvailableRecipeCount() {
		return this.availableRecipes.size();
	}
	
	@Override
	public boolean canCraft() {
		return !this.input.getStack(0).isEmpty() && !this.availableRecipes.isEmpty();
	}
}

