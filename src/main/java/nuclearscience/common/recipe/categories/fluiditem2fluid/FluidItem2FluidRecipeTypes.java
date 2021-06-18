package nuclearscience.common.recipe.categories.fluiditem2fluid;

import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2fluid.specificmachines.NuclearBoilerRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public class FluidItem2FluidRecipeTypes {

	public static final IRecipeSerializer<NuclearBoilerRecipe> NUCLEAR_BOILER_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<NuclearBoilerRecipe>(NuclearBoilerRecipe.class);
	
}
