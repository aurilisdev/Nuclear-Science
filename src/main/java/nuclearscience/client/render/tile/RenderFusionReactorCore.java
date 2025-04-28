package nuclearscience.client.render.tile;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.math.Color;

public class RenderFusionReactorCore extends AbstractTileRenderer<TileFusionReactorCore> {

	public RenderFusionReactorCore(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileFusionReactorCore tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BlockPos pos = tileEntityIn.getBlockPos();
		if (Minecraft.getInstance().player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 32) {

			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("deuteriumlevel", tileEntityIn.deuterium.getValue()), 0.5f, 0.7f + 1, 0.5f, Color.WHITE.color(), combinedLightIn);
			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("tritiumlevel", tileEntityIn.tritium.getValue()), 0.5f, 0.3f + 1, 0.5f, Color.WHITE.color(), combinedLightIn);

			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("deuteriumlevel", tileEntityIn.deuterium.getValue()), 0.5f, 0.7f - 1, 0.5f, Color.WHITE.color(), combinedLightIn);
			renderFloatingText(matrixStackIn, bufferIn, NuclearTextUtils.tooltip("tritiumlevel", tileEntityIn.tritium.getValue()), 0.5f, 0.3f - 1, 0.5f, Color.WHITE.color(), combinedLightIn);
		}
	}

	public void renderFloatingText(PoseStack matrixStackIn, MultiBufferSource bufferIn, Component text, float x, float y, float z, int color, int combinedLightIn) {

		matrixStackIn.pushPose();

		matrixStackIn.translate(x, y, z);

		matrixStackIn.scale(-0.025F, -0.025F, 0.025F);

		Camera camera = minecraft().getEntityRenderDispatcher().camera;

		matrixStackIn.mulPose(new Quaternionf().rotationYXZ((float) Math.toRadians(camera.getYRot()), (float) Math.toRadians(-camera.getXRot()), 0.0F));

		Matrix4f matrix4f = matrixStackIn.last().pose();

		int backgroundColor = (int) (minecraft().options.getBackgroundOpacity(0.76F) * 255) << 24;

		Font font = Minecraft.getInstance().font;

		float xOffset = -font.width(text) / 2f;

		font.drawInBatch(text, xOffset, 0, color, false, matrix4f, bufferIn, DisplayMode.NORMAL, backgroundColor, combinedLightIn);

		matrixStackIn.popPose();
	}

}
