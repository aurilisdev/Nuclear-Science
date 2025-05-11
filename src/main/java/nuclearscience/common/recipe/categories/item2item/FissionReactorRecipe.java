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

public class FissionReactorRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "fission_reactor_recipe";
	public static final ResourceLocation RECIPE_ID = NuclearScience.rl(RECIPE_GROUP);

	public FissionReactorRecipe(ResourceLocation group, List<CountableIngredient> inputs, ItemStack output, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts) {
		super(group, inputs, output, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipies.FISSION_REACTOR_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return NuclearScienceRecipies.FISSION_REACTOR_TYPE;
	}

}
