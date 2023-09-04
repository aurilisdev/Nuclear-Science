package nuclearscience.compatibility.jei.recipecategories.item2item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.compatibility.jei.recipecategories.item2item.Item2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.types.ArrowAnimatedObject;
import electrodynamics.compatibility.jei.utils.gui.types.BackgroundObject;
import electrodynamics.compatibility.jei.utils.gui.types.ItemSlotObject;
import electrodynamics.compatibility.jei.utils.label.types.LabelWrapperGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FissionReactorRecipe;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class FissionReactorRecipeCategory extends Item2ItemRecipeCategory<FissionReactorRecipe> {

	// JEI Window Parameters
	public static final BackgroundObject BACK_WRAP = new BackgroundObject(132, 132);

	public static final ItemSlotObject INPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 57, 25, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject CELL_SLOT_1 = new ItemSlotObject(SlotType.NORMAL, 15, 15, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject CELL_SLOT_2 = new ItemSlotObject(SlotType.NORMAL, 15, 99, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject CELL_SLOT_3 = new ItemSlotObject(SlotType.NORMAL, 99, 15, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject CELL_SLOT_4 = new ItemSlotObject(SlotType.NORMAL, 99, 99, RecipeIngredientRole.INPUT);
	public static final ItemSlotObject OUTPUT_SLOT = new ItemSlotObject(SlotType.NORMAL, 57, 88, RecipeIngredientRole.OUTPUT);

	public static final ArrowAnimatedObject ARROW = new ArrowAnimatedObject(ProgressTextures.FEYNMAN_DIAGRAM_OFF, ProgressTextures.FEYNMAN_DIAGRAM_ON, 35, 40, StartDirection.TOP);

	public static final LabelWrapperGeneric POWER_LABEL = new LabelWrapperGeneric(0xFF808080, 122, 2, false, NuclearTextUtils.jeiTranslated("gui.reactorcore.info.temp"));

	public static final int ANIM_TIME = 50;

	public static ItemStack INPUT_MACHINE = new ItemStack(NuclearScienceBlocks.blockFissionReactorCore);

	public static final RecipeType<FissionReactorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, FissionReactorRecipe.RECIPE_GROUP, FissionReactorRecipe.class);

	public FissionReactorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, NuclearTextUtils.jeiTranslated(FissionReactorRecipe.RECIPE_GROUP), INPUT_MACHINE, BACK_WRAP, RECIPE_TYPE, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, CELL_SLOT_1, CELL_SLOT_2, CELL_SLOT_3, CELL_SLOT_4);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setAnimatedArrows(guiHelper, ARROW);
		setLabels(POWER_LABEL);
	}

	@Override
	public List<List<ItemStack>> getItemInputs(Item2ItemRecipe recipe) {
		ItemStack u235Cell = new ItemStack(NuclearScienceItems.ITEM_FUELHEUO2.get(), 1);
		ItemStack u238Cell = new ItemStack(NuclearScienceItems.ITEM_FUELLEUO2.get(), 1);
		ItemStack plutoniumCell = new ItemStack(NuclearScienceItems.ITEM_FUELPLUTONIUM.get(), 1);

		List<ItemStack> fuels = new ArrayList<>();
		fuels.add(u238Cell);
		fuels.add(u235Cell);
		fuels.add(plutoniumCell);

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
