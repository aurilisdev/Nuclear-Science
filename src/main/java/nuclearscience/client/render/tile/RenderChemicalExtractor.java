package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluids;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileChemicalExtractor;

public class RenderChemicalExtractor implements BlockEntityRenderer<TileChemicalExtractor> {
    public RenderChemicalExtractor(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TileChemicalExtractor tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALEXTRACTORWATER);
	Direction face = tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING);
	matrixStackIn.translate(face.getStepX(), face.getStepY(), face.getStepZ());
	UtilitiesRendering.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
	matrixStackIn.translate(-0.5, 0, 0.5);
	float prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getTankFromFluid(Fluids.WATER, true)
		.getFluidAmount() / (float) TileChemicalExtractor.MAX_TANK_CAPACITY;
	if (prog > 0) {
	    matrixStackIn.scale(1, prog / 16.0f * 5.8f * 2, 1);
	    matrixStackIn.translate(0, prog / 16.0f * 5.8f, 0);
	    UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.cutout(), matrixStackIn, bufferIn, combinedLightIn,
		    combinedOverlayIn);
	}
    }

}
