package nuclearscience.client.render.tile;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.AABB;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class RenderElectromagneticGateway extends AbstractTileRenderer<TileElectromagneticGateway> {

    public static final boolean[] FACES = {false, false, true, true, true, true};
    public static final Color COLOR = new Color(255, 0, 0, 255);
	private static final AABB BOX1 = new AABB(0.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0);
	private static final AABB BOX2 = new AABB(15.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 16.0 / 16.0, 15.0 / 16.0,15.0 / 16.0);
	private static final AABB BOX3 = new AABB(1.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0, 1.0 / 16.0);
	private static final AABB BOX4 = new AABB(1.0 / 16.0, 1.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0,16.0 / 16.0);
    public RenderElectromagneticGateway(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull TileElectromagneticGateway tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        matrix.pushPose();

        TextureAtlasSprite texture = NuclearScienceClientRegister.getSprite(NuclearScienceClientRegister.TEXTURE_GATEWAYLASER);

        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX1, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX2, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX3, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX4, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);

        matrix.popPose();

    }
}
