package nuclearscience.client.render.tile;

import java.util.Random;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.client.particle.smoke.ParticleOptionSmoke;
import nuclearscience.common.tile.TileFalloutScrubber;
import voltaic.Voltaic;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderFalloutScrubber extends AbstractTileRenderer<TileFalloutScrubber> {
	
    public RenderFalloutScrubber(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(@Nonnull TileFalloutScrubber tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        stack.pushPose();

        stack.translate(0.5, 0.5, 0.5);

        Direction facing = tile.getFacing();

        float roat = 0;

        if(tile.active.getValue()) {
            roat = (System.currentTimeMillis() % 150) / 150.0F * 90.0F;
        }

        if(facing == Direction.EAST) {
            stack.mulPose(new Quaternion(-roat, 0, 0, true));
        } else if (facing == Direction.WEST) {
            stack.mulPose(new Quaternion(roat, 0, 0, true));
        } else if (facing == Direction.NORTH) {
            stack.mulPose(new Quaternion(0, 90, 0, true));
            stack.mulPose(new Quaternion(-roat, 0, 0, true));
        } else if (facing == Direction.SOUTH) {
            stack.mulPose(new Quaternion(0, 90, 0, true));
            stack.mulPose(new Quaternion(roat, 0, 0, true));
        }

        BlockPos pos = tile.getBlockPos();
        Random random = Voltaic.RANDOM;

        RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_FALLOUTSCRUBBER_FAN), tile, RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

        stack.popPose();

        if(!tile.active.getValue() || random.nextFloat() > 0.4F) {
            return;
        }

        double offset = 1 + Math.min(random.nextDouble(), 0.5);

        double x = pos.getX() + Math.min(random.nextDouble(), 0.75) + 0.125;
        double y = pos.getY() + Math.min(random.nextDouble(), 0.75) + 0.12;
        double z = pos.getZ() + Math.min(random.nextDouble(), 0.75) + 0.12;

        int lifetime = (int) (10.0 * offset);

        float inColor = Math.min(random.nextFloat(), 0.5F);
        float outColor = Math.min(random.nextFloat(), 0.3F) + 0.7F;

        minecraft().particleEngine.createParticle(new ParticleOptionSmoke().setParameters(inColor, inColor, inColor, 0.1F * random.nextFloat(), 0, lifetime, false), x + -facing.getStepX() * offset , y, z + -facing.getStepZ() * offset, facing.getStepX() * 0.05, 0, facing.getStepZ() * 0.05);

        minecraft().particleEngine.createParticle(new ParticleOptionSmoke().setParameters(outColor, outColor, outColor, 0.1F * random.nextFloat(), 0, lifetime, false), x + 0.5 * facing.getStepX(), y, z + 0.5 * facing.getStepZ(), facing.getStepX() * 0.05, 0, facing.getStepZ() * 0.05);



    }
}
