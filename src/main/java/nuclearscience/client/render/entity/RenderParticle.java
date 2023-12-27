package nuclearscience.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.common.entity.EntityParticle;

public class RenderParticle extends EntityRenderer<EntityParticle> {

	public RenderParticle(EntityRendererManager renderManager) {
		super(renderManager);
	}

	@Override
	public void render(EntityParticle entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();

		GlStateManager._pushMatrix();
		RenderSystem.multMatrix(matrixStackIn.last().pose());
		matrixStackIn.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
		float scale = 0.03f;
		GlStateManager._scalef(scale, scale, scale);

		GlStateManager._disableTexture();
		GlStateManager._shadeModel(GL11.GL_SMOOTH);
		GlStateManager._enableBlend();
		GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager._disableAlphaTest();
		GlStateManager._enableCull();
		GlStateManager._enableDepthTest();

		GlStateManager._pushMatrix();
		try {
			float par2 = entityIn.level.getGameTime() * 3 % 180;
			float var41 = (5.0F + par2) / 200.0F;
			float var51 = 0.0F;
			if (var41 > 0.8F) {
				var51 = (var41 - 0.8F) / 0.2F;
			}
			Random rand = new Random(432L);
			for (int i1 = 0; i1 < 100; i1++) {
				GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(rand.nextFloat() * 360.0F + var41 * 90.0F, 0.0F, 0.0F, 1.0F);
				final float f2 = rand.nextFloat() * 20 + 5 + var51 * 10;
				final float f3 = rand.nextFloat() * 2 + 1 + var51 * 2;
				bufferBuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
				bufferBuilder.vertex(0, 0, 0).color(148, 0, 184, 200).endVertex();
				bufferBuilder.vertex(-0.866 * f3, f2, -0.5 * f3).color(255, 255, 255, 0).endVertex();
				bufferBuilder.vertex(0.866 * f3, f2, -0.5 * f3).color(255, 255, 255, 0).endVertex();
				bufferBuilder.vertex(0, f2, 1 * f3).color(255, 255, 255, 0).endVertex();
				bufferBuilder.vertex(-0.866 * f3, f2, -0.5 * f3).color(255, 255, 255, 0).endVertex();
				tessellator.end();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GlStateManager._popMatrix();

		GlStateManager._disableDepthTest();
		GlStateManager._disableBlend();
		GlStateManager._shadeModel(GL11.GL_FLAT);
		GlStateManager._color4f(1, 1, 1, 1);
		GlStateManager._enableTexture();
		GlStateManager._enableAlphaTest();

		GlStateManager._popMatrix();
	}

	@Override
	public ResourceLocation getTextureLocation(EntityParticle p_110775_1_) {
		return AtlasTexture.LOCATION_BLOCKS;
	}

}
