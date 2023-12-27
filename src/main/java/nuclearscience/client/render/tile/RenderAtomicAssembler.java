package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.common.tile.TileAtomicAssembler;

public class RenderAtomicAssembler extends AbstractTileRenderer<TileAtomicAssembler> {

	public RenderAtomicAssembler(TileEntityRendererDispatcher context) {
		super(context);
	}

	@Override
	public void render(TileAtomicAssembler tile, float partialTicks, MatrixStack poseStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
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
			Minecraft.getInstance().getItemRenderer().renderStatic(stack, TransformType.NONE, combinedLightIn, combinedOverlayIn, poseStack, bufferIn);
			poseStack.popPose();
		}
	}

}