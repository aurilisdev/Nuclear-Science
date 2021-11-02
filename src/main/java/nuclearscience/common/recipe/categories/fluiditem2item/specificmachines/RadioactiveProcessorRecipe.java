package nuclearscience.common.recipe.categories.fluiditem2item.specificmachines;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class RadioactiveProcessorRecipe extends FluidItem2ItemRecipe {

    public static final String RECIPE_GROUP = "radioactive_processor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public RadioactiveProcessorRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput) {
	super(recipeID, itemInput, fluidInput, itemOutput);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return Registry.RECIPE_TYPE.get(RECIPE_ID);
    }

}
