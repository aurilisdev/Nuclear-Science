package nuclearscience.common.recipe.categories.item2item;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FuelReprocessorRecipe;

public class Item2ItemRecipeTypes {

	public static final RecipeSerializer<FissionReactorRecipe> FISSION_REACTOR_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(
			FissionReactorRecipe.class);
	public static final RecipeSerializer<FuelReprocessorRecipe> FUEL_REPROCESSOR_JSON_SERIALIZER = new Item2ItemRecipeSerializer<>(
			FuelReprocessorRecipe.class);
}
