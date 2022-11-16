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
import net.minecraft.core.Direction;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileControlRodAssembly;

public class RenderRodAssembly implements BlockEntityRenderer<TileControlRodAssembly> {
	public RenderRodAssembly(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileControlRodAssembly tileEntityIn, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		stack.pushPose();
		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSTRUCTURE);
		stack.translate(0.5, 0.5, 0.5);
		if (tileEntityIn.isMSR.get()) {
			Direction dir = tileEntityIn.direction;
			stack.mulPose(new Quaternion(90, 0, dir.toYRot(), true));
		}
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
		int insertion = tileEntityIn.insertion.get() - 100;
		stack.translate(0, 12 / 16.0 * insertion / 100.0 + .5 / 16.0, 0);
		ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSROD);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
		stack.popPose();
	}
}
