package nuclearscience.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.math.Color;

public class RenderMonitorModule extends AbstractTileRenderer<TileMonitorModule> {

	public RenderMonitorModule(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileMonitorModule tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		if (!tile.linked.getValue()) {
			return;
		}

		long time = System.currentTimeMillis() % 1500 - 750;

		if (time < 0) {
			return;
		}

		FontRenderer font = Minecraft.getInstance().font;

		Direction facing = tile.getFacing();

		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		rotateMatrix(stack, facing);

		stack.translate(0.1, 0.3, -0.188125);

		ITextComponent transfer = new StringTextComponent("_");

		int width = font.width(transfer);

		float scale = 0.0215F / (width / 8.0F);

		stack.scale(-scale, -scale, -scale);

		Matrix4f matrix4f = stack.last().pose();

		float textX = -width / 2.0f;

		font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, false, 0, combinedLightIn);

		stack.popPose();

	}

	private void rotateMatrix(MatrixStack stack, Direction dir) {
		switch (dir) {
		case EAST:
			stack.mulPose(new Quaternion(0, -90, 0, true));
			break;
		case SOUTH:
			stack.mulPose(new Quaternion(0, 180, 0, true));
			break;
		case WEST:
			stack.mulPose(new Quaternion(0, 90, 0, true));
			break;
		default:
			break;
		}
	}
}
