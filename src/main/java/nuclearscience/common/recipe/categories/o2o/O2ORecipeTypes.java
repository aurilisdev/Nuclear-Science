package nuclearscience.common.recipe.categories.o2o;

import electrodynamics.common.recipe.categories.o2o.O2ORecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FuelReprocessorRecipe;

public class O2ORecipeTypes {

    public static final IRecipeSerializer<FissionReactorRecipe> FISSION_REACTOR_JSON_SERIALIZER = 
    	new O2ORecipeSerializer<>(FissionReactorRecipe.class);
    public static final IRecipeSerializer<FuelReprocessorRecipe> FUEL_REPROCESSOR_JSON_SERIALIZER = 
    	new O2ORecipeSerializer<>(FuelReprocessorRecipe.class);
}
