package nuclearscience.compatibility.jei.utils.psuedorecipes;

import java.util.ArrayList;
import java.util.Arrays;

import electrodynamics.compatibility.jei.recipecategories.psuedo.PsuedoItem2ItemRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.DeferredRegisters;

public class NuclearSciencePsuedoRecipes {

	private static ArrayList<ArrayList<ItemStack>> NUCLEAR_SCIENCE_ITEMS = new ArrayList<>();
	private static ArrayList<Fluid> NUCLEAR_SCIENCE_FLUIDS = new ArrayList<>();

	public static ArrayList<PsuedoGasCentrifugeRecipe> GAS_CENTRIFUGE_RECIPES = new ArrayList<>();
	public static ArrayList<PsuedoItem2ItemRecipe> ANTI_MATTER_RECIPES = new ArrayList<>();
	public static ArrayList<PsuedoItem2ItemRecipe> DARK_MATTER_RECIPES = new ArrayList<>();

	public static void addNuclearScienceRecipes() {

		addNuclearScienceFluids();
		addNuclearScienceItems();

		GAS_CENTRIFUGE_RECIPES.add(new PsuedoGasCentrifugeRecipe(new FluidStack(NUCLEAR_SCIENCE_FLUIDS.get(0), 5000), NUCLEAR_SCIENCE_ITEMS.get(0).get(2), NUCLEAR_SCIENCE_ITEMS.get(0).get(1), new ItemStack(DeferredRegisters.ITEM_FISSILEDUST.get())));

		ANTI_MATTER_RECIPES.add(new PsuedoItem2ItemRecipe(Arrays.asList(new ItemStack[] { NUCLEAR_SCIENCE_ITEMS.get(1).get(4) }), NUCLEAR_SCIENCE_ITEMS.get(1).get(5)));

		DARK_MATTER_RECIPES.add(new PsuedoItem2ItemRecipe(Arrays.asList(new ItemStack[] { NUCLEAR_SCIENCE_ITEMS.get(1).get(4) }), NUCLEAR_SCIENCE_ITEMS.get(1).get(7)));
	}

	private static void addNuclearScienceFluids() {
		NUCLEAR_SCIENCE_FLUIDS.add(nuclearscience.DeferredRegisters.fluidUraniumHexafluoride);
	}

	private static void addNuclearScienceItems() {

		// Uranium and Derivatives : 0
		Item[] uraniumMisc = { nuclearscience.DeferredRegisters.ITEM_YELLOWCAKE.get(), nuclearscience.DeferredRegisters.ITEM_URANIUM238.get(), nuclearscience.DeferredRegisters.ITEM_URANIUM235.get() };

		NUCLEAR_SCIENCE_ITEMS.add(formItemStacks(uraniumMisc, 1));

		// Cells : 1
		Item[] cells = { nuclearscience.DeferredRegisters.ITEM_CELLEMPTY.get(), nuclearscience.DeferredRegisters.ITEM_CELLHEAVYWATER.get(), nuclearscience.DeferredRegisters.ITEM_CELLDEUTERIUM.get(), nuclearscience.DeferredRegisters.ITEM_CELLTRITIUM.get(), nuclearscience.DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get(), nuclearscience.DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get(), nuclearscience.DeferredRegisters.ITEM_CELLANTIMATTERLARGE.get(), nuclearscience.DeferredRegisters.ITEM_CELLDARKMATTER.get() };

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
