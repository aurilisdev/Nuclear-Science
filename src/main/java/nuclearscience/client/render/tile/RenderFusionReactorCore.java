package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import nuclearscience.common.tile.fusionreactor.TileFusionReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class RenderFusionReactorCore extends AbstractTileRenderer<TileFusionReactorCore> {

	public RenderFusionReactorCore(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileFusionReactorCore tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BlockPos pos = tileEntityIn.getBlockPos();
		if (Minecraft.getInstance().player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 32) {

			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("deuteriumlevel", tileEntityIn.deuterium), 0.6f, 0.7f, 0.6f, 16777215, combinedLightIn);
			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("tritiumlevel", tileEntityIn.tritium), 0.6f, 0.3f, 0.6f, 16777215, combinedLightIn);
		}
	}

	public static void renderFloatingText(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, ITextComponent text, float x, float y, float z, int color, int combinedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(x, y, z);
		matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
		Matrix4f matrix4f = matrixStackIn.last().pose();
		float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.76F);
		int j = (int) (f1 * 50.0F) << 24;
		FontRenderer fontrenderer = Minecraft.getInstance().font;
		float f2 = -fontrenderer.width(text) / 2f;
		fontrenderer.drawInBatch(text, f2, 0, color, false, matrix4f, bufferIn, false, j, combinedLightIn);
		matrixStackIn.popPose();
	}
}