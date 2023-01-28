package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class RenderQuantumCapacitor extends AbstractTileRenderer<TileQuantumCapacitor> {
	
	public RenderQuantumCapacitor(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileQuantumCapacitor tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5, 0.5, 0.5);
		float scale = (float) Math.abs(Math.sin((tileEntityIn.<ComponentTickable>getComponent(ComponentType.Tickable).getTicks() + partialTicks) / 40.0)) * 0.001f + 0.001f;
		matrixStackIn.scale(scale, scale, scale);
		float distance = (float) Math.sqrt(1 + Minecraft.getInstance().player.distanceToSqr(tileEntityIn.getBlockPos().getX() + 0.5, tileEntityIn.getBlockPos().getY() + 0.5, tileEntityIn.getBlockPos().getZ() + 0.5));
		RenderingUtils.renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.2f + 0.1f, 0, 0, 1f, false);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + 20f + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.1f + 0.3f, 0, 0, 0.6f, false);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + 40f + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.3f + 0.5f, 0, 0, 0.2f, false);
		matrixStackIn.popPose();
	}

}
