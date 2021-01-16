package nuclearscience.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileGasCentrifuge;

public class RenderGasCentrifuge extends TileEntityRenderer<TileGasCentrifuge> {

	public RenderGasCentrifuge(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(TileGasCentrifuge tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_GASCENTRIFUGECENTER);
		matrixStackIn.translate(8 / 16.0, 5 / 16.0, 8 / 16.0);
		double daytime = System.currentTimeMillis() / 5 * (tileEntityIn.spinSpeed / 20.0);
		matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
				bufferIn.getBuffer(RenderType.getCutoutMipped()), false, tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
	}

}
