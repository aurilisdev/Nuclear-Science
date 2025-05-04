package nuclearscience.client.render.entity;

import java.util.Random;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import nuclearscience.common.entity.EntityParticle;

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


		matrixStackIn.scale(scale / 4.0F, scale / 4.0F, scale / 4.0F);
		int distance = entityIn.level.getRandom().nextInt(1, 11);

		long gameTime = entityIn.level.getGameTime();

		//float scale = (float) Math.abs(Math.sin((gameTime + partialTicks) / 40.0)) * 0.001f + 0.001f;
		//matrixStack.scale(scale, scale, scale);

		renderStar(matrixStackIn, bufferIn, gameTime + partialTicks, 250 / distance, 1, 1, 1, 0.3F, false);
		renderStar(matrixStackIn, bufferIn, gameTime + 20f + partialTicks, 250 / distance, 1, 1, 1, 0.3F, false);
		renderStar(matrixStackIn, bufferIn, gameTime + 40f + partialTicks, 250 / distance, 1, 1, 1, 0.3F, false);

		renderStar(matrixStackIn, bufferIn, gameTime + 60f + partialTicks, 250 / distance, 1, 1, 1, 0.3F, false);

		matrixStackIn.popPose();
	}
	
	public static void renderStar(PoseStack stack, MultiBufferSource bufferIn, float time, int starFrags, float r, float g, float b, float a, boolean star) {
    	stack.pushPose();
		try {
			float f5 = time / 200.0F;
			Random random = new Random(432L);
			VertexConsumer vertexconsumer2 = bufferIn.getBuffer(RenderType.lightning());
			stack.pushPose();
			stack.translate(0.0D, -1.0D, 0.0D);

			for (int i = 0; i < starFrags; ++i) {
				stack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
				stack.mulPose(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
				float f3 = random.nextFloat() * 20.0F + 1.0F;
				float f4 = random.nextFloat() * 2.0F + 1.0F + (star ? 0 : 100);
				Matrix4f matrix4f = stack.last().pose();
				vertexconsumer2.vertex(matrix4f, 0.1F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, f3, 1.0F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
			}

			stack.popPose();
			if (bufferIn instanceof BufferSource source) {
				source.endBatch(RenderType.lightning());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stack.popPose();
    }

	@Override
	public ResourceLocation getTextureLocation(EntityParticle entity) {
		return InventoryMenu.BLOCK_ATLAS;
	}

}
