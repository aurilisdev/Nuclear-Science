package nuclearscience.datagen.server.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import nuclearscience.datagen.server.recipe.custom.fluiditem2fluid.NuclearScienceChemicalMixerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2fluid.NuclearScienceNuclaerBoilerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceChemicalExtractorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceMSRFuelPreprocessorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceRadioactiveProcessorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFissionReactorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFuelReprocessorRecipes;
import nuclearscience.datagen.server.recipe.vanilla.NuclearScienceCraftingTableRecipes;

public class NuclearScienceRecipeProvider extends RecipeProvider {

	public final List<AbstractRecipeGenerator> GENERATORS = new ArrayList<>();
	
	
	public NuclearScienceRecipeProvider(DataGenerator gen) {
		super(gen);
		addRecipes();
	}
	
	public void addRecipes() {
		GENERATORS.add(new NuclearScienceCraftingTableRecipes());
		GENERATORS.add(new NuclearScienceChemicalMixerRecipes());
		GENERATORS.add(new NuclearScienceNuclaerBoilerRecipes());
		GENERATORS.add(new NuclearScienceChemicalExtractorRecipes());
		GENERATORS.add(new NuclearScienceMSRFuelPreprocessorRecipes());
		GENERATORS.add(new NuclearScienceRadioactiveProcessorRecipes());
		GENERATORS.add(new NuclearScienceFissionReactorRecipes());
		GENERATORS.add(new NuclearScienceFuelReprocessorRecipes());
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		for(AbstractRecipeGenerator generator : GENERATORS) {
			generator.addRecipes(consumer);
		}
	}



}
