package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileControlRodAssembly;

public class RenderRodAssembly implements BlockEntityRenderer<TileControlRodAssembly> {

    @Override
    public void render(TileControlRodAssembly tileEntityIn, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	stack.pushPose();
	BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSTRUCTURE);
	stack.translate(0.5, 0.5, 0.5);
	if (tileEntityIn.isMSR) {
	    Direction dir = tileEntityIn.dir;
	    stack.mulPose(new Quaternion(90, 0, dir.toYRot(), true));
	}
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
	int insertion = tileEntityIn.insertion - 100;
	stack.translate(0, 12 / 16.0 * insertion / 100.0 + .5 / 16.0, 0);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSROD);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
	stack.popPose();
    }
}
