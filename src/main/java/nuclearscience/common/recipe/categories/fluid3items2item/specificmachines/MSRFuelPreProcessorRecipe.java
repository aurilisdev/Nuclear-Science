package nuclearscience.common.recipe.categories.fluid3items2item.specificmachines;

import electrodynamics.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class MSRFuelPreProcessorRecipe extends Fluid3Items2ItemRecipe {

    public static final String RECIPE_GROUP = "msrfuel_preprocessor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public MSRFuelPreProcessorRecipe(ResourceLocation location, FluidIngredient inputFluid, CountableIngredient inputItem1,
	    CountableIngredient inputItem2, CountableIngredient inputItem, ItemStack outputItem) {
	super(location, inputFluid, inputItem1, inputItem2, inputItem, outputItem);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return Registry.RECIPE_TYPE.get(RECIPE_ID);
    }
}
