package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.AABB;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFissionInterface;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class RenderFissionInterface extends AbstractTileRenderer<TileFissionInterface> {

    private static final double START_Y = 0;
    private static final double MAX_Y = 13.0 / 16.0;
    private static final double FISSION_REACTOR_FLOOR_Y = 17.8 / 16.0;
    private static final double FLOOR_Y = 1.0 / 16.0;
    private static final double DELTA_FLOOR_Y = FISSION_REACTOR_FLOOR_Y - FLOOR_Y;


    public static final AABB FUEL_ROD_PISTON_1 = new AABB(3.0 / 16.0, FLOOR_Y, 3.0 / 16.0, 4.0 / 16.0, FLOOR_Y + 0.8 / 16.0, 4.0 / 16.0);
    public static final AABB FUEL_ROD_PISTON_2 = new AABB(12.0 / 16.0, FLOOR_Y, 3.0 / 16.0, 13.0 / 16.0, FLOOR_Y + 0.8 / 16.0, 4.0 / 16.0);
    public static final AABB FUEL_ROD_PISTON_3 = new AABB(12.0 / 16.0, FLOOR_Y, 12.0 / 16.0, 13.0 / 16.0, FLOOR_Y + 0.8 / 16.0, 13.0 / 16.0);
    public static final AABB FUEL_ROD_PISTON_4 = new AABB(3.0 / 16.0, FLOOR_Y, 12.0 / 16.0, 4.0 / 16.0, FLOOR_Y + 0.8 / 16.0, 13.0 / 16.0);

    public static final AABB TRITIUM_CELL_PISTON = new AABB(7.0 / 16.0, FLOOR_Y, 7.0 / 16.0, 9.0 / 16.0, FLOOR_Y + 0.8 / 16.0, 9.0 / 16.0);


    public static final Color PISTON_HEAD_GRAY = new Color(92, 92, 92, 255);
    public static final Color PISTON_ROD_GRAY = new Color(140, 140, 140, 255);

    public RenderFissionInterface(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TileFissionInterface tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        matrix.pushPose();

        matrix.translate(0.5, 0.5, 0.5);

        double insertion = tile.insertion.getValue() / (double) TileControlRod.MAX_EXTENSION;

        matrix.translate(0, START_Y + MAX_Y * insertion, 0);

        RenderingUtils.renderModel(getModel(NuclearScienceClientRegister.MODEL_FISSIONINTERFACE_ROD), tile, RenderType.solid(), matrix, bufferIn, combinedLightIn, combinedOverlayIn);

        matrix.popPose();

        if (tile.clientAnimations.isEmpty() || !tile.reactor.valid() || !(tile.reactor.getSafe() instanceof TileFissionReactorCore)) {

            if (insertion > 0) {

                matrix.pushPose();

                TextureAtlasSprite white = VoltaicClientRegister.whiteSprite();
                renderBox(matrix, FUEL_ROD_PISTON_1, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                renderBox(matrix, FUEL_ROD_PISTON_2, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                renderBox(matrix, FUEL_ROD_PISTON_3, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                renderBox(matrix, FUEL_ROD_PISTON_4, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                renderBox(matrix, TRITIUM_CELL_PISTON, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                matrix.popPose();

            }

            return;
        }

        TileFissionReactorCore core = tile.reactor.getSafe();

        ComponentInventory coreInv = core.getComponent(IComponentType.Inventory);

        Long currTime = tile.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks();

        TextureAtlasSprite fuelCell = NuclearScienceClientRegister.getSprite(NuclearScienceClientRegister.TEXTURE_FUELCELL);
        TextureAtlasSprite white = VoltaicClientRegister.whiteSprite();

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

                case FISSION_WASTE_1:

                    if (perc > 0.5) {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_1, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_1, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_1, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_WASTE_2:

                    if (perc > 0.5) {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_2, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_2, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_2, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_WASTE_3:

                    if (perc > 0.5) {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_3, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_3, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_3, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_WASTE_4:

                    if (perc > 0.5) {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_4, RenderFissionReactorCore.SPENT, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_4, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_4, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_FUEL_1:

                    if (perc < 0.5) {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_1, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(0)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_1, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_1, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_FUEL_2:

                    if (perc < 0.5) {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_2, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(1)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_2, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 3.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 3.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_2, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_FUEL_3:

                    if (perc < 0.5) {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_3, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(2)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_3, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(12.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 12.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_3, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_FUEL_4:

                    if (perc < 0.5) {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.FUEL_ROD_4, RenderFissionReactorCore.getColorFromFuel(coreInv.getItem(3)), fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, FUEL_ROD_PISTON_4, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(3.25 / 16.0, FLOOR_Y, 12.25 / 16.0, 3.75 / 16.0, FLOOR_Y + y, 12.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, FUEL_ROD_PISTON_4, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }


                case FISSION_TRITIUM_EXTRACT:

                    if (perc > 0.5) {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(7.25 / 16.0, FLOOR_Y, 7.25 / 16.0, 8.75 / 16.0, FLOOR_Y + y, 8.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.TRITIUM_CELL, RenderFissionReactorCore.TRITIUM, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, TRITIUM_CELL_PISTON, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(7.25 / 16.0, FLOOR_Y, 7.25 / 16.0, 8.75 / 16.0, FLOOR_Y + y, 8.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, TRITIUM_CELL_PISTON, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

                case FISSION_DEUTERIUM_INSERT:

                    if (perc < 0.5) {

                        y = DELTA_FLOOR_Y * doublePerc;

                        pistonRod = new AABB(7.25 / 16.0, FLOOR_Y, 7.25 / 16.0, 8.75 / 16.0, FLOOR_Y + y, 8.75 / 16.0);
                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);

                        renderBox(matrix, RenderFissionReactorCore.TRITIUM_CELL, RenderFissionReactorCore.DEUTERIUM, fuelCell, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                        renderBox(matrix, TRITIUM_CELL_PISTON, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    } else {

                        y = DELTA_FLOOR_Y * oneMinusHalfPerc;

                        pistonRod = new AABB(7.25 / 16.0, FLOOR_Y, 7.25 / 16.0, 8.75 / 16.0, FLOOR_Y + y, 8.75 / 16.0);

                        renderBox(matrix, pistonRod, PISTON_ROD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);

                        matrix.translate(0, y, 0);
                        renderBox(matrix, TRITIUM_CELL_PISTON, PISTON_HEAD_GRAY, white, bufferIn, combinedLightIn, RenderingUtils.ALL_FACES);
                    }

                    break;

            }

            matrix.popPose();

        });


        matrix.popPose();
    }

    private void renderBox(PoseStack matrix, AABB box, Color color, TextureAtlasSprite texture, MultiBufferSource bufferIn, int combinedLightIn, boolean[] faces) {
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.solid()), box, color.rFloat(), color.gFloat(), color.bFloat(), color.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, faces);
    }

}
