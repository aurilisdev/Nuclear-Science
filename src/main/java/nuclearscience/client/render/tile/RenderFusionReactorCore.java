package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.ITextComponent;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.math.Color;

public class RenderFusionReactorCore extends AbstractTileRenderer<TileFusionReactorCore> {

	public RenderFusionReactorCore(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileFusionReactorCore tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BlockPos pos = tileEntityIn.getBlockPos();
		if (Minecraft.getInstance().player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 32) {

			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("deuteriumlevel", tileEntityIn.deuterium.getValue()), 0.5f, 0.7f + 1, 0.5f, Color.WHITE.color(), combinedLightIn);
			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("tritiumlevel", tileEntityIn.tritium.getValue()), 0.5f, 0.3f + 1, 0.5f, Color.WHITE.color(), combinedLightIn);

			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("deuteriumlevel", tileEntityIn.deuterium.getValue()), 0.5f, 0.7f - 1, 0.5f, Color.WHITE.color(), combinedLightIn);
			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("tritiumlevel", tileEntityIn.tritium.getValue()), 0.5f, 0.3f - 1, 0.5f, Color.WHITE.color(), combinedLightIn);
		}
	}

	public void renderFloatingText(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, ITextComponent text, float x, float y, float z, int color, int combinedLightIn) {

		matrixStackIn.pushPose();

		matrixStackIn.translate(x, y, z);

		matrixStackIn.scale(-0.025F, -0.025F, 0.025F);

		ActiveRenderInfo camera = minecraft().getEntityRenderDispatcher().camera;

		matrixStackIn.mulPose(new Quaternion((float) Math.toRadians(camera.getYRot()), (float) Math.toRadians(-camera.getXRot()), 0.0F, false));

		Matrix4f matrix4f = matrixStackIn.last().pose();

		int backgroundColor = (int) (minecraft().options.getBackgroundOpacity(0.76F) * 255) << 24;

		FontRenderer font = Minecraft.getInstance().font;

		float xOffset = -font.width(text) / 2f;

		font.drawInBatch(text, xOffset, 0, color, false, matrix4f, bufferIn, false, backgroundColor, combinedLightIn);

		matrixStackIn.popPose();
	}

}
