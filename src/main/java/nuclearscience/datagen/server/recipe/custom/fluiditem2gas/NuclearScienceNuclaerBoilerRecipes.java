package nuclearscience.datagen.server.recipe.custom.fluiditem2gas;

import java.util.function.Consumer;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeGasOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceGases;

public class NuclearScienceNuclaerBoilerRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALBOILER_USAGE_PER_TICK = 750.0;
	public static int CHEMICALBOILER_REQUIRED_TICKS = 800;

	private final String modID;

	public NuclearScienceNuclaerBoilerRecipes() {
		this(References.ID);
	}

	public NuclearScienceNuclaerBoilerRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new GasStack(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get(), 2000, 293, 1), 0, CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_uraniumpellets")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROGEN_FLUORIDE, 1600)
				//
				.addItemTagInput(NuclearScienceTags.Items.PELLET_URANIUM238, 1)
				//
				.complete(consumer);

		newRecipe(new GasStack(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get(), 2500, 293, 1), 0.25F, CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_yellowcake")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROGEN_FLUORIDE, 800)
				//
				.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeGasOutput newRecipe(GasStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeGasOutput.of(NuclearScienceRecipeInit.NUCLEAR_BOILER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_GAS, modID, "nuclear_boiler/" + name);
	}

}
