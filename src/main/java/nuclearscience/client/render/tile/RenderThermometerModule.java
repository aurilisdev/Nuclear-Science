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
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.math.Color;

public class RenderThermometerModule extends AbstractTileRenderer<TileThermometerModule> {

	public RenderThermometerModule(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileThermometerModule tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		if (!tile.linked.getValue()) {
			return;
		}

		FontRenderer font = Minecraft.getInstance().font;

		Direction facing = tile.getFacing();

		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		rotateMatrix(stack, facing);

		/* MONITORED TEMPERATURE */

		stack.pushPose();

		stack.translate(0, 0.175, -0.46875);

		ITextComponent transfer = ChatFormatter.getChatDisplayShort(tile.trackedTemperature.getValue(), DisplayUnits.TEMPERATURE_CELCIUS);

		int width = font.width(transfer);

		float scale = 0.0215F / (width / 24.0F);

		stack.scale(-scale, -scale, -scale);

		Matrix4f matrix4f = stack.last().pose();

		float textX = -width / 2.0f;

		font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, false, 0, combinedLightIn);

		stack.popPose();

		/* TARGET TEMPERATURE */

		stack.pushPose();

		stack.translate(0, -0.0625, -0.46875);

		transfer = ChatFormatter.getChatDisplayShort(tile.targetTemperature.getValue(), DisplayUnits.TEMPERATURE_CELCIUS);

		width = font.width(transfer);

		scale = 0.0215F / (width / 24.0F);

		stack.scale(-scale, -scale, -scale);

		matrix4f = stack.last().pose();

		textX = -width / 2.0f;

		font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, false, 0, combinedLightIn);

		stack.popPose();

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
