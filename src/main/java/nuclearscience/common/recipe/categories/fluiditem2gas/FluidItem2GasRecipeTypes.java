package nuclearscience.common.recipe.categories.fluiditem2gas;

import electrodynamics.common.recipe.categories.fluiditem2gas.FluidItem2GasRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2gas.specificmachines.NuclearBoilerRecipe;

public class FluidItem2GasRecipeTypes {

	public static final RecipeSerializer<NuclearBoilerRecipe> NUCLEAR_BOILER_JSON_SERIALIZER = new FluidItem2GasRecipeSerializer<>(NuclearBoilerRecipe.class);

}
