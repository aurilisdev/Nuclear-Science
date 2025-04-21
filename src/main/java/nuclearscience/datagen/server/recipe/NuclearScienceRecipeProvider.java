package nuclearscience.datagen.server.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import nuclearscience.datagen.server.recipe.custom.NuclearScienceChemicalReactorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluid2item.NuclearScienceChemicalCrystalizerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2fluid.NuclearScienceChemicalMixerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2gas.NuclearScienceNuclaerBoilerRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceChemicalExtractorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceMSRFuelPreprocessorRecipes;
import nuclearscience.datagen.server.recipe.custom.fluiditem2item.NuclearScienceRadioactiveProcessorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFissionReactorRecipes;
import nuclearscience.datagen.server.recipe.custom.item2item.NuclearScienceFuelReprocessorRecipes;
import nuclearscience.datagen.server.recipe.vanilla.NuclearScienceCraftingTableRecipes;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;

public class NuclearScienceRecipeProvider extends RecipeProvider {

	public final List<AbstractRecipeGenerator> generators = new ArrayList<>();

	public NuclearScienceRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
		addRecipes();
	}

	public void addRecipes() {
		generators.add(new NuclearScienceCraftingTableRecipes());
		generators.add(new NuclearScienceChemicalMixerRecipes());
		generators.add(new NuclearScienceNuclaerBoilerRecipes());
		generators.add(new NuclearScienceChemicalExtractorRecipes());
		generators.add(new NuclearScienceMSRFuelPreprocessorRecipes());
		generators.add(new NuclearScienceRadioactiveProcessorRecipes());
		generators.add(new NuclearScienceFissionReactorRecipes());
		generators.add(new NuclearScienceFuelReprocessorRecipes());
		generators.add(new NuclearScienceChemicalCrystalizerRecipes());
		generators.add(new NuclearScienceChemicalReactorRecipes());
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		for (AbstractRecipeGenerator generator : generators) {
			generator.addRecipes(output);
		}
	}

}
