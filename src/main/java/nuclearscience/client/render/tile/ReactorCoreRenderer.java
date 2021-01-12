package nuclearscience.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileReactorCore;

public class ReactorCoreRenderer extends TileEntityRenderer<TileReactorCore> {

	public ReactorCoreRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(TileReactorCore tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel fuelrod = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_REACTORFUELROD);
		if (tileEntityIn.fuelCount > 0) {
			for (int i = 1; i <= tileEntityIn.fuelCount; i++) {
				matrixStackIn.push();
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
				}
				matrixStackIn.scale(1, 0.8f, 1);
				Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), fuelrod, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
						bufferIn.getBuffer(RenderType.getSolid()), false, tileEntityIn.getWorld().rand, new Random().nextLong(), 0);
				matrixStackIn.pop();
			}
			if (tileEntityIn.hasDeuterium) {
				matrixStackIn.push();
				IBakedModel deuterium = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_REACTORDEUTERIUM);
				matrixStackIn.translate(0.5, 0, 0.5);
				Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), deuterium, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
						bufferIn.getBuffer(RenderType.getSolid()), false, tileEntityIn.getWorld().rand, new Random().nextLong(), 0);
				matrixStackIn.pop();
			}
		}
	}

}
