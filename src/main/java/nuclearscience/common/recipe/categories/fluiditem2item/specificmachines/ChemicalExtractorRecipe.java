package nuclearscience.common.recipe.categories.fluiditem2item.specificmachines;

import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class ChemicalExtractorRecipe extends FluidItem2ItemRecipe {

	public static final String RECIPE_GROUP = "chemical_extractor_recipe";
	public static final String MOD_ID = electrodynamics.api.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public ChemicalExtractorRecipe(ResourceLocation recipeID, CountableIngredient[] itemInputs, FluidIngredient[] fluidInputs, ItemStack itemOutput, double experience, int ticks, double usagePerTick) {
		super(recipeID, itemInputs, fluidInputs, itemOutput, experience, ticks, usagePerTick);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_TYPE.get();
	}

}
