package nuclearscience.client.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.common.entity.EntityParticle;

public class RenderParticle extends EntityRenderer<EntityParticle> {

    public RenderParticle(EntityRenderDispatcher renderManager) {
	super(renderManager);
    }

    @Override
    public void render(EntityParticle entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
	    int packedLightIn) {
	Tesselator tessellator = Tesselator.getInstance();
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
	    float par2 = entityIn.level.getLevelData().getGameTime() * 3 % 180;
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
		bufferBuilder.begin(6, DefaultVertexFormat.POSITION_COLOR);
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
    public ResourceLocation getTextureLocation(EntityParticle entity) {
	return TextureAtlas.LOCATION_BLOCKS;
    }

}
