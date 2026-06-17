package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderRadioactiveProcessor extends AbstractTileRenderer<TileRadioactiveProcessor> {

    private static final float DELTA_Y = 4.0F / 16.0F;

    public RenderRadioactiveProcessor(BlockEntityRendererProvider.Context context) {
	super(context);
    }

    @Override
    public void render(TileRadioactiveProcessor tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer,
	    int combinedLight, int overlay) {

	matrix.pushPose();

	Direction facing = tile.getFacing();
	ComponentFluidHandlerMulti multi = tile.getComponent(IComponentType.FluidHandler);
	VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());

	FluidTank input = multi.getInputTanks()[0];

	if (input.isEmpty()) {
	    matrix.popPose();
	    return;
	}

	AABB box = null;
	float maxY = DELTA_Y * ((float) input.getFluidAmount() / (float) TileRadioactiveProcessor.MAX_TANK_CAPACITY)
		+ 7.0F / 16.0F;

	switch (facing) {
	case NORTH:
	    box = new AABB(4 / 16.0, 7 / 16.0, 0.5 / 16.0, 12 / 16.0, maxY, 1 / 16.0);

	    RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay,
		    RenderingUtils.ALL_FACES);

	    break;
	case SOUTH:
	    box = new AABB(4 / 16.0, 7 / 16.0, 15.5 / 16.0, 12 / 16.0, maxY, 15 / 16.0);

	    RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay,
		    RenderingUtils.ALL_FACES);
	    break;
	case EAST:
	    box = new AABB(15.5 / 16.0, 7 / 16.0, 4 / 16.0, 15 / 16.0, maxY, 12 / 16.0);

	    RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay,
		    RenderingUtils.ALL_FACES);
	    break;
	case WEST:
	    box = new AABB(0.5 / 16.0, 7 / 16.0, 4 / 16.0, 1 / 16.0, maxY, 12 / 16.0);

	    RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay,
		    RenderingUtils.ALL_FACES);

	    break;
	default:
	    break;
	}
	matrix.popPose();
    }

}
