package nuclearscience.common.recipe.categories.item2item;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class FuelReprocessorRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "fuel_reprocessor_recipe";
	public static final ResourceLocation RECIPE_ID = NuclearScience.rl(RECIPE_GROUP);

	public FuelReprocessorRecipe(ResourceLocation group, List<CountableIngredient> inputs, ItemStack output, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts) {
		super(group, inputs, output, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipies.FUEL_REPROCESSOR_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return NuclearScienceRecipies.FUEL_REPROCESSOR_TYPE;
	}

}
