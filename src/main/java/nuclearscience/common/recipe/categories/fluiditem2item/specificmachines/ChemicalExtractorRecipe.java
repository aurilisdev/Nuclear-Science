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

public class ChemicalExtractorRecipe extends FluidItem2ItemRecipe {

    public static final String RECIPE_GROUP = "chemical_extractor_recipe";
    public static final String MOD_ID = electrodynamics.api.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    public ChemicalExtractorRecipe(ResourceLocation recipeID, CountableIngredient itemInput, FluidIngredient fluidInput, ItemStack itemOutput) {
	super(recipeID, itemInput, fluidInput, itemOutput);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
	return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
    }

}
