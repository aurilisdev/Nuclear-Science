package nuclearscience.common.recipe.categories.o2o;

import electrodynamics.common.recipe.categories.o2o.O2ORecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FuelReprocessorRecipe;

public class O2ORecipeTypes {

    public static final RecipeSerializer<FissionReactorRecipe> FISSION_REACTOR_JSON_SERIALIZER = new O2ORecipeSerializer<>(
	    FissionReactorRecipe.class);
    public static final RecipeSerializer<FuelReprocessorRecipe> FUEL_REPROCESSOR_JSON_SERIALIZER = new O2ORecipeSerializer<>(
	    FuelReprocessorRecipe.class);
}
