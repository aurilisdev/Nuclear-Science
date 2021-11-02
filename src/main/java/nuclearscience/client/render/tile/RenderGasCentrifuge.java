package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import com.mojang.math.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileGasCentrifuge;

public class RenderGasCentrifuge extends BlockEntityRenderer<TileGasCentrifuge> {

    public RenderGasCentrifuge(BlockEntityRenderDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileGasCentrifuge tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_GASCENTRIFUGECENTER);
	matrixStackIn.translate(0.5, 0.5, 0.5);
	double daytime = System.currentTimeMillis() / 5.0 * (tileEntityIn.spinSpeed / 20.0);
	matrixStackIn.mulPose(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

}
