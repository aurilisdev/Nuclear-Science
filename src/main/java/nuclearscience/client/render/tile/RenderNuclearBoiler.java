package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileNuclearBoiler;

public class RenderNuclearBoiler extends TileEntityRenderer<TileNuclearBoiler> {

    public RenderNuclearBoiler(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileNuclearBoiler tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	matrixStackIn.push();
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALBOILERWATER);
	Direction face = tileEntityIn.getBlockState().get(BlockGenericMachine.FACING);
	matrixStackIn.translate(face.getXOffset(), face.getYOffset(), face.getZOffset());
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(-0.5, 0, 0.5);
	float prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getStackFromFluid(Fluids.WATER, true)
		.getAmount() / (float) TileNuclearBoiler.MAX_TANK_CAPACITY;
	if (prog > 0) {
	    matrixStackIn.translate(0, 4.5 / 16.0, 2.0 / 16.0);
	    matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
	    matrixStackIn.translate(0, prog / 16.0f * 6f, 0);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getCutout(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
	matrixStackIn.pop();
	matrixStackIn.push();
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALBOILERHEXAFLUORIDE);
	face = tileEntityIn.getBlockState().get(BlockGenericMachine.FACING);
	matrixStackIn.translate(face.getXOffset(), face.getYOffset(), face.getZOffset());
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(-0.5, 0, 0.5);
	prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler)
		.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride, false).getAmount() / (float) TileNuclearBoiler.MAX_TANK_CAPACITY;
	if (prog > 0) {
	    matrixStackIn.translate(0, 4.5 / 16.0, -2.0 / 16.0);
	    matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
	    matrixStackIn.translate(0, prog / 16.0f * 6f, 0);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getCutout(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
	matrixStackIn.pop();
    }

}
