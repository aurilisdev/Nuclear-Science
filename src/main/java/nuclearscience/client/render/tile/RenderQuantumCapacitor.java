package nuclearscience.client.render.tile;

import java.util.Random;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class RenderQuantumCapacitor extends AbstractTileRenderer<TileQuantumCapacitor> {

	public RenderQuantumCapacitor(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileQuantumCapacitor tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5, 0.5, 0.5);
		float scale = (float) Math.abs(Math.sin((tileEntityIn.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks() + partialTicks) / 40.0)) * 0.001f + 0.001f;
		matrixStackIn.scale(scale, scale, scale);
		float distance = (float) Math.sqrt(1 + Minecraft.getInstance().player.distanceToSqr(tileEntityIn.getBlockPos().getX() + 0.5, tileEntityIn.getBlockPos().getY() + 0.5, tileEntityIn.getBlockPos().getZ() + 0.5));
		renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.2f + 0.1f, 0, 0, 1f, false);
		renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + 20f + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.1f + 0.3f, 0, 0, 0.6f, false);
		renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + 40f + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.3f + 0.5f, 0, 0, 0.2f, false);
		matrixStackIn.popPose();
		

	}
	
	public void renderStar(MatrixStack stack, IRenderTypeBuffer bufferIn, float time, int starFrags, float r, float g, float b, float a, boolean star) {
		stack.pushPose();
		try {
			float f5 = time / 200.0F;
			Random random = new Random(432L);
			IVertexBuilder vertexconsumer2 = bufferIn.getBuffer(RenderType.lightning());
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
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, f3, 1.0F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
				vertexconsumer2.vertex(matrix4f, -0.866f * f4, f3, -0.5F * f4).color((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a)).endVertex();
			}

			stack.popPose();
			if (bufferIn instanceof BufferBuilder) {
				((BufferBuilder) bufferIn).end();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stack.popPose();
	}

}