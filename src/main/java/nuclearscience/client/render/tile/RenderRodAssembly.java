package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.tile.TileControlRodAssembly;

public class RenderRodAssembly extends AbstractTileRenderer<TileControlRodAssembly> {

	public RenderRodAssembly(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileControlRodAssembly tileEntityIn, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		stack.pushPose();
		IBakedModel ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSTRUCTURE);
		stack.translate(0.5, 0.5, 0.5);
		if (tileEntityIn.isMSR.get()) {
			Direction dir = Direction.values()[tileEntityIn.direction.get()];
			stack.mulPose(new Quaternion(90, 0, dir.toYRot(), true));
		}
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
		int insertion = tileEntityIn.insertion.get() - 100;
		stack.translate(0, 12 / 16.0 * insertion / 100.0 + .5 / 16.0, 0);
		ibakedmodel = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_CONTROLRODASSEMBLYSROD);
		RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);
		stack.popPose();
	}
}