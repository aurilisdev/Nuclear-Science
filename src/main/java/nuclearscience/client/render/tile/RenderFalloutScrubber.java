package nuclearscience.client.render.tile;

import java.util.Random;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.client.particle.smoke.ParticleOptionSmoke;
import nuclearscience.common.tile.TileFalloutScrubber;
import voltaic.Voltaic;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.MathUtils;

public class RenderFalloutScrubber extends AbstractTileRenderer<TileFalloutScrubber> {
    public RenderFalloutScrubber(BlockEntityRendererProvider.Context context) {
	super(context);
    }

    @Override
    public void render(@NotNull TileFalloutScrubber tile, float partialTicks, PoseStack stack,
	    MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

	stack.pushPose();

	stack.translate(0.5, 0.5, 0.5);

	Direction facing = tile.getFacing();

	float roat = 0;

	if (tile.active.getValue()) {
	    roat = System.currentTimeMillis() % 150 / 150.0F * 90.0F;
	}

	if (facing == Direction.EAST) {
	    stack.mulPose(MathUtils.rotQuaternionDeg(-roat, 0, 0));
	} else if (facing == Direction.WEST) {
	    stack.mulPose(MathUtils.rotQuaternionDeg(roat, 0, 0));
	} else if (facing == Direction.NORTH) {
	    stack.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));
	    stack.mulPose(MathUtils.rotQuaternionDeg(-roat, 0, 0));
	} else if (facing == Direction.SOUTH) {
	    stack.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));
	    stack.mulPose(MathUtils.rotQuaternionDeg(roat, 0, 0));
	}

	BlockPos pos = tile.getBlockPos();
	Random random = Voltaic.RANDOM;

	RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_FALLOUTSCRUBBER_FAN), tile,
		RenderType.solid(), stack, bufferIn, combinedLightIn, combinedOverlayIn);

	stack.popPose();

	if (!tile.active.getValue() || random.nextFloat() > 0.4F) {
	    return;
	}

	double offset = 1 + random.nextDouble(0.5);

	double x = pos.getX() + random.nextDouble(0.75) + 0.125;
	double y = pos.getY() + Voltaic.RANDOM.nextDouble(0.75) + 0.12;
	double z = pos.getZ() + random.nextDouble(0.75) + 0.12;

	int lifetime = (int) (10.0 * offset);

	float inColor = random.nextFloat(0.5F);
	float outColor = random.nextFloat(0.3F) + 0.7F;

	minecraft().particleEngine.createParticle(
		new ParticleOptionSmoke().setParameters(inColor, inColor, inColor, 0.1F * random.nextFloat(), 0,
			lifetime, false),
		x + -facing.getStepX() * offset, y, z + -facing.getStepZ() * offset, facing.getStepX() * 0.05, 0,
		facing.getStepZ() * 0.05);

	minecraft().particleEngine.createParticle(
		new ParticleOptionSmoke().setParameters(outColor, outColor, outColor, 0.1F * random.nextFloat(), 0,
			lifetime, false),
		x + 0.5 * facing.getStepX(), y, z + 0.5 * facing.getStepZ(), facing.getStepX() * 0.05, 0,
		facing.getStepZ() * 0.05);

    }
}
