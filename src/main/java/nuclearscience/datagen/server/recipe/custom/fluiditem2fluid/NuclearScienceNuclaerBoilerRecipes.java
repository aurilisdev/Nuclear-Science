package nuclearscience.datagen.server.recipe.custom.fluiditem2fluid;

import java.util.function.Consumer;

import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceFluids;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeFluidOutput;

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
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new FluidStack(NuclearScienceFluids.FLUID_URANIUMHEXAFLUORIDE.get(), 2000), 0, CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_uraniumpellets")
				//
				.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 1600)
				//
				.addItemTagInput(NuclearScienceTags.Items.PELLET_URANIUM238, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(NuclearScienceFluids.FLUID_URANIUMHEXAFLUORIDE.get(), 2500), 0.25F, CHEMICALBOILER_REQUIRED_TICKS, CHEMICALBOILER_USAGE_PER_TICK, "uraniumhexafluoride_from_yellowcake")
				//
				.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 800)
				//
				.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeFluidOutput newRecipe(FluidStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeFluidOutput.of(NuclearScienceRecipies.NUCLEAR_BOILER_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_FLUID, modID, "nuclear_boiler/" + name);
	}

}
