package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderFissionControlRod extends AbstractTileRenderer<TileControlRod.TileFissionControlRod> {

	private static final double START_Y = 0;
	private static final double MAX_Y = 13.0 / 16.0;

	public RenderFissionControlRod(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileControlRod.TileFissionControlRod tileEntityIn, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);

		double insertion = tileEntityIn.insertion.getValue() / (double) TileControlRod.MAX_EXTENSION;

		stack.translate(0, START_Y + MAX_Y * insertion, 0);

		RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_FISSIONCONTROLROD_ROD), tileEntityIn, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

		stack.popPose();
	}
}
