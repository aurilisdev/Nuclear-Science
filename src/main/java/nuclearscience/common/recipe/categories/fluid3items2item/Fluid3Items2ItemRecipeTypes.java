package nuclearscience.common.recipe.categories.fluid3items2item;

import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import nuclearscience.common.recipe.categories.fluiditem2fluid.specificmachines.NuclearBoilerRecipe;

public class Fluid3Items2ItemRecipeTypes {

    public static final IRecipeSerializer<NuclearBoilerRecipe> MSR_FUEL_PREPROCESSOR_JSON_SERIALIZER = new FluidItem2FluidRecipeSerializer<>(
	    NuclearBoilerRecipe.class);

}
