package nuclearscience.common.recipe.categories.fluiditem2item;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;

public class FluidItem2ItemRecipeTypes {

    public static final IRecipeSerializer<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<>(
	    ChemicalExtractorRecipe.class);
}
