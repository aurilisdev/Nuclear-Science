package nuclearscience.client.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileMoltenSaltSupplier;

public class RenderMoltenSaltSupplier extends TileEntityRenderer<TileMoltenSaltSupplier> {

    private static final TransferPack PACK = TransferPack.joulesVoltage(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK,
	    Constants.MOLTENSALTSUPPLIER_VOLTAGE);

    public RenderMoltenSaltSupplier(TileEntityRendererDispatcher rendererDispatcherIn) {
	super(rendererDispatcherIn);
    }

    @Override
    public void render(TileMoltenSaltSupplier tile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn,
	    int combinedOverlayIn) {
	matrixStack.push();
	matrixStack.translate(0.5, 0.5, 0.5);
	Direction dir = tile.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
	switch (dir) {
	case WEST:
	    matrixStack.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 180, true));
	    break;
	case NORTH:
	    matrixStack.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), 90, true));
	    break;
	case SOUTH:
	    matrixStack.rotate(new Quaternion(new Vector3f(0.0F, 1.0F, 0.0F), -90, true));
	    break;
	default:
	    break;
	}
	ComponentElectrodynamic electro = tile.getComponent(ComponentType.Electrodynamic);
	if (electro != null && electro.getJoulesStored() >= PACK.getJoules()) {
	    IBakedModel on = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOLTENSALTSUPPLIER_ON);
	    UtilitiesRendering.renderModel(on, tile, RenderType.getSolid(), matrixStack, buffer, combinedLightIn, combinedOverlayIn);
	} else {
	    IBakedModel off = Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOLTENSALTSUPPLIER);
	    UtilitiesRendering.renderModel(off, tile, RenderType.getSolid(), matrixStack, buffer, combinedLightIn, combinedOverlayIn);
	}
	matrixStack.pop();

    }

}
