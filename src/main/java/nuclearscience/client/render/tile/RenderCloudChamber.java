package nuclearscience.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import nuclearscience.common.tile.TileCloudChamber;
import voltaic.Voltaic;
import voltaic.client.render.AbstractTileRenderer;

public class RenderCloudChamber extends AbstractTileRenderer<TileCloudChamber> {

    private static final double MAX_COUNT = 20.0;

    public RenderCloudChamber(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(@Nonnull TileCloudChamber tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(!tile.active.getValue()) {
            return;
        }

        double countPerc = Math.min(1.0, tile.sources.getValue().size() / MAX_COUNT);

        if(level().getRandom().nextFloat() > countPerc * 0.2) {
            return;
        }

        BlockPos pos = tile.getBlockPos();

        double x = Math.min(Voltaic.RANDOM.nextDouble(), 0.75) + 0.125;
        double y = Math.min(Voltaic.RANDOM.nextDouble(), 0.75) + 0.125;
        double z = Math.min(Voltaic.RANDOM.nextDouble(), 0.75) + 0.125;

        minecraft().particleEngine.createParticle(new RedstoneParticleData(1, 1, 1, 0.5F), pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);

    }
}
