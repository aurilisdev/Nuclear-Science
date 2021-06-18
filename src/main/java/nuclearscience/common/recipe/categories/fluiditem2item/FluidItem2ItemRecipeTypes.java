package nuclearscience.common.recipe.categories.fluiditem2item;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class FluidItem2ItemRecipeTypes {

	public static final IRecipeSerializer<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_JSON_SERIALIZER = new FluidItem2ItemRecipeSerializer<ChemicalExtractorRecipe>(ChemicalExtractorRecipe.class);
}
