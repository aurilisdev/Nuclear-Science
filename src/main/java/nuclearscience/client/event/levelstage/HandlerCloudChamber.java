package nuclearscience.client.event.levelstage;

import java.util.HashSet;
import java.util.Iterator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import nuclearscience.common.tile.TileCloudChamber;
import voltaic.client.event.AbstractLevelStageHandler;

public class HandlerCloudChamber extends AbstractLevelStageHandler {

    public static final HandlerCloudChamber INSTANCE = new HandlerCloudChamber();

    private HashSet<TileCloudChamber> locations = new HashSet<>();

    @Override
    public void render(WorldRenderer context, MatrixStack poseStack, float partialTicks, Matrix4f projectionMatrix, long finishTimeNano) {

    	Minecraft minecraft = Minecraft.getInstance();
		IRenderTypeBuffer.Impl buffer = minecraft.renderBuffers().bufferSource();
		IVertexBuilder builder = buffer.getBuffer(RenderType.LINES);
		Vector3d camPos = minecraft.gameRenderer.getMainCamera().getPosition();

        Iterator<TileCloudChamber> it = locations.iterator();

        while (it.hasNext()) {

            TileCloudChamber chamber = it.next();

            if(chamber == null || chamber.isRemoved() || !chamber.hasLevel() || !chamber.getLevel().isLoaded(chamber.getBlockPos()) || !chamber.active.getValue()) {
                it.remove();
                continue;
            }

            chamber.sources.getValue().forEach(source -> {
                AxisAlignedBB outline = new AxisAlignedBB(source);

                poseStack.pushPose();
                poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
                WorldRenderer.renderLineBox(poseStack, builder, outline, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            });



        }

        buffer.endBatch(RenderType.LINES);


    }

    @Override
    public void clear() {
        locations.clear();
    }

    public static void addSources(TileCloudChamber chamber) {
        INSTANCE.locations.add(chamber);
    }
}
