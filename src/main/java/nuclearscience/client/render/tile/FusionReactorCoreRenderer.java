package nuclearscience.client.render.tile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileFusionReactorCore;

public class FusionReactorCoreRenderer extends TileEntityRenderer<TileFusionReactorCore> {

	public FusionReactorCoreRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(TileFusionReactorCore tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_FUSIONREACTOR);
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModelFlat(tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
				bufferIn.getBuffer(RenderType.getSolid()), true, tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferBuilder = tessellator.getBuffer();

		float par2 = tileEntityIn.getWorld().getWorldInfo().getGameTime() * 3 % 180;

		float var41 = (5.0F + par2) / 200.0F;
		float var51 = 0.0F;

		if (var41 > 0.8F) {
			var51 = (var41 - 0.8F) / 0.2F;
		}
		Random rand = new Random(432L);
		ActiveRenderInfo activeRenderInfoIn = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
		Vector3d vector3d = activeRenderInfoIn.getProjectedView();
		double d0 = vector3d.getX();
		double d1 = vector3d.getY();
		double d2 = vector3d.getZ();
		BlockPos blockpos = tileEntityIn.getPos();
		RenderHelper.disableStandardItemLighting();
		GL11.glPushMatrix();
		GlStateManager.enableDepthTest();
		Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
		RenderSystem.multMatrix(matrix4f);
		GL11.glTranslated(-d0, -d1, -d2);
		GL11.glTranslated(blockpos.getX() + 1, blockpos.getY() + 0.75, blockpos.getZ() + 1);
		float scale = 0.03f;
		GL11.glScalef(scale, scale, scale);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(false);
		GL11.glPushMatrix();
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
			bufferBuilder.pos(0, 0, 0).color(148, 0, 184, 200).endVertex();
			bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3).color(255, 255, 255, 0).endVertex();
			bufferBuilder.pos(0.866 * f3, f2, -0.5 * f3).color(255, 255, 255, 0).endVertex();
			bufferBuilder.pos(0, f2, 1 * f3).color(255, 255, 255, 0).endVertex();
			bufferBuilder.pos(-0.866 * f3, f2, -0.5 * f3).color(255, 255, 255, 0).endVertex();
			tessellator.draw();
		}
		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.disableDepthTest();
		GL11.glPopMatrix();

	}

}
