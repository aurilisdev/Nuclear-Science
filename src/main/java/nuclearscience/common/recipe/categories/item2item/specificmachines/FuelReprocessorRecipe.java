package nuclearscience.common.recipe.categories.item2item.specificmachines;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class FuelReprocessorRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "fuel_reprocessor_recipe";
	public static final String MOD_ID = nuclearscience.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public FuelReprocessorRecipe(ResourceLocation id, CountableIngredient[] inputs, ItemStack output, double experience, int ticks, double usagePerTick) {
		super(id, inputs, output, experience, ticks, usagePerTick);
	}

	public FuelReprocessorRecipe(ResourceLocation id, CountableIngredient[] input, ItemStack output, ProbableItem[] itemBiproducts, double experience, int ticks, double usagePerTick) {
		super(id, input, output, itemBiproducts, experience, ticks, usagePerTick);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipeInit.FUEL_REPROCESSOR_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE.get();
	}

}
