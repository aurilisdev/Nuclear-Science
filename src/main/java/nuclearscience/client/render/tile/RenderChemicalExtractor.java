package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.tile.TileChemicalExtractor;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderChemicalExtractor extends AbstractTileRenderer<TileChemicalExtractor> {

	private static final float DELTA_Y = 7.0F / 16.0F;

	public RenderChemicalExtractor(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileChemicalExtractor tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int overlay) {

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
		int i = 0;

		float maxY = DELTA_Y * ((float) input.getFluidAmount() / (float) TileChemicalExtractor.MAX_TANK_CAPACITY) + 5.0F / 16.0F;

		if (facing == Direction.NORTH || facing != Direction.EAST && facing == Direction.SOUTH) {

			box = new AABB(2.7 / 16.0, 5 / 16.0, 2.2 / 16.0, 4.3 / 16.0, maxY, 3.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay, RenderingUtils.ALL_FACES);

				box = box.move(3.0 / 16.0, 0, 0);
			}

			box = new AABB(2.7 / 16.0, 5 / 16.0, 12.2 / 16.0, 4.3 / 16.0, maxY, 13.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay, RenderingUtils.ALL_FACES);

				box = box.move(3.0 / 16.0, 0, 0);
			}

		} else {

			box = new AABB(2.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 3.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay, RenderingUtils.ALL_FACES);

				box = box.move(0, 0, 3.0 / 16.0);
			}

			box = new AABB(12.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 13.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay, RenderingUtils.ALL_FACES);

				box = box.move(0, 0, 3.0 / 16.0);
			}

		}

		matrix.popPose();
	}

}
