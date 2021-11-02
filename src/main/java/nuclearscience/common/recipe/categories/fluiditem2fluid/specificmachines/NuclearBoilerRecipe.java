package nuclearscience.common.recipe.categories.fluiditem2fluid.specificmachines;

import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class NuclearBoilerRecipe extends FluidItem2FluidRecipe {

    public static final String RECIPE_GROUP = "nuclear_boiler_recipe";
    public static final String MOD_ID = electrodynamics.api.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public NuclearBoilerRecipe(ResourceLocation recipeID, CountableIngredient inputItem, FluidIngredient inputFluid, FluidStack outputFluid) {
	super(recipeID, inputItem, inputFluid, outputFluid);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.NUCLEAR_BOILER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return Registry.RECIPE_TYPE.get(RECIPE_ID);
    }

}
