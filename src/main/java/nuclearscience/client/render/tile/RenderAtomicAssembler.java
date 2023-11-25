package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.tile.TileAtomicAssembler;

public class RenderAtomicAssembler extends AbstractTileRenderer<TileAtomicAssembler> {

	public RenderAtomicAssembler(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(TileAtomicAssembler tile, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ComponentInventory inv = tile.getComponent(IComponentType.Inventory);
		ItemStack stack = inv.getItem(6);
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5, 1 / 16.0 + (stack.getItem() instanceof BlockItem ? 0.48 : 0.39), 0.5);
			poseStack.scale(0.35f, 0.35f, 0.35f);
			if (!(stack.getItem() instanceof BlockItem)) {
				poseStack.mulPose(Vector3f.XN.rotationDegrees(90));
			} else {
				poseStack.scale(0.3f, 0.3f, 0.3f);
				poseStack.translate(0, -0.5, 0);
			}
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, poseStack, bufferIn, 0);
			poseStack.popPose();
		}
	}

}
