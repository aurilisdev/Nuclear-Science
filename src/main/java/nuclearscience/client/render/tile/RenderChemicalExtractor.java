package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.tile.TileChemicalExtractor;

public class RenderChemicalExtractor extends AbstractTileRenderer<TileChemicalExtractor> {

	private static final float DELTA_Y = 7.0F / 16.0F;

	public RenderChemicalExtractor(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileChemicalExtractor tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int overlay) {

		matrix.pushPose();

		Direction facing = tile.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		ComponentFluidHandlerMulti multi = tile.getComponent(ComponentType.FluidHandler);
		VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());

		FluidTank input = multi.getInputTanks()[0];

		if (input.isEmpty()) {
			matrix.popPose();
			return;
		}

		AABB box = null;
		int i = 0;

		float maxY = DELTA_Y * ((float) input.getFluidAmount() / (float) TileChemicalExtractor.MAX_TANK_CAPACITY) + 5.0F / 16.0F;

		if (facing == Direction.NORTH) {

			box = new AABB(2.7 / 16.0, 5 / 16.0, 2.2 / 16.0, 4.3 / 16.0, maxY, 3.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(3.0 / 16.0, 0, 0);
			}

			box = new AABB(2.7 / 16.0, 5 / 16.0, 12.2 / 16.0, 4.3 / 16.0, maxY, 13.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(3.0 / 16.0, 0, 0);
			}

		} else if (facing == Direction.EAST) {

			box = new AABB(2.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 3.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(0, 0, 3.0 / 16.0);
			}

			box = new AABB(12.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 13.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(0, 0, 3.0 / 16.0);
			}

		} else if (facing == Direction.SOUTH) {

			box = new AABB(2.7 / 16.0, 5 / 16.0, 2.2 / 16.0, 4.3 / 16.0, maxY, 3.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(3.0 / 16.0, 0, 0);
			}

			box = new AABB(2.7 / 16.0, 5 / 16.0, 12.2 / 16.0, 4.3 / 16.0, maxY, 13.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(3.0 / 16.0, 0, 0);
			}

		} else {

			box = new AABB(2.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 3.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(0, 0, 3.0 / 16.0);
			}

			box = new AABB(12.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 13.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(0, 0, 3.0 / 16.0);
			}

		}

		matrix.popPose();
	}

}
