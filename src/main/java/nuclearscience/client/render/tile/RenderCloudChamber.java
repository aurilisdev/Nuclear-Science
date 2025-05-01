package nuclearscience.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import nuclearscience.common.tile.TileCloudChamber;
import voltaic.Voltaic;
import voltaic.client.render.AbstractTileRenderer;

public class RenderCloudChamber extends AbstractTileRenderer<TileCloudChamber> {

    private static final double MAX_COUNT = 20.0;

    public RenderCloudChamber(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull TileCloudChamber tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(!tile.active.getValue()) {
            return;
        }

        double countPerc = Math.min(1.0, tile.sources.getValue().size() / MAX_COUNT);

        if(level().getRandom().nextFloat() > countPerc * 0.2) {
            return;
        }

        BlockPos pos = tile.getBlockPos();

        double x = Voltaic.RANDOM.nextDouble(0.75) + 0.125;
        double y = Voltaic.RANDOM.nextDouble(0.75) + 0.125;
        double z = Voltaic.RANDOM.nextDouble(0.75) + 0.125;

        minecraft().particleEngine.createParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 0.5F), pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);

    }
}
