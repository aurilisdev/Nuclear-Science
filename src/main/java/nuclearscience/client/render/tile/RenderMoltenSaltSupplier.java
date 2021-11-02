package nuclearscience.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileMoltenSaltSupplier;

public class RenderMoltenSaltSupplier extends BlockEntityRenderer<TileMoltenSaltSupplier> {

    private static final TransferPack PACK = TransferPack.joulesVoltage(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK,
	    Constants.MOLTENSALTSUPPLIER_VOLTAGE);

    public RenderMoltenSaltSupplier(BlockEntityRenderDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileMoltenSaltSupplier tile, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn,
	    int combinedOverlayIn) {
	matrixStack.pushPose();
	matrixStack.translate(0.5, 0.5, 0.5);
	Direction dir = tile.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	switch (dir) {
	case WEST:
	    matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180, true));
	    break;
	case NORTH:
	    matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
	    break;
	case SOUTH:
	    matrixStack.mulPose(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), -90, true));
	    break;
	default:
	    break;
	}
	ComponentElectrodynamic electro = tile.getComponent(ComponentType.Electrodynamic);
	if (electro != null && electro.getJoulesStored() >= PACK.getJoules()) {
	    BakedModel on = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOLTENSALTSUPPLIER_ON);
	    UtilitiesRendering.renderModel(on, tile, RenderType.solid(), matrixStack, buffer, combinedLightIn, combinedOverlayIn);
	} else {
	    BakedModel off = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOLTENSALTSUPPLIER);
	    UtilitiesRendering.renderModel(off, tile, RenderType.solid(), matrixStack, buffer, combinedLightIn, combinedOverlayIn);
	}
	matrixStack.popPose();

    }

}
