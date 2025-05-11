package nuclearscience.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.ITextComponent;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class RenderControlRodModule extends AbstractTileRenderer<TileControlRodModule> {

	private static final double MAX_DELTA = 13.0 / 16.0;

	public RenderControlRodModule(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(@Nonnull TileControlRodModule tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		Direction facing = tile.getFacing();

		int sign = 1;

		if (facing == Direction.WEST || facing == Direction.EAST) {
			sign = -1;
		}

		stack.mulPose(new Quaternion(0, facing.toYRot() - sign * 90, 0, true));

		double insertion = tile.insertion.getValue() / (double) TileControlRod.MAX_EXTENSION;

		stack.translate(0, 0, -MAX_DELTA * insertion);

		RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_CONTROLRODMODULE_ROD), tile, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

		stack.popPose();

		if (!tile.linked.getValue()) {
			return;
		}

		FontRenderer font = Minecraft.getInstance().font;

		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		rotateMatrix(stack, facing);

		stack.translate(0, 0.175, 0.1775);

		ITextComponent transfer = ChatFormatter.getChatDisplayShort((double) tile.insertion.getValue() / (double) TileControlRod.MAX_EXTENSION * 100.0, DisplayUnits.PERCENTAGE);

		float scale = 0.0215F / (font.width(transfer) / 16.0F);

		stack.scale(-scale, -scale, -scale);

		Matrix4f matrix4f = stack.last().pose();

		float textX = -font.width(transfer) / 2.0f;

		font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, false, 0, combinedLightIn);

		stack.popPose();
	}

	private void rotateMatrix(MatrixStack stack, Direction dir) {
		switch (dir) {
		case EAST:
			stack.mulPose(new Quaternion(0, -90, 0, true));
			return;
		case SOUTH:
			stack.mulPose(new Quaternion(0, 180, 0, true));
			return;
		case WEST:
			stack.mulPose(new Quaternion(0, 90, 0, true));
			return;
		default:
			return;
		}
	}
}
