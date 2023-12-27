package nuclearscience.client.render.tile;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.common.tile.TileTeleporter;

public class RenderTeleporter extends AbstractTileRenderer<TileTeleporter> {

	public RenderTeleporter(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	@Deprecated
	public void render(TileTeleporter tileEntityIn, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		stack.pushPose();
		ComponentElectrodynamic electro = tileEntityIn.getComponent(IComponentType.Electrodynamic);
		if (electro.getJoulesStored() <= 0) {
			stack.popPose();
			return;
		}

		AxisAlignedBB bb = new AxisAlignedBB(tileEntityIn.getBlockPos(), tileEntityIn.getBlockPos().offset(1, 2, 1));
		List<PlayerEntity> player = tileEntityIn.getLevel().getEntities(EntityType.PLAYER, bb, en -> true);

		if (player.isEmpty()) {
			stack.popPose();
			return;
		}

		stack.pushPose();
		stack.scale(0.5f, 0.5f, 0.5f);

		GlStateManager._pushMatrix();
		RenderSystem.multMatrix(stack.last().pose());
		stack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
		stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

		GlStateManager._pushMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		GlStateManager._disableTexture();
		GlStateManager._disableLighting();
		GlStateManager._shadeModel(GL11.GL_SMOOTH);
		GlStateManager._enableBlend();
		GlStateManager.glBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager._blendFunc(770, 1);
		GlStateManager._disableAlphaTest();
		GlStateManager._enableCull();
		GlStateManager._enableDepthTest();

		GlStateManager._pushMatrix();
		float scale = 10;
		Random rand = tileEntityIn.getLevel().random;
		GlStateManager._scaled(0.05, 0.08, 0.05);
		double r = 1;
		double g = rand.nextFloat() * 0.4;
		double b = 0;
		double a = 1;
		try {
			GlStateManager._translated(0, 40, 0);
			for (int i1 = 0; i1 < (int) scale * 2; i1++) {
				bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
				GL11.glLineWidth(1f + rand.nextInt(2));
				bufferBuilder.vertex(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5, rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20, rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5, rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20, rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5, rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20, rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				tessellator.end();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GlStateManager._popMatrix();
		GlStateManager._pushMatrix();
		GlStateManager._scaled(0.05, 0.08, 0.05);
		g = rand.nextFloat() * 0.8;
		try {
			GlStateManager._translated(0, 40, 0);
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			for (int i1 = 0; i1 < (int) scale * 2; i1++) {
				bufferBuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
				GL11.glLineWidth(1f + rand.nextInt(2));
				bufferBuilder.vertex(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5, rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20, rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5, rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20, rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				bufferBuilder.vertex(rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5, rand.nextFloat() * 30 - 15 + rand.nextFloat() * 20 * 2 - 20, rand.nextFloat() * 30 - 15 + rand.nextFloat() * scale - scale * 0.5).color((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255)).endVertex();
				tessellator.end();
			}
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GlStateManager._popMatrix();

		GlStateManager._disableDepthTest();
		GlStateManager._disableBlend();
		GlStateManager._shadeModel(GL11.GL_FLAT);
		GlStateManager._color4f(1, 1, 1, 1);
		GlStateManager._enableTexture();
		GlStateManager._enableLighting();
		GlStateManager._enableAlphaTest();
		GlStateManager._popMatrix();
		GlStateManager._popMatrix();
		stack.popPose();

		stack.popPose();
	}
}
