package net.jineric.durability_rework.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.block.campfire.CampfireScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BlockWithEntity implements Waterloggable {
	@Shadow @Final public static BooleanProperty LIT;
	@Shadow @Final public static BooleanProperty SIGNAL_FIRE;
	@Shadow @Final public static BooleanProperty WATERLOGGED;
	@Shadow @Final public static EnumProperty<Direction> FACING;
	protected CampfireBlockMixin(Settings settings) {
		super(settings);
	}
	
	//  MIXINS
	@WrapOperation(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/block/CampfireBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"
			)
	)
	private void modifyDefaultStateToUnlit(CampfireBlock instance, BlockState blockState, Operation<Void> original) {
//		original.call(instance, this.stateManager.getDefaultState().with(LIT, false).with(SIGNAL_FIRE, false).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
	}
	
	@ModifyArg(
			method = "getPlacementState",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;",
					ordinal = 2
			),
			index = 1
	)
	private Comparable<Boolean> alwaysPlaceUnlit(Comparable<Boolean> par2) {
		return false;
	}
	
	
	
	//  IMPLEMENTED CLASS OVERRIDES
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (!world.isClient() && blockEntity instanceof CampfireBlockEntity campfireBlockEntity) {
			player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
//			player.incrementStat();
		}
		return ActionResult.SUCCESS;
	}
	
	@Override
	protected @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		if (world.getBlockEntity(pos) instanceof CampfireBlockEntity campfireBlockEntity) {
			return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> new CampfireScreenHandler(syncId, playerInventory), Text.of(DurabilityMain.ofDurability("container.campfire")));
		} else {
			return null;
		}
	}
	
}
