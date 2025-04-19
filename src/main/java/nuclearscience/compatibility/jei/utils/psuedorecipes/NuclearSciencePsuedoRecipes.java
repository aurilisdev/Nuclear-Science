package nuclearscience.compatibility.jei.utils.psuedorecipes;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoItem2ItemRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.common.recipe.recipeutils.GasIngredient;

public class NuclearSciencePsuedoRecipes {

	private static final ArrayList<ArrayList<ItemStack>> NUCLEAR_SCIENCE_ITEMS = new ArrayList<>();

	public static final ArrayList<PsuedoGasCentrifugeRecipe> GAS_CENTRIFUGE_RECIPES = new ArrayList<>();
	public static final ArrayList<PsuedoItem2ItemRecipe> ANTI_MATTER_RECIPES = new ArrayList<>();
	public static final ArrayList<PsuedoItem2ItemRecipe> DARK_MATTER_RECIPES = new ArrayList<>();

	public static void addNuclearScienceRecipes() {

		NUCLEAR_SCIENCE_ITEMS.clear();
		GAS_CENTRIFUGE_RECIPES.clear();
		ANTI_MATTER_RECIPES.clear();
		DARK_MATTER_RECIPES.clear();

		addNuclearScienceItems();

		GAS_CENTRIFUGE_RECIPES.add(new PsuedoGasCentrifugeRecipe(new GasIngredient(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE, 5000, 273, 1), NUCLEAR_SCIENCE_ITEMS.get(0).get(2), NUCLEAR_SCIENCE_ITEMS.get(0).get(1), new ItemStack(NuclearScienceItems.ITEM_FISSILEDUST.get())));

		ANTI_MATTER_RECIPES.add(new PsuedoItem2ItemRecipe(Arrays.asList(NUCLEAR_SCIENCE_ITEMS.get(1).get(4)), NUCLEAR_SCIENCE_ITEMS.get(1).get(5)));

		DARK_MATTER_RECIPES.add(new PsuedoItem2ItemRecipe(Arrays.asList(NUCLEAR_SCIENCE_ITEMS.get(1).get(4)), NUCLEAR_SCIENCE_ITEMS.get(1).get(7)));
	}

	private static void addNuclearScienceItems() {

		// Uranium and Derivatives : 0
		Item[] uraniumMisc = { NuclearScienceItems.ITEM_YELLOWCAKE.get(), NuclearScienceItems.ITEM_URANIUM238.get(), NuclearScienceItems.ITEM_URANIUM235.get() };

		NUCLEAR_SCIENCE_ITEMS.add(formItemStacks(uraniumMisc, 1));

		// Cells : 1
		Item[] cells = { NuclearScienceItems.ITEM_CELLEMPTY.get(), NuclearScienceItems.ITEM_CELLHEAVYWATER.get(), NuclearScienceItems.ITEM_CELLDEUTERIUM.get(), NuclearScienceItems.ITEM_CELLTRITIUM.get(), NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get(), NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get(), NuclearScienceItems.ITEM_CELLANTIMATTERLARGE.get(), NuclearScienceItems.ITEM_CELLDARKMATTER.get() };

		NUCLEAR_SCIENCE_ITEMS.add(formItemStacks(cells, 1));

	}

	private static ArrayList<ItemStack> formItemStacks(Item[] items, int countPerItemStack) {
		ArrayList<ItemStack> inputItems = new ArrayList<>();

		for (int i = 0; i < items.length; i++) {
			inputItems.add(new ItemStack(items[i]));
			inputItems.get(i).setCount(countPerItemStack);
		}
		return inputItems;
	}
}
