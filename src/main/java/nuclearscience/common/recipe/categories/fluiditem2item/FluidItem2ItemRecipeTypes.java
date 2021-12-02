package nuclearscience.common.recipe.categories.fluiditem2item;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.RadioactiveProcessorRecipe;

public class FluidItem2ItemRecipeTypes {

    public static final RecipeSerializer<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<>(
	    ChemicalExtractorRecipe.class);
    public static final RecipeSerializer<RadioactiveProcessorRecipe> RADIOACTIVE_PROCESSOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<>(
	    RadioactiveProcessorRecipe.class);
    public static final RecipeSerializer<MSRFuelPreProcessorRecipe> MSR_FUEL_PREPROCESSOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<>(
    	    MSRFuelPreProcessorRecipe.class);
}
