package nuclearscience.common.recipe.categories.o2o;

import electrodynamics.common.recipe.categories.o2o.O2ORecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FissionReactorRecipe;

public class O2ORecipeTypes {

    public static final IRecipeSerializer<FissionReactorRecipe> FISSION_REACTOR_JSON_SERIALIZER = new O2ORecipeSerializer<>(
	    FissionReactorRecipe.class);

}
