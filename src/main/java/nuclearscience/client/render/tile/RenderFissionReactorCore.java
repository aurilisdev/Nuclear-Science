package nuclearscience.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileFissionReactorCore;

public class RenderFissionReactorCore extends AbstractTileRenderer<TileFissionReactorCore> {

	public RenderFissionReactorCore(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileFissionReactorCore tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ModelBlockRenderer.enableCaching();
		BakedModel fuelrod = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_FISSIONREACTORFUELROD);
		if (tileEntityIn.fuelCount.get() > 0) {
			for (int i = 1; i <= Math.min(4, tileEntityIn.fuelCount.get()); i++) {
				matrixStackIn.pushPose();
				switch (i) {
				case 1:
					matrixStackIn.translate(4.0 / 16.0, 0, 4.0 / 16.0);
					break;
				case 2:
					matrixStackIn.translate(12.0 / 16.0, 0, 4.0 / 16.0);
					break;
				case 3:
					matrixStackIn.translate(12.0 / 16, 0, 12.0 / 16.0);
					break;
				case 4:
					matrixStackIn.translate(4.0 / 16.0, 0, 12.0 / 16.0);
					break;
				default:
					break;
				}
				matrixStackIn.translate(0, 0.05, 0);
				matrixStackIn.scale(1, 0.75f, 1);
				Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(tileEntityIn.getLevel(), fuelrod, tileEntityIn.getBlockState(), tileEntityIn.getBlockPos(), matrixStackIn, bufferIn.getBuffer(RenderType.solid()), false, tileEntityIn.getLevel().random, new Random().nextLong(), 0);
				matrixStackIn.popPose();
			}
		}
		if (tileEntityIn.hasDeuterium.get()) {
			matrixStackIn.pushPose();
			BakedModel deuterium = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_FISSIONREACTORDEUTERIUM);
			matrixStackIn.translate(0.5, 0, 0.5);
			Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(tileEntityIn.getLevel(), deuterium, tileEntityIn.getBlockState(), tileEntityIn.getBlockPos(), matrixStackIn, bufferIn.getBuffer(RenderType.solid()), false, tileEntityIn.getLevel().random, new Random().nextLong(), 0);
			matrixStackIn.popPose();
		}
		ModelBlockRenderer.clearCache();
	}

}
