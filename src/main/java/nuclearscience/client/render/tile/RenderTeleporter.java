package nuclearscience.client.render.tile;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import nuclearscience.common.tile.TileTeleporter;

public class RenderTeleporter extends AbstractTileRenderer<TileTeleporter> {
	
	public RenderTeleporter(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileTeleporter tileEntityIn, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		stack.pushPose();

		stack.translate(0.5, 0.5, 0.5);
		ComponentElectrodynamic electro = tileEntityIn.getComponent(ComponentType.Electrodynamic);

		if (electro.getJoulesStored() > 0) {
			AABB bb = new AABB(tileEntityIn.getBlockPos(), tileEntityIn.getBlockPos().offset(1, 2, 1));
			List<Player> player = tileEntityIn.getLevel().getEntities(EntityType.PLAYER, bb, en -> true);
			if (!player.isEmpty()) {

				stack.pushPose();
				// TODO: Actually render something??
				stack.popPose();
			}
		}
		stack.popPose();
	}
}
