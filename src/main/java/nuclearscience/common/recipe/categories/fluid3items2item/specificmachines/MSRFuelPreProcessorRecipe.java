package nuclearscience.common.recipe.categories.fluid3items2item.specificmachines;

import electrodynamics.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class MSRFuelPreProcessorRecipe extends Fluid3Items2ItemRecipe {

    public static final String RECIPE_GROUP = "msrfuel_preprocessor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public MSRFuelPreProcessorRecipe(FluidIngredient iNPUT_FLUID, CountableIngredient iNPUT_ITEM1, CountableIngredient iNPUT_ITEM2,
	    CountableIngredient iNPUT_ITEM3, ItemStack oUTPUT_ITEM) {
	super(RECIPE_ID, iNPUT_FLUID, iNPUT_ITEM1, iNPUT_ITEM2, iNPUT_ITEM3, oUTPUT_ITEM);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
	return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
    }
}
