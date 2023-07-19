package nuclearscience.common.recipe.categories.fluiditem2gas.specificmachines;

import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.categories.fluiditem2gas.FluidItem2GasRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableGas;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class NuclearBoilerRecipe extends FluidItem2GasRecipe {

	public static final String RECIPE_GROUP = "nuclear_boiler_recipe";
	public static final String MOD_ID = References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public NuclearBoilerRecipe(ResourceLocation recipeID, CountableIngredient[] inputItems, FluidIngredient[] inputFluids, GasStack outputGas, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproudcts, ProbableGas[] gasBiproducts) {
		super(recipeID, inputItems, inputFluids, outputGas, experience, ticks, usagePerTick, itemBiproducts, fluidBiproudcts, gasBiproducts);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipeInit.NUCLEAR_BOILER_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get();
	}

}
