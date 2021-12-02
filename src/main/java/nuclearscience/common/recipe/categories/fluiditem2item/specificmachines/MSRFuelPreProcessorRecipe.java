package nuclearscience.common.recipe.categories.fluiditem2item.specificmachines;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class MSRFuelPreProcessorRecipe extends FluidItem2ItemRecipe {

    public static final String RECIPE_GROUP = "msrfuel_preprocessor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public MSRFuelPreProcessorRecipe(ResourceLocation location, CountableIngredient[] inputItems, FluidIngredient[] inputFluids,
	    ItemStack outputItem) {
	super(location, inputItems, inputFluids, outputItem);
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
