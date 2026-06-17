package nuclearscience.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.MathUtils;

public class RenderMSControlRod extends AbstractTileRenderer<TileControlRod.TileMSControlRod> {

    private static final double MAX_DELTA = 10.0 / 16.0;

    public RenderMSControlRod(BlockEntityRendererProvider.Context context) {
	super(context);
    }

    @Override
    public void render(TileControlRod.@NotNull TileMSControlRod tile, float partialTicks, PoseStack stack,
	    MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

	stack.pushPose();

	stack.translate(0.5, 0.5, 0.5);

	Direction facing = tile.getFacing();

	int sign = 1;

	if (facing == Direction.WEST || facing == Direction.EAST) {
	    sign = -1;
	}

	stack.mulPose(MathUtils.rotQuaternionDeg(0, facing.toYRot() + sign * 90, 0));

	double insertion = tile.insertion.getValue() / (double) TileControlRod.MAX_EXTENSION;

	stack.translate(MAX_DELTA * insertion, 0, 0);

	RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_MSCONTROLROD_ROD), tile,
		RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

	stack.popPose();

    }
}
