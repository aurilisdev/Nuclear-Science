package nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.api.References;
import electrodynamics.compatability.jei.recipecategories.FluidItem2ItemRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nuclearscience.DeferredRegisters;

public class ChemicalExtractorRecipeCategory extends FluidItem2ItemRecipeCategory {

    // JEI Window Parameters
    private static int[] GUI_BACKGROUND = { 0, 0, 132, 64 };
    private static int[] MAJOR_PROCESSING_ARROW_LOCATION = { 0, 65, 68, 15 };
    private static int[] MINOR_PROCESSING_ARROW_LOCATION = { 0, 80, 20, 15 };

    private static int[] INPUT_ITEM_OFFSET = { 58, 16 };
    private static int[] INPUT_FLUID_BUCKET_OFFSET = { 58, 36 };
    private static int[] INPUT_FLUID_TANK = { 11, 52, 12, 47, 5000 };
    private static int[] OUTPUT_FLUID_TANK = { 104, 16 };

    private static int[] MAJOR_PROCESSING_ARROW_OFFSET = { 32, 17 };
    private static int[] MINOR_PROCESSING_ARROW_OFFSET = { 32, 37 };

    private static int SMELT_TIME = 50;
    private static int TEXT_Y_HEIGHT = 48;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "chemical_extractor";
    private static String GUI_TEXTURE = "textures/gui/jei/sol_and_liq_to_sol_recipe_gui.png";

    private static ItemStack INPUT_MACHINE = new ItemStack(DeferredRegisters.blockChemicalExtractor);

    private static StartDirection MAJOR_ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.LEFT;
    private static StartDirection MINOR_ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.RIGHT;

    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    private static ArrayList<int[]> INPUT_COORDINATES = new ArrayList<>(
	    Arrays.asList(GUI_BACKGROUND, MAJOR_PROCESSING_ARROW_LOCATION, MINOR_PROCESSING_ARROW_LOCATION, INPUT_ITEM_OFFSET,
		    INPUT_FLUID_BUCKET_OFFSET, INPUT_FLUID_TANK, OUTPUT_FLUID_TANK, MAJOR_PROCESSING_ARROW_OFFSET, MINOR_PROCESSING_ARROW_OFFSET));

    public ChemicalExtractorRecipeCategory(IGuiHelper guiHelper) {
	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, INPUT_COORDINATES, SMELT_TIME, MAJOR_ARROW_START_DIRECTION,
		MINOR_ARROW_START_DIRECTION, TEXT_Y_HEIGHT);
    }

    @Override
    public ResourceLocation getUid() {
	return UID;
    }

}
