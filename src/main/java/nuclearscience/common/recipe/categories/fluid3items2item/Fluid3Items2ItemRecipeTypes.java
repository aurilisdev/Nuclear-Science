package nuclearscience.common.recipe.categories.fluid3items2item;

import electrodynamics.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nuclearscience.common.recipe.categories.fluid3items2item.specificmachines.MSRFuelPreProcessorRecipe;

public class Fluid3Items2ItemRecipeTypes {

    public static final RecipeSerializer<MSRFuelPreProcessorRecipe> MSR_FUEL_PREPROCESSOR_JSON_SERIALIZER = new Fluid3Items2ItemRecipeSerializer<>(
	    MSRFuelPreProcessorRecipe.class);

}
