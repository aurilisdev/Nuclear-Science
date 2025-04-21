package nuclearscience.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import nuclearscience.common.entity.EntityParticle;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderParticle extends EntityRenderer<EntityParticle> {

	private static final float MAX_SCALE = 0.02F;
	private static final float MIN_SCALE = 0.01F;
	private static final float DELTA_SCALE = MAX_SCALE - MIN_SCALE;

	public RenderParticle(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(EntityParticle entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();

		float perc = (500 - System.currentTimeMillis() % 1000) / 500.0F;

		float scale;

		if(perc < 0) {

			perc *= -1.0F;

			//perc = 1.0F - perc;

			scale = MAX_SCALE - DELTA_SCALE * perc;


		} else {

			perc = 1.0F - perc;

			scale = MIN_SCALE + DELTA_SCALE * perc;

		}


		matrixStackIn.scale(scale, scale, scale);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, entityIn.tickCount + partialTicks, 60, 1, 1, 1, 0.3f, true);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityParticle entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}

}
