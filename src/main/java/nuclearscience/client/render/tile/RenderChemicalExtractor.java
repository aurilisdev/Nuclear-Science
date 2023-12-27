package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.tile.TileChemicalExtractor;

public class RenderChemicalExtractor extends AbstractTileRenderer<TileChemicalExtractor> {

	private static final float DELTA_Y = 7.0F / 16.0F;

	public RenderChemicalExtractor(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileChemicalExtractor tile, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int overlay) {

		matrix.pushPose();

		Direction facing = tile.getFacing();
		ComponentFluidHandlerMulti multi = tile.getComponent(IComponentType.FluidHandler);
		IVertexBuilder builder = buffer.getBuffer(Atlases.translucentCullBlockSheet());

		FluidTank input = multi.getInputTanks()[0];

		if (input.isEmpty()) {
			matrix.popPose();
			return;
		}

		AxisAlignedBB box = null;
		int i = 0;

		float maxY = DELTA_Y * ((float) input.getFluidAmount() / (float) TileChemicalExtractor.MAX_TANK_CAPACITY) + 5.0F / 16.0F;

		if (facing == Direction.NORTH || facing != Direction.EAST && facing == Direction.SOUTH) {

			box = new AxisAlignedBB(2.7 / 16.0, 5 / 16.0, 2.2 / 16.0, 4.3 / 16.0, maxY, 3.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(3.0 / 16.0, 0, 0);
			}

			box = new AxisAlignedBB(2.7 / 16.0, 5 / 16.0, 12.2 / 16.0, 4.3 / 16.0, maxY, 13.8 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(3.0 / 16.0, 0, 0);
			}

		} else {

			box = new AxisAlignedBB(2.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 3.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(0, 0, 3.0 / 16.0);
			}

			box = new AxisAlignedBB(12.2 / 16.0, 5 / 16.0, 2.7 / 16.0, 13.8 / 16.0, maxY, 4.3 / 16.0);

			for (i = 0; i < 4; i++) {
				RenderingUtils.renderFluidBox(matrix, minecraft(), builder, box, input.getFluid(), combinedLight, overlay);

				box = box.move(0, 0, 3.0 / 16.0);
			}

		}

		matrix.popPose();
	}

}