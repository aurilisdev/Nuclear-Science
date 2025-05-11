package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileMSInterface;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderMSInterface extends AbstractTileRenderer<TileMSInterface> {

    private static final double MAX_DELTA = 10.0 / 16.0;

    public RenderMSInterface(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(TileMSInterface tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        stack.pushPose();

        stack.translate(0.5, 0.5, 0.5);

        Direction facing = tile.getFacing();

        int sign = 1;

        if (facing == Direction.WEST || facing == Direction.EAST) {
            sign = -1;
        }

        stack.mulPose(new Quaternion(0, facing.toYRot() + sign * 90, 0, true));

        double insertion = tile.insertion.getValue() / (double) TileControlRod.MAX_EXTENSION;

        stack.translate(MAX_DELTA * insertion, 0, 0);

        RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_MSCONTROLROD_ROD), tile, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

        stack.popPose();

    }

}
