package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import com.mojang.math.Matrix4f;
import net.minecraft.network.chat.TextComponent;
import nuclearscience.common.tile.TileFusionReactorCore;

public class RenderFusionReactorCore extends BlockEntityRenderer<TileFusionReactorCore> {

    public RenderFusionReactorCore(BlockEntityRenderDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileFusionReactorCore tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	BlockPos pos = tileEntityIn.getBlockPos();
	if (Minecraft.getInstance().player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 32) {
	    renderFloatingText(matrixStackIn, bufferIn, "Deuterium: " + tileEntityIn.deuterium, 0.6f, 0.7f, 0.6f, 16777215, combinedLightIn);
	    renderFloatingText(matrixStackIn, bufferIn, "Tritium: " + tileEntityIn.tritium, 0.6f, 0.3f, 0.6f, 16777215, combinedLightIn);
	}
    }

    public static void renderFloatingText(PoseStack matrixStackIn, MultiBufferSource bufferIn, String text, float x, float y, float z, int color,
	    int combinedLightIn) {
	TextComponent displayNameIn = new TextComponent(text);
	matrixStackIn.pushPose();
	matrixStackIn.translate(x, y, z);
	matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
	matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
	Matrix4f matrix4f = matrixStackIn.last().pose();
	float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.76F);
	int j = (int) (f1 * 50.0F) << 24;
	Font fontrenderer = Minecraft.getInstance().font;
	float f2 = -fontrenderer.width(displayNameIn) / 2f;
	fontrenderer.drawInBatch(displayNameIn, f2, 0, color, false, matrix4f, bufferIn, true, j, combinedLightIn);
	matrixStackIn.popPose();
    }
}
