package nuclearscience.datagen.server.recipe;

import net.minecraft.data.DataGenerator;
import nuclearscience.datagen.server.recipe.custom.fluid2item.NuclearScienceChemicalCrystalizerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2fluid.NuclearScienceChemicalMixerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2fluid.NuclearScienceNuclaerBoilerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceChemicalExtractorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceMSRFuelPreprocessorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceRadioactiveProcessorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFissionReactorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFuelReprocessorRecipes;
import nuclearscience.datagen.server.recipe.vanilla.NuclearScienceCraftingTableRecipes;
import voltaic.datagen.utils.server.recipe.BaseRecipeProvider;

public class NuclearScienceRecipeProvider extends BaseRecipeProvider {

	public NuclearScienceRecipeProvider(DataGenerator gen) {
		super(gen);
	}

	public void addRecipes() {
		generators.add(new NuclearScienceCraftingTableRecipes());
		generators.add(new NuclearScienceChemicalMixerRecipes());
		generators.add(new NuclearScienceChemicalCrystalizerRecipes());
		generators.add(new NuclearScienceNuclaerBoilerRecipes());
		generators.add(new NuclearScienceChemicalExtractorRecipes());
		generators.add(new NuclearScienceMSRFuelPreprocessorRecipes());
		generators.add(new NuclearScienceRadioactiveProcessorRecipes());
		generators.add(new NuclearScienceFissionReactorRecipes());
		generators.add(new NuclearScienceFuelReprocessorRecipes());
	}

}
