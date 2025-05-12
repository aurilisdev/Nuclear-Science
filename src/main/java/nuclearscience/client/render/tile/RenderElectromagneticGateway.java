package nuclearscience.client.render.tile;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.AxisAlignedBB;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class RenderElectromagneticGateway extends AbstractTileRenderer<TileElectromagneticGateway> {

    public static final boolean[] FACES = {false, false, true, true, true, true};
    public static final Color COLOR = new Color(255, 0, 0, 255);
    private static final AxisAlignedBB BOX1 = new AxisAlignedBB(0.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0);
    private static final AxisAlignedBB BOX2 = new AxisAlignedBB(15.0 / 16.0, 1.0 / 16.0, 1.0 / 16.0, 16.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0);
    private static final AxisAlignedBB BOX3 = new AxisAlignedBB(1.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0, 1.0 / 16.0);
    private static final AxisAlignedBB BOX4 = new AxisAlignedBB(1.0 / 16.0, 15.0 / 16.0, 1.0 / 16.0, 15.0 / 16.0, 15.0 / 16.0, 16.0 / 16.0);

    public RenderElectromagneticGateway(TileEntityRendererDispatcher context) {
        super(context);
    }

    @Override
    public void render(@Nonnull TileElectromagneticGateway tile, float partialTicks, MatrixStack matrix, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        matrix.pushPose();

        TextureAtlasSprite texture = NuclearScienceClientRegister.getSprite(NuclearScienceClientRegister.TEXTURE_GATEWAYLASER);

        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX1, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX2, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX3, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);
        RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.translucent()), BOX4, COLOR.rFloat(), COLOR.gFloat(), COLOR.bFloat(), COLOR.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(), texture.getV1(), combinedLightIn, FACES);

        matrix.popPose();

    }
}
