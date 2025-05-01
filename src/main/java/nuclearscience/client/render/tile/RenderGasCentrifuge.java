package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.TileGasCentrifuge;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderGasCentrifuge extends AbstractTileRenderer<TileGasCentrifuge> {

	public RenderGasCentrifuge(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileGasCentrifuge tile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		BakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(NuclearScienceClientRegister.MODEL_GASCENTRIFUGECENTER);
		poseStack.translate(0.5, 0.5, 0.5);
		double daytime = System.currentTimeMillis() / 5.0 * (tile.spinSpeed.getValue() / 20.0);
		poseStack.mulPose(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
		RenderingUtils.renderModel(ibakedmodel, tile, RenderType.solid(), poseStack, bufferIn, combinedLightIn, combinedOverlayIn);
	}

}
