package nuclearscience.compatability.jei.recipecategories.psuedorecipes;

import java.util.ArrayList;

import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoO2ORecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoRecipes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class NuclearSciencePsuedoRecipes {

    private static ArrayList<ArrayList<ItemStack>> NUCLEAR_SCIENCE_ITEMS = new ArrayList<>();
    private static ArrayList<Fluid> NUCLEAR_SCIENCE_FLUIDS = new ArrayList<>();
    public static ArrayList<ItemStack> NUCLEAR_SCIENCE_MACHINES = new ArrayList<>();

    public static ArrayList<PsuedoGasCentrifugeRecipe> GAS_CENTRIFUGE_RECIPES = new ArrayList<>();
    public static ArrayList<PsuedoO2ORecipe> ANTI_MATTER_RECIPES = new ArrayList<>();
    public static ArrayList<PsuedoO2ORecipe> DARK_MATTER_RECIPES = new ArrayList<>();

    public static void addNuclearScienceRecipes() {
	PsuedoRecipes.addElectrodynamicsMachines();
	PsuedoRecipes.addElectrodynamicsFluids();
	PsuedoRecipes.addElectrodynamicsItems();

	addNuclearScienceMachines();
	addNuclearScienceFluids();
	addNuclearScienceItems();

	/* Gas Centrifuge */

	GAS_CENTRIFUGE_RECIPES.add(new PsuedoGasCentrifugeRecipe(new FluidStack(NUCLEAR_SCIENCE_FLUIDS.get(0), 1000),
		NUCLEAR_SCIENCE_ITEMS.get(0).get(2), NUCLEAR_SCIENCE_ITEMS.get(0).get(1)));

	/* Anit Matter Production */

	ANTI_MATTER_RECIPES.add(new PsuedoO2ORecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(4), NUCLEAR_SCIENCE_ITEMS.get(1).get(5)));

	DARK_MATTER_RECIPES.add(new PsuedoO2ORecipe(NUCLEAR_SCIENCE_ITEMS.get(1).get(4), NUCLEAR_SCIENCE_ITEMS.get(1).get(7)));
    }

    private static void addNuclearScienceMachines() {

	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockFusionReactorCore));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockNuclearBoiler));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockChemicalExtractor));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockElectromagnet));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockElectromagneticBooster));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockElectromagneticSwitch));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockGasCentrifuge));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockQuantumCapacitor));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockRadioisotopeGenerator));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockTurbine));
	NUCLEAR_SCIENCE_MACHINES.add(new ItemStack(nuclearscience.DeferredRegisters.blockTeleporter));

    }

    private static void addNuclearScienceFluids() {
	NUCLEAR_SCIENCE_FLUIDS.add(nuclearscience.DeferredRegisters.fluidUraniumHexafluoride);
    }

    private static void addNuclearScienceItems() {

	// Uranium and Derivatives : 0
	Item[] uraniumMisc = { nuclearscience.DeferredRegisters.ITEM_YELLOWCAKE.get(), nuclearscience.DeferredRegisters.ITEM_URANIUM238.get(),
		nuclearscience.DeferredRegisters.ITEM_URANIUM235.get() };

	NUCLEAR_SCIENCE_ITEMS.add(formItemStacks(uraniumMisc, 1));

	// Cells : 1
	Item[] cells = { nuclearscience.DeferredRegisters.ITEM_CELLEMPTY.get(), nuclearscience.DeferredRegisters.ITEM_CELLHEAVYWATER.get(),
		nuclearscience.DeferredRegisters.ITEM_CELLDEUTERIUM.get(), nuclearscience.DeferredRegisters.ITEM_CELLTRITIUM.get(),
		nuclearscience.DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get(), nuclearscience.DeferredRegisters.ITEM_CELLANTIMATTERSMALL.get(),
		nuclearscience.DeferredRegisters.ITEM_CELLANTIMATTERLARGE.get(), nuclearscience.DeferredRegisters.ITEM_CELLDARKMATTER.get() };

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
