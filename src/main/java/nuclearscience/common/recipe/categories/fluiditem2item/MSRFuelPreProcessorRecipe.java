package nuclearscience.common.recipe.categories.fluiditem2item;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class MSRFuelPreProcessorRecipe extends FluidItem2ItemRecipe {

	public static final String RECIPE_GROUP = "msrfuel_preprocessor_recipe";
	public static final ResourceLocation RECIPE_ID = NuclearScience.rl(RECIPE_GROUP);

	public MSRFuelPreProcessorRecipe(String group, List<CountableIngredient> itemInputs, List<FluidIngredient> fluidInputs, ItemStack itemOutput, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
		super(group, itemInputs, fluidInputs, itemOutput, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts, gasBiproducts);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipies.MSR_FUEL_PREPROCESSOR_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return NuclearScienceRecipies.MSR_FUEL_PREPROCESSOR_TYPE.get();
	}
}
