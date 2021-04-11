package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.utilities.UtilitiesRendering;
import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileChemicalExtractor;

public class RenderChemicalExtractor extends TileEntityRenderer<TileChemicalExtractor> {

    public RenderChemicalExtractor(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileChemicalExtractor tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALEXTRACTORWATER);
	Direction face = tileEntityIn.getBlockState().get(BlockGenericMachine.FACING);
	matrixStackIn.translate(face.getXOffset(), face.getYOffset(), face.getZOffset());
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(-0.5, 0, 0.5);
	float prog = tileEntityIn.<ComponentFluidHandler>getComponent(ComponentType.FluidHandler).getStackFromFluid(Fluids.WATER).getAmount()
		/ (float) TileChemicalExtractor.TANKCAPACITY;
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 5.8f * 2, 1);
	    matrixStackIn.translate(0, prog / 16.0f * 5.8f, 0);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getCutout(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
    }

}
