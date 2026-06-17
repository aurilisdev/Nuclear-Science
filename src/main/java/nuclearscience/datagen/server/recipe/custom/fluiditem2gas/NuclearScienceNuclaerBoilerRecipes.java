package nuclearscience.datagen.server.recipe.custom.fluiditem2gas;

import net.minecraft.data.recipes.RecipeOutput;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.fluiditem2gas.NuclearBoilerRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceGases;
import voltaic.api.gas.GasStack;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.FluidItem2GasBuilder;

public class NuclearScienceNuclaerBoilerRecipes extends AbstractRecipeGenerator {

    public static double CHEMICALBOILER_USAGE_PER_TICK = 750.0;
    public static int CHEMICALBOILER_REQUIRED_TICKS = 800;

    private final String modID;

    public NuclearScienceNuclaerBoilerRecipes() {
	this(NuclearScience.ID);
    }

    public NuclearScienceNuclaerBoilerRecipes(String modID) {
	this.modID = modID;
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	newRecipe(new GasStack(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get(), 2000, 350, 1), 0,
		CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_uraniumpellets",
		this.modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 1600)
		//
		.addItemTagInput(NuclearScienceTags.Items.PELLET_URANIUM238, 1)
		//
		.save(output);

	newRecipe(new GasStack(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get(), 2500, 350, 1), 0.25F,
		CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_yellowcake",
		this.modID)
		//
		.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 800)
		//
		.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 1)
		//
		.save(output);

    }

    public FluidItem2GasBuilder<NuclearBoilerRecipe> newRecipe(GasStack stack, float xp, int ticks, double usagePerTick,
	    String name, String group) {
	return new FluidItem2GasBuilder<>(NuclearBoilerRecipe::new, stack,
		BaseRecipeBuilder.RecipeCategory.FLUID_ITEM_2_GAS, modID, "nuclear_boiler/" + name, group, xp, ticks,
		usagePerTick);
    }

}
