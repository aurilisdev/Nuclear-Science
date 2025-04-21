package nuclearscience.client.render.tile;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.utilities.math.Color;
import voltaic.prefab.utilities.math.MathUtils;

public class RenderThermometerModule extends AbstractTileRenderer<TileThermometerModule> {

    public RenderThermometerModule(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull TileThermometerModule tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(!tile.linked.getValue()) {
            return;
        }

        Font font = Minecraft.getInstance().font;

        Direction facing = tile.getFacing();


        stack.pushPose();


        stack.translate(0.5, 0.5, 0.5);

        rotateMatrix(stack, facing);

        /* MONITORED TEMPERATURE */

        stack.pushPose();

        stack.translate(0, 0.175, -0.46875);

        Component transfer = ChatFormatter.getChatDisplayShort(tile.trackedTemperature.getValue(), DisplayUnits.TEMPERATURE_CELCIUS);

        int width = font.width(transfer);

        float scale = 0.0215F / (width / 24.0F);

        stack.scale(-scale, -scale, -scale);

        Matrix4f matrix4f = stack.last().pose();

        float textX = -width / 2.0f;

        font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, 0, combinedLightIn);

        stack.popPose();

        /* TARGET TEMPERATURE */

        stack.pushPose();

        stack.translate(0, -0.0625, -0.46875);

        transfer = ChatFormatter.getChatDisplayShort(tile.targetTemperature.getValue(), DisplayUnits.TEMPERATURE_CELCIUS);

        width = font.width(transfer);

        scale = 0.0215F / (width / 24.0F);

        stack.scale(-scale, -scale, -scale);

        matrix4f = stack.last().pose();

        textX = -width / 2.0f;

        font.drawInBatch(transfer, textX, 0, Color.WHITE.color(), false, matrix4f, bufferIn, Font.DisplayMode.NORMAL, 0, combinedLightIn);

        stack.popPose();


        stack.popPose();

    }

    private void rotateMatrix(PoseStack stack, Direction dir) {
        switch (dir) {
            case EAST -> stack.mulPose(MathUtils.rotQuaternionDeg(0, -90, 0));// stack.mulPose(new Quaternion(0, -90, 0, true));
            case SOUTH -> stack.mulPose(MathUtils.rotQuaternionDeg(0, 180, 0));// stack.mulPose(new Quaternion(0, 180, 0, true));
            case WEST -> stack.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));// stack.mulPose(new Quaternion(0, 90, 0, true));
            default -> {
            }
        }
    }
}
