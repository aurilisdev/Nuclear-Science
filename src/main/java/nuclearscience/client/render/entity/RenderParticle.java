package nuclearscience.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import nuclearscience.common.entity.EntityParticle;

public class RenderParticle extends EntityRenderer<EntityParticle> {

	public RenderParticle(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(EntityParticle entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.scale(0.01f, 0.01f, 0.01f);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, entityIn.tickCount + partialTicks, 60, 1, 1, 1, 0.3f, true);
		matrixStackIn.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityParticle entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}

}
