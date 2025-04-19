package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import nuclearscience.common.tile.TileQuantumTunnel;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.RenderingUtils;

public class RenderQuantumTunnel extends AbstractTileRenderer<TileQuantumTunnel> {

	public RenderQuantumTunnel(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileQuantumTunnel tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5, 0.5, 0.5);
		float scale = (float) Math.abs(Math.sin((tileEntityIn.<ComponentTickable>getComponent(IComponentType.Tickable).getTicks() + partialTicks) / 40.0)) * 0.001f + 0.001f;
		matrixStackIn.scale(scale, scale, scale);
		float distance = (float) Math.sqrt(1 + Minecraft.getInstance().player.distanceToSqr(tileEntityIn.getBlockPos().getX() + 0.5, tileEntityIn.getBlockPos().getY() + 0.5, tileEntityIn.getBlockPos().getZ() + 0.5));
		RenderingUtils.renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.2f + 0.1f, 0, 0, 1f, false);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + 20f + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.1f + 0.3f, 0, 0, 0.6f, false);
		RenderingUtils.renderStar(matrixStackIn, bufferIn, tileEntityIn.getLevel().getLevelData().getDayTime() + 40f + partialTicks, (int) (250 / distance), tileEntityIn.getLevel().random.nextFloat() * 0.3f + 0.5f, 0, 0, 0.2f, false);
		matrixStackIn.popPose();
	}

}
