package nuclearscience.common.recipe.categories.item2item.specificmachines;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;


public class FissionReactorRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "fission_reactor_recipe";
	public static final String MOD_ID = nuclearscience.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public FissionReactorRecipe(ResourceLocation id, CountableIngredient[] inputs, ItemStack output, double experience, int ticks, double usagePerTick, ProbableItem[] itemBiproducts, ProbableFluid[] fluidBiproudcts) {
		super(id, inputs, output, experience, ticks, usagePerTick, itemBiproducts, fluidBiproudcts);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipeInit.FISSION_REACTOR_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return NuclearScienceRecipeInit.FISSION_REACTOR_TYPE;
	}

}
