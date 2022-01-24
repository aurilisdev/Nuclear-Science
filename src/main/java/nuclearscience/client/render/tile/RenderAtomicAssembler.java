package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.tile.TileAtomicAssembler;

public class RenderAtomicAssembler implements BlockEntityRenderer<TileAtomicAssembler> {
	public RenderAtomicAssembler(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(TileAtomicAssembler tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		ComponentInventory inv = tileEntityIn.getComponent(ComponentType.Inventory);
		ItemStack stack = inv.getItem(6);
		if (!stack.isEmpty()) {
			matrixStackIn.pushPose();
			matrixStackIn.translate(0.5, 1 / 16.0 + (stack.getItem() instanceof BlockItem ? 0.48 : 0.39), 0.5);
			matrixStackIn.scale(0.35f, 0.35f, 0.35f);
			if (!(stack.getItem() instanceof BlockItem)) {
				matrixStackIn.mulPose(Vector3f.XN.rotationDegrees(90));
			} else {
				matrixStackIn.scale(0.3f, 0.3f, 0.3f);
				matrixStackIn.translate(0, -0.5, 0);
			}
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
			matrixStackIn.popPose();
		}
	}

}
