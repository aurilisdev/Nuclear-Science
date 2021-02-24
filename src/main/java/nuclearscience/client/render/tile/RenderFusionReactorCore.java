package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import nuclearscience.common.tile.TileFusionReactorCore;

public class RenderFusionReactorCore extends TileEntityRenderer<TileFusionReactorCore> {

    public RenderFusionReactorCore(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileFusionReactorCore tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
	    IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	BlockPos pos = tileEntityIn.getPos();
	if (Minecraft.getInstance().player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 32) {
	    renderFloatingText(matrixStackIn, bufferIn, "Deuterium: " + tileEntityIn.deuterium, 0.6f, 0.7f, 0.6f,
		    16777215, combinedLightIn);
	    renderFloatingText(matrixStackIn, bufferIn, "Tritium: " + tileEntityIn.tritium, 0.6f, 0.3f, 0.6f, 16777215,
		    combinedLightIn);
	}
    }

    public static void renderFloatingText(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, String text, float x,
	    float y, float z, int color, int combinedLightIn) {
	StringTextComponent displayNameIn = new StringTextComponent(text);
	matrixStackIn.push();
	matrixStackIn.translate(x, y, z);
	matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
	matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
	Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
	float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.76F);
	int j = (int) (f1 * 50.0F) << 24;
	FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
	float f2 = -fontrenderer.getStringPropertyWidth(displayNameIn) / 2;
	fontrenderer.func_243247_a(displayNameIn, f2, 0, color, false, matrix4f, bufferIn, true, j, combinedLightIn);
	matrixStackIn.pop();
    }
}
