package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.block.BlockRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileTurbine;

public class RenderTurbine extends TileEntityRenderer<TileTurbine> {

    public RenderTurbine(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileTurbine tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	boolean isCore = tileEntityIn.isCore;
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TURBINEROTORLAYER);
	double daytime = System.currentTimeMillis() / 5.0 * (tileEntityIn.spinSpeed / 20.0);
	if (!isCore && tileEntityIn.getBlockState().getBlock().getRenderType(tileEntityIn.getBlockState()) != BlockRenderType.INVISIBLE) {
	    matrixStackIn.push();
	    matrixStackIn.translate(8 / 16.0, 4.75 / 16.0, 8 / 16.0);
	    matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	    matrixStackIn.push();
	    matrixStackIn.translate(8 / 16.0, 7.75 / 16.0, 8 / 16.0);
	    matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360 + 22.5f), 0, true));
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	    matrixStackIn.push();
	    matrixStackIn.translate(8 / 16.0, 10.75 / 16.0, 8 / 16.0);
	    matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360 + 45.0f), 0, true));
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	} else if (isCore) {
	    float size = 3;
	    matrixStackIn.push();
	    matrixStackIn.translate(8 / 16.0, 4.75 / 16.0, 8 / 16.0);
	    matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
	    matrixStackIn.scale(size, 1, size);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	    matrixStackIn.push();
	    matrixStackIn.translate(8 / 16.0, 7.75 / 16.0, 8 / 16.0);
	    matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360 + 22.5f), 0, true));
	    matrixStackIn.scale(size, 1, size);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	    matrixStackIn.push();
	    matrixStackIn.translate(8 / 16.0, 10.75 / 16.0, 8 / 16.0);
	    matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360 + 45.0f), 0, true));
	    matrixStackIn.scale(size, 1, size);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	    matrixStackIn.push();
	    matrixStackIn.translate(0.5, 0.5, 0.5);
	    matrixStackIn.scale(size, 1, size);
	    ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TURBINECASING);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	    matrixStackIn.pop();
	}
    }

}
