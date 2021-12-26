package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluids;
import nuclearscience.DeferredRegisters;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileNuclearBoiler;

public class RenderNuclearBoiler implements BlockEntityRenderer<TileNuclearBoiler> {
	public RenderNuclearBoiler(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileNuclearBoiler tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
			int combinedOverlayIn) {
		matrixStackIn.pushPose();
		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALBOILERWATER);
		Direction face = tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING);
		matrixStackIn.translate(face.getStepX(), face.getStepY(), face.getStepZ());
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(-0.5, 0, 0.5);
		float prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getTankFromFluid(Fluids.WATER, true)
				.getFluidAmount() / (float) TileNuclearBoiler.MAX_TANK_CAPACITY;
		if (prog > 0) {
			matrixStackIn.translate(0, 4.5 / 16.0, 2.0 / 16.0);
			matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
			matrixStackIn.translate(0, prog / 16.0f * 6f, 0);
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.cutout(), matrixStackIn, bufferIn, combinedLightIn,
					combinedOverlayIn);
		}
		matrixStackIn.popPose();
		matrixStackIn.pushPose();
		ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CHEMICALBOILERHEXAFLUORIDE);
		face = tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING);
		matrixStackIn.translate(face.getStepX(), face.getStepY(), face.getStepZ());
		RenderingUtils.prepareRotationalTileModel(tileEntityIn, matrixStackIn);
		matrixStackIn.translate(-0.5, 0, 0.5);
		prog = tileEntityIn.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler)
				.getTankFromFluid(DeferredRegisters.fluidUraniumHexafluoride, false).getFluidAmount() / (float) TileNuclearBoiler.MAX_TANK_CAPACITY;
		if (prog > 0) {
			matrixStackIn.translate(0, 4.5 / 16.0, -2.0 / 16.0);
			matrixStackIn.scale(1, prog / 16.0f * 12f, 1);
			matrixStackIn.translate(0, prog / 16.0f * 6f, 0);
			RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.cutout(), matrixStackIn, bufferIn, combinedLightIn,
					combinedOverlayIn);
		}
		matrixStackIn.popPose();
	}

}
