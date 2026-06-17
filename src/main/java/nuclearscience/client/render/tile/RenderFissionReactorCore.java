package nuclearscience.client.render.tile;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import voltaic.client.render.AbstractTileRenderer;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class RenderFissionReactorCore extends AbstractTileRenderer<TileFissionReactorCore> {

    public static final AABB FUEL_ROD_1 = new AABB(3.0 / 16.0, 1.8 / 16.0, 3.0 / 16.0, 4.0 / 16.0, 12.0 / 16.0,
	    4.0 / 16.0);
    public static final AABB FUEL_ROD_2 = new AABB(12.0 / 16.0, 1.8 / 16.0, 3.0 / 16.0, 13.0 / 16.0, 12.0 / 16.0,
	    4.0 / 16.0);
    public static final AABB FUEL_ROD_3 = new AABB(12.0 / 16.0, 1.8 / 16.0, 12.0 / 16.0, 13.0 / 16.0, 12.0 / 16.0,
	    13.0 / 16.0);
    public static final AABB FUEL_ROD_4 = new AABB(3.0 / 16.0, 1.8 / 16.0, 12.0 / 16.0, 4.0 / 16.0, 12.0 / 16.0,
	    13.0 / 16.0);

    public static final AABB TRITIUM_CELL = new AABB(7.0 / 16.0, 1.8 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 12.0 / 16.0,
	    9.0 / 16.0);

    public static final Color HIGH_ENRICH = new Color(75, 181, 0, 255);
    public static final Color LOW_ENRICH = new Color(40, 98, 0, 255);
    public static final Color SPENT = new Color(116, 147, 95, 255);
    public static final Color PLUTONIUM = new Color(227, 125, 11, 255);

    public static final Color DEUTERIUM = new Color(0, 144, 255, 255);
    public static final Color TRITIUM = new Color(255, 255, 0, 255);

    public static final boolean[] FACES = { false, false, true, true, true, true }; // DUNSWE

    public RenderFissionReactorCore(BlockEntityRendererProvider.Context context) {
	super(context);
    }

    @Override
    public void render(TileFissionReactorCore tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {

	matrix.pushPose();

	ComponentInventory inv = tile.getComponent(IComponentType.Inventory);

	if (!inv.areInputsEmpty()) {

	    List<ItemStack> inputs = inv.getInputContents();

	    ItemStack stack;

	    for (int i = 0; i < 4; i++) {

		stack = inputs.get(i);

		if (!stack.isEmpty()) {
		    renderFuelCell(i, stack, matrix, bufferIn, combinedLightIn, combinedOverlayIn);
		}

	    }

	    if (!inputs.get(TileFissionReactorCore.DUETERIUM_SLOT).isEmpty()) {
		renderDeuteriumCell(DEUTERIUM, matrix, bufferIn, combinedLightIn, combinedOverlayIn);
	    } else if (!inv.getOutputContents().get(0).isEmpty()) {
		renderDeuteriumCell(TRITIUM, matrix, bufferIn, combinedLightIn, combinedOverlayIn);
	    }

	}

	matrix.popPose();
    }

    private static void renderDeuteriumCell(Color color, PoseStack matrix, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {

	matrix.pushPose();

	TextureAtlasSprite texture = NuclearScienceClientRegister
		.getSprite(NuclearScienceClientRegister.TEXTURE_FUELCELL);

	RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.solid()), TRITIUM_CELL,
		color.rFloat(), color.gFloat(), color.bFloat(), color.aFloat(), texture.getU0(), texture.getV0(),
		texture.getU1(), texture.getV1(), combinedLightIn, FACES);

	matrix.popPose();

    }

    private static void renderFuelCell(int cellSlot, ItemStack stack, PoseStack matrix, MultiBufferSource bufferIn,
	    int combinedLightIn, int combinedOverlayIn) {

	TextureAtlasSprite texture = NuclearScienceClientRegister
		.getSprite(NuclearScienceClientRegister.TEXTURE_FUELCELL);

	matrix.pushPose();

	Color color = getColorFromFuel(stack);

	AABB box = new AABB(0, 0, 0, 1, 1, 1);

	switch (cellSlot) {
	case 0:
	    box = FUEL_ROD_1;
	    break;
	case 1:
	    box = FUEL_ROD_2;
	    break;
	case 2:
	    box = FUEL_ROD_3;
	    break;
	case 3:
	    box = FUEL_ROD_4;
	    break;
	default:
	    break;
	}

	RenderingUtils.renderFilledBoxNoOverlay(matrix, bufferIn.getBuffer(RenderType.solid()), box, color.rFloat(),
		color.gFloat(), color.bFloat(), color.aFloat(), texture.getU0(), texture.getV0(), texture.getU1(),
		texture.getV1(), combinedLightIn, FACES);

	matrix.popPose();

    }

    public static Color getColorFromFuel(ItemStack stack) {

	if (stack.is(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN)) {
	    return LOW_ENRICH;
	} else if (stack.is(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN)) {
	    return HIGH_ENRICH;
	} else if (stack.is(NuclearScienceTags.Items.FUELROD_PLUTONIUM)) {
	    return PLUTONIUM;
	} else if (stack.is(NuclearScienceTags.Items.FUELROD_SPENT)) {
	    return SPENT;
	} else {
	    return Color.WHITE;
	}

    }

}
