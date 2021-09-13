package nuclearscience.common.recipe.categories.fluiditem2item.specificmachines;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class RadioactiveProcessorRecipe extends FluidItem2ItemRecipe {

    public static final String RECIPE_GROUP = "radioactive_processor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public RadioactiveProcessorRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput) {
	super(recipeID, itemInput, fluidInput, itemOutput);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
	return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
    }

}
