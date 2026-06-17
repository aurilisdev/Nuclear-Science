package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.AABB;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFusionInterface;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class RenderFusionInterface extends AbstractTileRenderer<TileFusionInterface> {

    private static final double FISSION_REACTOR_FLOOR_Y = 17.0 / 16.0;
    private static final double FLOOR_Y = 1.0 / 16.0;
    private static final double DELTA_FLOOR_Y = FISSION_REACTOR_FLOOR_Y - FLOOR_Y;

    public static final AABB FUEL_CELL = new AABB(6.0 / 16.0, 2.0 / 16.0, 6.0 / 16.0, 10.0 / 16.0, 15.0 / 16.0,
	    10.0 / 16.0);
    public static final AABB FUEL_CELL_PISTON = new AABB(5.0 / 16.0, FLOOR_Y, 5.0 / 16.0, 11.0 / 16.0,
	    FLOOR_Y + 1.0 / 16.0, 11.0 / 16.0);

    public RenderFusionInterface(BlockEntityRendererProvider.Context context) {
	super(context);
    }

    @Override
    public void render(TileFusionInterface tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {

	if (tile.reactor == null || !tile.reactor.valid()
		|| !(tile.reactor.getSafe() instanceof TileFusionReactorCore)) {
	    return;
	}

	tile.reactor.getSafe();

	Long currTime = tile.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks();

	TextureAtlasSprite fuelCell = NuclearScienceClientRegister
		.getSprite(NuclearScienceClientRegister.TEXTURE_FUELCELL);
	TextureAtlasSprite white = VoltaicClientRegister.whiteSprite();

	if (tile.clientAnimations.isEmpty()) {
	    matrix.pushPose();

	    renderBox(matrix, FUEL_CELL_PISTON, RenderFissionInterface.PISTON_HEAD_GRAY, white, bufferIn,
		    combinedLightIn, RenderingUtils.ALL_FACES);

	    matrix.popPose();

	    return;
	}

	matrix.pushPose();

	tile.clientAnimations.forEach((animation, recieved) -> {

	    matrix.pushPose();

	    double perc = (double) (currTime - recieved) / (double) animation.animationTime;
	    double doublePerc = perc * 2.0;
	    double halfPerc = perc - 0.5;
	    double doubleHalfPerc = halfPerc * 2.0;
	    double oneMinusHalfPerc = 1.0 - doubleHalfPerc;

	    AABB pistonRod;
	    double y;

	    switch (animation) {

	    case FUSION_TRITIUM_INSERT:

		if (perc < 0.5) {

		    y = DELTA_FLOOR_Y * doublePerc;

		    pistonRod = new AABB(7.0 / 16.0, FLOOR_Y, 7.0 / 16.0, 9.0 / 16.0, FLOOR_Y + y, 9.0 / 16.0);
		    renderBox(matrix, pistonRod, RenderFissionInterface.PISTON_ROD_GRAY, white, bufferIn,
			    combinedLightIn, RenderingUtils.ALL_FACES);

		    matrix.translate(0, y, 0);

		    renderBox(matrix, FUEL_CELL, RenderFissionReactorCore.TRITIUM, fuelCell, bufferIn, combinedLightIn,
			    RenderingUtils.ALL_FACES);
		} else {

		    y = DELTA_FLOOR_Y * oneMinusHalfPerc;

		    pistonRod = new AABB(7.0 / 16.0, FLOOR_Y, 7.0 / 16.0, 9.0 / 16.0, FLOOR_Y + y, 9.0 / 16.0);

		    renderBox(matrix, pistonRod, RenderFissionInterface.PISTON_ROD_GRAY, white, bufferIn,
			    combinedLightIn, RenderingUtils.ALL_FACES);

		    matrix.translate(0, y, 0);
		}
		renderBox(matrix, FUEL_CELL_PISTON, RenderFissionInterface.PISTON_HEAD_GRAY, white, bufferIn,
		    combinedLightIn, RenderingUtils.ALL_FACES);

		break;

	    case FUSION_DEUTERIUM_INSERT:

		if (perc < 0.5) {

		    y = DELTA_FLOOR_Y * doublePerc;

		    pistonRod = new AABB(7.0 / 16.0, FLOOR_Y, 7.0 / 16.0, 9.0 / 16.0, FLOOR_Y + y, 9.0 / 16.0);
		    renderBox(matrix, pistonRod, RenderFissionInterface.PISTON_ROD_GRAY, white, bufferIn,
			    combinedLightIn, RenderingUtils.ALL_FACES);

		    matrix.translate(0, y, 0);

		    renderBox(matrix, FUEL_CELL, RenderFissionReactorCore.DEUTERIUM, fuelCell, bufferIn,
			    combinedLightIn, RenderingUtils.ALL_FACES);
		} else {

		    y = DELTA_FLOOR_Y * oneMinusHalfPerc;

		    pistonRod = new AABB(7.0 / 16.0, FLOOR_Y, 7.0 / 16.0, 9.0 / 16.0, FLOOR_Y + y, 9.0 / 16.0);

		    renderBox(matrix, pistonRod, RenderFissionInterface.PISTON_ROD_GRAY, white, bufferIn,
			    combinedLightIn, RenderingUtils.ALL_FACES);

		    matrix.translate(0, y, 0);
		}
		renderBox(matrix, FUEL_CELL_PISTON, RenderFissionInterface.PISTON_HEAD_GRAY, white, bufferIn,
		    combinedLightIn, RenderingUtils.ALL_FACES);

		break;

	    }

	    matrix.popPose();

	});

	matrix.popPose();

    }

    private static void renderBox(PoseStack matrix, AABB box, Color color, TextureAtlasSprite texture,
	    MultiBufferSource bufferIn, int combinedLightIn, boolean[] faces) {
	RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.SOLID), box, color.rFloat(),
		color.gFloat(), color.bFloat(), color.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(),
		texture.getV1(), combinedLightIn, faces);
    }
}
