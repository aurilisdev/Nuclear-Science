package nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.compatability.jei.recipecategories.O2ORecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nuclearscience.References;

public class FissionReactorRecipeCategory extends O2ORecipeCategory {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
	
	// JEI Window Parameters
    private static int[] GUI_BACKGROUND = { 0, 0, 132, 132 };
    private static int[] PROCESSING_ARROW_LOCATION = { 132, 0, 48, 42 };

    private static int[] INPUT_ITEM_OFFSET = { 57, 25 };
    private static int[] OUTPUT_ITEM_OFFSET = { 57, 89 };
    private static int[] PROCESSING_ARROW_OFFSET = { 42, 45 };
    
    private static int[] FUEL_ROD_SLOTS = { 2, 3, 4, 5 };
    
    private static int SMELT_TIME = 50;
    private static int TEXT_Y_HEIGHT = 122;

    private static String MOD_ID = References.ID;
    private static String RECIPE_GROUP = "fission_reactor";
    private static String GUI_TEXTURE = "textures/gui/jei/fission_reactor_gui.png";
   
    private static ItemStack INPUT_MACHINE = new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore);
    
    private static StartDirection ARROW_START_DIRECTION = IDrawableAnimated.StartDirection.TOP;
    
    public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);
    
    private static ArrayList<int[]> INPUT_COORDINATES = new ArrayList<>(
	    Arrays.asList(GUI_BACKGROUND, PROCESSING_ARROW_LOCATION, INPUT_ITEM_OFFSET, OUTPUT_ITEM_OFFSET, PROCESSING_ARROW_OFFSET));
    
    public FissionReactorRecipeCategory(IGuiHelper guiHelper) {
	super(guiHelper, MOD_ID, RECIPE_GROUP, GUI_TEXTURE, INPUT_MACHINE, INPUT_COORDINATES, SMELT_TIME, 
			TEXT_Y_HEIGHT, ARROW_START_DIRECTION);
    }
    
    @Override
    public ResourceLocation getUid() {
	return UID;
    }

    @Override
    public void setIngredients(O2ORecipe recipe, IIngredients ingredients) {

	ingredients.setInputLists(VanillaTypes.ITEM, recipeInput(recipe));
	ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, O2ORecipe recipe, IIngredients ingredients) {

	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

	guiItemStacks.init(INPUT_SLOT, true, INPUT_ITEM_OFFSET[0], INPUT_ITEM_OFFSET[1]);
	guiItemStacks.init(FUEL_ROD_SLOTS[0], true, 15, 15);
	guiItemStacks.init(FUEL_ROD_SLOTS[1], true, 99, 15);
	guiItemStacks.init(FUEL_ROD_SLOTS[2], true, 15, 99);
	guiItemStacks.init(FUEL_ROD_SLOTS[3], true, 99, 99);
	guiItemStacks.init(OUTPUT_SLOT, false, OUTPUT_ITEM_OFFSET[0], OUTPUT_ITEM_OFFSET[1]);

	guiItemStacks.set(ingredients);
    }

    private static List<List<ItemStack>> recipeInput(O2ORecipe recipe) {
	ItemStack u235Cell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELHEUO2.get(), 1);
	ItemStack u238Cell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELLEUO2.get(), 1);

	List<ItemStack> fuels = new ArrayList<>();
	fuels.add(u238Cell);
	fuels.add(u235Cell);

	List<List<ItemStack>> inputSlots = new ArrayList<>();
	inputSlots.add(((CountableIngredient) recipe.getIngredients().get(0)).fetchCountedStacks());

	inputSlots.add(fuels);

	Collections.reverse(fuels);
	inputSlots.add(fuels);

	Collections.reverse(fuels);
	inputSlots.add(fuels);

	Collections.reverse(fuels);
	inputSlots.add(fuels);

	return inputSlots;
    }

}
