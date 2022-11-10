package nuclearscience.compatibility.jei.recipecategories.item2item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import electrodynamics.api.References;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.compatibility.jei.recipecategories.item2item.Item2ItemRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.arrows.animated.FeynmanDiagramAnimatedWrapper;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import electrodynamics.compatibility.jei.utils.gui.item.DefaultItemSlotWrapper;
import electrodynamics.compatibility.jei.utils.label.GenericLabelWrapper;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FissionReactorRecipe;

public class FissionReactorRecipeCategory extends Item2ItemRecipeCategory<FissionReactorRecipe> {

	// JEI Window Parameters
	private static BackgroundWrapper BACK_WRAP = new BackgroundWrapper(132, 132);

	private static DefaultItemSlotWrapper INPUT_SLOT = new DefaultItemSlotWrapper(57, 25);
	private static DefaultItemSlotWrapper CELL_SLOT_1 = new DefaultItemSlotWrapper(15, 15);
	private static DefaultItemSlotWrapper CELL_SLOT_2 = new DefaultItemSlotWrapper(15, 99);
	private static DefaultItemSlotWrapper CELL_SLOT_3 = new DefaultItemSlotWrapper(99, 15);
	private static DefaultItemSlotWrapper CELL_SLOT_4 = new DefaultItemSlotWrapper(99, 99);
	private static DefaultItemSlotWrapper OUTPUT_SLOT = new DefaultItemSlotWrapper(57, 88);

	private static FeynmanDiagramAnimatedWrapper ARROW = new FeynmanDiagramAnimatedWrapper(35, 40);

	private static GenericLabelWrapper POWER_LABEL = new GenericLabelWrapper(0xFF808080, 122, 2, "reactorcore.info.temp");

	private static int ANIM_TIME = 50;

	private static String MOD_ID = References.ID;
	private static String RECIPE_GROUP = "reactorcore";

	public static ItemStack INPUT_MACHINE = new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore);

	public static ResourceLocation UID = new ResourceLocation(MOD_ID, RECIPE_GROUP);
	public static final RecipeType<FissionReactorRecipe> RECIPE_TYPE = RecipeType.create(References.ID, FissionReactorRecipe.RECIPE_GROUP, FissionReactorRecipe.class);

	public FissionReactorRecipeCategory(IGuiHelper guiHelper) {
		super(guiHelper, MOD_ID, RECIPE_GROUP, INPUT_MACHINE, BACK_WRAP, FissionReactorRecipe.class, ANIM_TIME);
		setInputSlots(guiHelper, INPUT_SLOT, CELL_SLOT_1, CELL_SLOT_2, CELL_SLOT_3, CELL_SLOT_4);
		setOutputSlots(guiHelper, OUTPUT_SLOT);
		setAnimatedArrows(guiHelper, ARROW);
		setLabels(POWER_LABEL);
	}

	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public RecipeType<FissionReactorRecipe> getRecipeType() {
		return RECIPE_TYPE;
	}

	@Override
	public List<List<ItemStack>> getItemInputs(ElectrodynamicsRecipe electro) {
		Item2ItemRecipe recipe = (Item2ItemRecipe) electro;
		ItemStack u235Cell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELHEUO2.get(), 1);
		ItemStack u238Cell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELLEUO2.get(), 1);
		ItemStack plutoniumCell = new ItemStack(nuclearscience.DeferredRegisters.ITEM_FUELPLUTONIUM.get(), 1);

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
