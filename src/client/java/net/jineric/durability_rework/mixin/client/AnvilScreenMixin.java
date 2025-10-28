package net.jineric.durability_rework.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.jineric.durability_rework.DrConfig;
import net.jineric.durability_rework.DurabilityMain;
import net.jineric.durability_rework.access.AnvilScreenHandlerAccess;
import net.jineric.durability_rework.mixin.StaticParameters;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ForgingScreen<AnvilScreenHandler> {
	@Shadow @Final private PlayerEntity player;
	@Unique private static final Identifier DISABLED_SLOT = DurabilityMain.ofDurability("container/slot/disabled");
	@Unique private static final Identifier RECIPE_TEXTURE = DurabilityMain.ofDurability("container/anvil/recipe");
	@Unique private float scrollAmount;
	@Unique private boolean mouseClicked;
	@Unique private int scrollOffset;
	@Unique private boolean canCraft;
	
	public AnvilScreenMixin(AnvilScreenHandler handler, PlayerInventory playerInventory, Text title, Identifier texture) {
		super(handler, playerInventory, title, texture);
		StaticParameters.setAnvilContentsChangedListener(this::onInventoryChange);
	}
	
	@Inject(
			method = "drawForeground",
			at = @At(value = "HEAD")
	)
	private void renderDisabledSlotOverlay(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
//		context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, DISABLED_SLOT, 10, 52, 18, 18);
	
	}

	@WrapWithCondition(
			method = "drawBackground",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V"
			)
	)
	private boolean skipDrawingTextBoxIfAnvilReworked(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
		if (!DrConfig.VANILLA_ANVIL) {
//			instance.drawTexture();
			return false;
		}
		return true;
	}
	
	@Inject(
			method = "drawBackground",
			at = @At(
					value = "TAIL"
			)
	)
	private void addCustomFunctionality(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
		if (!DrConfig.VANILLA_ANVIL) {
			this.renderRecipeBackground(context, deltaTicks, mouseX, mouseY);
		}
	}
	
	@WrapWithCondition(
			method = "drawInvalidRecipeArrow",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V"
			)
	)
	private boolean skipDrawingInvalidRecipeArrowIfAnvilReworked(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height) {
		return DrConfig.VANILLA_ANVIL;
	}
	
	@Inject(
			method = "setup",
			at = @At(value = "NEW",
					target = "(Lnet/minecraft/client/font/TextRenderer;IIIILnet/minecraft/text/Text;)Lnet/minecraft/client/gui/widget/TextFieldWidget;",
					shift = At.Shift.AFTER
			),
			cancellable = true
	)
	private void cancelSetupIfAnvilTextDisabled(CallbackInfo ci) {
		if (!DrConfig.VANILLA_ANVIL) {
			ci.cancel();
		}
	}
	
	@WrapWithCondition(
			method = "setInitialFocus",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/gui/screen/ingame/AnvilScreen;setInitialFocus(Lnet/minecraft/client/gui/Element;)V"
			)
	)
	private boolean setFocusIfAnvilEnabled(AnvilScreen instance, Element element) {
		return DrConfig.VANILLA_ANVIL;
	}
	
	@Inject(method = "resize", at = @At(value = "HEAD"), cancellable = true)
	private void setTextIfAnvilEnabled(MinecraftClient client, int width, int height, CallbackInfo ci) {
		if (!DrConfig.VANILLA_ANVIL) {
			this.init(client, width, height);
			ci.cancel();
		}
	}
	
	@WrapOperation(
			method = "keyPressed",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;keyPressed(Lnet/minecraft/client/input/KeyInput;)Z"
			)
	)
	private boolean checkKeyPressedIfAnvilTextEnabled(TextFieldWidget instance, KeyInput input, Operation<Boolean> original) {
		return DrConfig.VANILLA_ANVIL;
	}
	
	@WrapOperation(
			method = "keyPressed",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;isActive()Z"
			)
	)
	private boolean checkIsActiveIfAnvilTextEnabled(TextFieldWidget instance, Operation<Boolean> original) {
		return DrConfig.VANILLA_ANVIL;
	}
	
//	@ModifyReturnValue(method = "keyPressed", at = @At("RETURN"))
//	private boolean ignoreKeyPressIfAnvilTestDisabled(boolean original, int keyCode, int scanCode, int modifiers) {
//		if (!DrConfig.VANILLA_ANVIL) {
//			return super.keyPressed(keyCode, scanCode, modifiers);
//		}
//		return original;
//	}
	
	@Inject(method = "onSlotUpdate", at = @At("HEAD"), cancellable = true)
	private void skipTestUpdateIfAnvilTextDisabled(ScreenHandler handler, int slotId, ItemStack stack, CallbackInfo ci) {
		if (!DrConfig.VANILLA_ANVIL) {
			ci.cancel();
		}
	}
	
	@Unique
	private void renderRecipeBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
		int recipe_box_x = this.x + 36;
		int recipe_box_y = this.y + 16;
		if (this.handler.slots.getFirst().hasStack()) {
			
			int dirty_x = recipe_box_x;
			int dirty_y = recipe_box_y;
			
			
			for (int x=0; x < 4; x++) {
				if (x >= 1) {
					dirty_x += 16;
				}
				
//				for (int y=0; y < 2; y++) {
//					dirty_y-= 18;
					
					context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, RECIPE_TEXTURE, dirty_x, dirty_y, 16, 18);
//				}
			}
			

			
//			if (dirty_y < 4) {
//				dirty_y -= 18;
//			}
		
		}
	}
	
//	@Unique
//	private boolean canShowRecipes() {
//		return this.;
//	}
	
//  ADDED CONTENT
	
//	private boolean shouldScroll() {
//		return this.canCraft && this.handler.getAvailableRecipeCount() > 12;
//	}
//
//	protected int getMaxScroll() {
//		return (this.handler.getAvailableRecipeCount() + 4 - 1) / 4 - 3;
//	}
	
	@Unique
	private void onInventoryChange() {
		this.canCraft = ((AnvilScreenHandlerAccess)this.handler).canCraft();
		if (!this.canCraft) {
			this.scrollAmount = 0.0F;
			this.scrollOffset = 0;
		}
	}
}
