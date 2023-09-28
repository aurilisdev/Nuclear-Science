package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileGasCentrifuge;

public class RenderGasCentrifuge extends AbstractTileRenderer<TileGasCentrifuge> {

	public RenderGasCentrifuge(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileGasCentrifuge tile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_GASCENTRIFUGECENTER);
		poseStack.translate(0.5, 0.5, 0.5);
		double daytime = System.currentTimeMillis() / 5.0 * (tile.spinSpeed.get() / 20.0);
		poseStack.mulPose(MathUtils.rotQuaternionDeg(0, (float) (daytime * 20 % 360), 0));
		// poseStack.mulPose(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
		RenderingUtils.renderModel(ibakedmodel, tile, RenderType.solid(), poseStack, bufferIn, combinedLightIn, combinedOverlayIn);
	}

}
