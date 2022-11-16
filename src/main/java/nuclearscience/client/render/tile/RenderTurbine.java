package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.RenderShape;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileTurbine;

public class RenderTurbine implements BlockEntityRenderer<TileTurbine> {
	public RenderTurbine(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileTurbine tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		boolean isCore = tileEntityIn.isCore.get();
		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TURBINEROTORLAYER);
		double daytime = System.currentTimeMillis() / 5.0 * (tileEntityIn.spinSpeed.get() / 20.0);
		if (!isCore && tileEntityIn.getBlockState().getBlock().getRenderShape(tileEntityIn.getBlockState()) != RenderShape.INVISIBLE) {
			matrixStackIn.pushPose();
			matrixStackIn.translate(8 / 16.0, 4.75 / 16.0, 8 / 16.0);
			matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.translate(8 / 16.0, 7.75 / 16.0, 8 / 16.0);
			matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360 + 22.5f), 0, true));
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.translate(8 / 16.0, 10.75 / 16.0, 8 / 16.0);
			matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360 + 45.0f), 0, true));
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
		} else if (isCore) {
			float size = 3;
			matrixStackIn.pushPose();
			matrixStackIn.translate(8 / 16.0, 4.75 / 16.0, 8 / 16.0);
			matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
			matrixStackIn.scale(size, 1, size);
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.translate(8 / 16.0, 7.75 / 16.0, 8 / 16.0);
			matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360 + 22.5f), 0, true));
			matrixStackIn.scale(size, 1, size);
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.translate(8 / 16.0, 10.75 / 16.0, 8 / 16.0);
			matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360 + 45.0f), 0, true));
			matrixStackIn.scale(size, 1, size);
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.translate(0.5, 0.5, 0.5);
			matrixStackIn.scale(size, 1, size);
			ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TURBINECASING);
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
			matrixStackIn.popPose();
		}
	}

}
