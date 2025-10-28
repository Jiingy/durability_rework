package net.jineric.durability_rework.block.work_bench;

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
public class WorkBenchScreen extends HandledScreen<WorkBenchScreenHandler> {
	private static final Identifier TEXTURE = DurabilityMain.ofDurability("textures/gui/container/work_bench.png");
	
	public WorkBenchScreen(WorkBenchScreenHandler screenHandler, PlayerInventory inventory, Text title) {
		super(screenHandler, inventory, title);
//      screenHandler.setInventoryChangeListener(this::onInventoryChanged);
	}
	
//	@Override
//	protected void init() {
//		super.init();
//	}
	
	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.drawMouseoverTooltip(context, mouseX, mouseY);
	}
	
	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		int i = this.x;
		int j = this.y;
		context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
	}
	
	private void onInventoryChanged() {
	
	}
}
