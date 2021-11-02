package nuclearscience.common.recipe.categories.o2o.specificmachines;

import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class FissionReactorRecipe extends O2ORecipe {

    public static final String RECIPE_GROUP = "fission_reactor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public FissionReactorRecipe(ResourceLocation id, CountableIngredient input, ItemStack output) {
	super(id, input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.FISSION_REACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return Registry.RECIPE_TYPE.get(RECIPE_ID);
    }

}
