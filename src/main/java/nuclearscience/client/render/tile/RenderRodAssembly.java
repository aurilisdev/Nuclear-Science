package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileControlRodAssembly;

public class RenderRodAssembly extends TileEntityRenderer<TileControlRodAssembly> {

    public RenderRodAssembly(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    @Deprecated
    public void render(TileControlRodAssembly tileEntityIn, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn,
	    int combinedOverlayIn) {
	stack.push();
	IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSTRUCTURE);
	stack.translate(0.5, 0.5, 0.5);
	if (tileEntityIn.isMSR) {
	    Direction dir = tileEntityIn.dir;
	    stack.rotate(new Quaternion(90, 0, dir.getHorizontalAngle(), true));
	}
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
	int insertion = tileEntityIn.insertion - 100;
	stack.translate(0, 12 / 16.0 * insertion / 100.0 + .5 / 16.0, 0);
	ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSROD);
	UtilitiesRendering.renderModel(ibakedmodel, tileEntityIn, RenderType.getSolid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
	stack.pop();
    }
}
