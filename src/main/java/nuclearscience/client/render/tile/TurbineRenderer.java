package nuclearscience.client.render.tile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileTurbine;

public class TurbineRenderer extends TileEntityRenderer<TileTurbine> {

	public TurbineRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(TileTurbine tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_TURBINEROTORLAYER);
		matrixStackIn.push();
		GL11.glDisable(GL11.GL_LIGHTING);
		matrixStackIn.translate(8 / 16.0, 4.75 / 16.0, 8 / 16.0);
		double daytime = System.currentTimeMillis() / 5 * (tileEntityIn.spinSpeed / 20.0);
		matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360), 0, true));
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
				bufferIn.getBuffer(RenderType.getCutoutMipped()), false, tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
		matrixStackIn.pop();
		matrixStackIn.push();
		GL11.glDisable(GL11.GL_LIGHTING);
		matrixStackIn.translate(8 / 16.0, 7.75 / 16.0, 8 / 16.0);
		matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360 + 22.5f), 0, true));
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
				bufferIn.getBuffer(RenderType.getCutoutMipped()), false, tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
		matrixStackIn.pop();
		matrixStackIn.push();
		GL11.glDisable(GL11.GL_LIGHTING);
		matrixStackIn.translate(8 / 16.0, 10.75 / 16.0, 8 / 16.0);
		matrixStackIn.rotate(new Quaternion(0, (float) (daytime * 20 % 360 + 45.0f), 0, true));
		Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(tileEntityIn.getWorld(), ibakedmodel, tileEntityIn.getBlockState(), tileEntityIn.getPos(), matrixStackIn,
				bufferIn.getBuffer(RenderType.getCutoutMipped()), false, tileEntityIn.getWorld().rand, new Random().nextLong(), 1);
		matrixStackIn.pop();
		RenderTypeLookup.setRenderLayer(tileEntityIn.getBlockState().getBlock(), RenderType.getSolid());

	}

}
