package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import electrodynamics.api.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class RenderQuantumCapacitor extends TileEntityRenderer<TileQuantumCapacitor> {

    public RenderQuantumCapacitor(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileQuantumCapacitor tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {
	GlStateManager.pushMatrix();
	matrixStackIn.translate(0.5, 0.5, 0.5);
	RenderSystem.multMatrix(matrixStackIn.getLast().getMatrix());
	matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
	matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
	float scale = 0.005f;
	GlStateManager.scalef(scale, scale, scale);
	float distance = (float) Math.sqrt(1 + Minecraft.getInstance().player.getDistanceSq(tileEntityIn.getPos().getX() + 0.5,
		tileEntityIn.getPos().getY() + 0.5, tileEntityIn.getPos().getZ() + 0.5));
	UtilitiesRendering.renderStar(tileEntityIn.getWorld().getWorldInfo().getDayTime(), (int) (250 / distance),
		tileEntityIn.getWorld().rand.nextFloat() * 0.2f + 0.2f, 0, 0, 1, false);
	UtilitiesRendering.renderStar(tileEntityIn.getWorld().getWorldInfo().getDayTime() + 20f, (int) (250 / distance),
		tileEntityIn.getWorld().rand.nextFloat() * 0.1f + 0.4f, 0, 0, 1, false);
	UtilitiesRendering.renderStar(tileEntityIn.getWorld().getWorldInfo().getDayTime() + 40f, (int) (250 / distance),
		tileEntityIn.getWorld().rand.nextFloat() * 0.3f + 0.5f, 0, 0, 1, false);
	GlStateManager.popMatrix();
    }

}
