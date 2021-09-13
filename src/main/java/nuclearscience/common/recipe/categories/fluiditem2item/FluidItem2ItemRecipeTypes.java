package nuclearscience.common.recipe.categories.fluiditem2item;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.RadioactiveProcessorRecipe;

public class FluidItem2ItemRecipeTypes {

    public static final IRecipeSerializer<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<>(
	    ChemicalExtractorRecipe.class);
    public static final IRecipeSerializer<RadioactiveProcessorRecipe> RADIOACTIVE_PROCESSOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<>(
	    RadioactiveProcessorRecipe.class);
}
