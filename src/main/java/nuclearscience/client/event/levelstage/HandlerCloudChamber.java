package nuclearscience.client.event.levelstage;

import java.util.HashSet;
import java.util.Iterator;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import nuclearscience.common.tile.TileCloudChamber;
import voltaic.client.event.AbstractLevelStageHandler;

public class HandlerCloudChamber extends AbstractLevelStageHandler {

    public static final HandlerCloudChamber INSTANCE = new HandlerCloudChamber();

    private HashSet<TileCloudChamber> locations = new HashSet<>();

    @Override
    public boolean shouldRender(RenderLevelStageEvent.Stage stage) {
        return stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS;
    }

    @Override
    public void render(Camera camera, Frustum frustum, LevelRenderer renderer, PoseStack poseStack, Matrix4f projectionMatrix, Minecraft minecraft, int renderTick, float partialTick) {

        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
        Vec3 camPos = camera.getPosition();

        Iterator<TileCloudChamber> it = locations.iterator();

        while (it.hasNext()) {

            TileCloudChamber chamber = it.next();

            if(chamber == null || chamber.isRemoved() || !chamber.hasLevel() || !chamber.getLevel().isLoaded(chamber.getBlockPos()) || !chamber.active.getValue()) {
                it.remove();
                continue;
            }

            chamber.sources.getValue().forEach(source -> {
                AABB outline = new AABB(source);

                if(!frustum.isVisible(outline)) {
                    return;
                }
                poseStack.pushPose();
                poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
                LevelRenderer.renderLineBox(poseStack, builder, outline, 1.0F, 1.0F, 1.0F, 1.0F);
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
