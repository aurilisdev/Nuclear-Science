package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class RenderQuantumCapacitor implements BlockEntityRenderer<TileQuantumCapacitor> {
    public RenderQuantumCapacitor(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileQuantumCapacitor tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	matrixStackIn.pushPose();
	matrixStackIn.translate(0.5, 0.5, 0.5);
	matrixStackIn.mulPoseMatrix(matrixStackIn.last().pose());
	matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
	matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
	float scale = 0.005f;
	matrixStackIn.scale(scale, scale, scale);
//	float distance = (float) Math.sqrt(1 + Minecraft.getInstance().player.distanceToSqr(tileEntityIn.getBlockPos().getX() + 0.5,
//		tileEntityIn.getBlockPos().getY() + 0.5, tileEntityIn.getBlockPos().getZ() + 0.5));
//	UtilitiesRendering.renderStar(tileEntityIn.getLevel().getLevelData().getDayTime(), (int) (250 / distance),
//		tileEntityIn.getLevel().random.nextFloat() * 0.2f + 0.2f, 0, 0, 1, false);
//	UtilitiesRendering.renderStar(tileEntityIn.getLevel().getLevelData().getDayTime() + 20f, (int) (250 / distance),
//		tileEntityIn.getLevel().random.nextFloat() * 0.1f + 0.4f, 0, 0, 1, false);
//	UtilitiesRendering.renderStar(tileEntityIn.getLevel().getLevelData().getDayTime() + 40f, (int) (250 / distance),
//		tileEntityIn.getLevel().random.nextFloat() * 0.3f + 0.5f, 0, 0, 1, false);
	// TODO: Reimplement these
	matrixStackIn.popPose();
    }

}
