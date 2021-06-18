package nuclearscience.common.recipe.categories.o2o;

import electrodynamics.common.recipe.categories.o2o.O2ORecipeSerializer;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FissionReactorRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class O2ORecipeTypes {

	public static final IRecipeSerializer<FissionReactorRecipe> FISSION_REACTOR_JSON_SERIALIZER = new O2ORecipeSerializer<FissionReactorRecipe>(FissionReactorRecipe.class);
	
}
