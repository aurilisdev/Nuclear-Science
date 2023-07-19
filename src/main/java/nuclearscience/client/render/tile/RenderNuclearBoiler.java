package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.client.ClientRegister;
import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.tile.TileNuclearBoiler;

public class RenderNuclearBoiler extends AbstractTileRenderer<TileNuclearBoiler> {

	private static final float DELTA_Y = 3.6F / 16.0F;

	public RenderNuclearBoiler(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileNuclearBoiler tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int light, int overlay) {

		matrix.pushPose();

		Direction facing = tile.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
		VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());

		FluidTank input = tile.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];

		if (!input.isEmpty()) {

			drawFluidInput(matrix, builder, input.getFluid(), facing, (float) input.getFluidAmount() / (float) TileNuclearBoiler.MAX_FLUID_TANK_CAPACITY, light, overlay);

		}

		matrix.popPose();

		matrix.pushPose();

		GasTank output = tile.<ComponentGasHandlerMulti>getComponent(ComponentType.GasHandler).getOutputTanks()[0];

		if (!output.isEmpty()) {

			drawGasOutput(matrix, builder, output.getGas(), facing, (float) output.getGasAmount() / (float) TileNuclearBoiler.MAX_GAS_TANK_CAPACITY, light, overlay);

		}

		matrix.popPose();

	}

	private void drawFluidInput(PoseStack stack, VertexConsumer builder, FluidStack fluid, Direction facing, float height, int light, int overlay) {

		AABB box = null;

		float maxY = DELTA_Y * height + 8.2F / 16.0F;

		if (facing == Direction.NORTH) {

			box = new AABB(8.7 / 16.0, 8.2 / 16, 6.2 / 16.0, 11.3 / 16.0, maxY, 9.8 / 16.0);

		} else if (facing == Direction.EAST) {

			box = new AABB(6.2 / 16.0, 8.2 / 16, 8.7 / 16.0, 9.8 / 16.0, maxY, 11.3 / 16.0);

		} else if (facing == Direction.SOUTH) {

			box = new AABB(4.7 / 16.0, 8.2 / 16, 6.2 / 16.0, 7.3 / 16.0, maxY, 9.8 / 16.0);

		} else {

			box = new AABB(6.2 / 16.0, 8.2 / 16, 4.7 / 16.0, 9.8 / 16.0, maxY, 7.3 / 16.0);

		}

		RenderingUtils.renderFluidBox(stack, minecraft(), builder, box, fluid, light, overlay);
	}

	private void drawGasOutput(PoseStack stack, VertexConsumer builder, GasStack fluid, Direction facing, float height, int light, int overlay) {

		AABB box = null;

		float maxY = DELTA_Y * height + 8.2F / 16.0F;

		if (facing == Direction.NORTH) {

			box = new AABB(4.7 / 16.0, 8.2 / 16, 6.2 / 16.0, 7.3 / 16.0, maxY, 9.8 / 16.0);

		} else if (facing == Direction.EAST) {

			box = new AABB(6.2 / 16.0, 8.2 / 16, 4.7 / 16.0, 9.8 / 16.0, maxY, 7.3 / 16.0);

		} else if (facing == Direction.SOUTH) {

			box = new AABB(8.7 / 16.0, 8.2 / 16, 6.2 / 16.0, 11.3 / 16.0, maxY, 9.8 / 16.0);
		} else {

			box = new AABB(6.2 / 16.0, 8.2 / 16, 8.7 / 16.0, 9.8 / 16.0, maxY, 11.3 / 16.0);

		}

		ResourceLocation texture = ClientRegister.TEXTURE_GAS;

		TextureAtlasSprite sprite = ClientRegister.CACHED_TEXTUREATLASSPRITES.get(texture);
		
		float[] colors = RenderingUtils.getColorArray(sprite.getPixelRGBA(0, 10, 10));
		
		RenderingUtils.renderFilledBox(stack, builder, box, colors[0], colors[1], colors[2], colors[3], sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), light, overlay);
	}

}
