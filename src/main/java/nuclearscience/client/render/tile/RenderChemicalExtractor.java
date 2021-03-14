package nuclearscience.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentFluidHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileChemicalExtractor;

public class RenderChemicalExtractor extends TileEntityRenderer<TileChemicalExtractor> {

    public RenderChemicalExtractor(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileChemicalExtractor tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
	    IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager()
		.getModel(ClientRegister.MODEL_CHEMICALBOILERWATER);
	Direction face = tileEntityIn.getBlockState().get(BlockGenericMachine.FACING);
	matrixStackIn.translate(8 / 16.0, 6 / 16.0, (8.0 - 0.16667) / 16.0);
	if (face == Direction.NORTH || face == Direction.SOUTH) {
	    matrixStackIn.rotate(new Quaternion(0, 90, 0, true));
	}
	float prog = tileEntityIn.<ComponentFluidHandler>getComponent(ComponentType.FluidHandler)
		.getStackFromFluid(Fluids.WATER).getAmount() / (float) TileChemicalExtractor.TANKCAPACITY;
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 5.8f * 2, 1);
	    Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
		    tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(),
		    matrixStackIn, bufferIn.getBuffer(RenderType.getCutout()), false, tileEntityIn.getWorld().rand,
		    new Random().nextLong(), 1);
	}
    }

}
