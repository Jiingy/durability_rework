package net.jineric.durability_rework.block.campfire;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jineric.durability_rework.DurabilityMain;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CampfireScreen extends HandledScreen<CampfireScreenHandler> {
	private static final Identifier TEXTURE = DurabilityMain.ofDurability("textures/gui/container/campfire.png");
	
	public CampfireScreen(CampfireScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}
	
	@Override
	protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
		int x = this.x;
		int y = (this.height - this.backgroundHeight) / 2;
		context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 256);
	}
}
