package nuclearscience.common.recipe.categories.item2item.specificmachines;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class FissionReactorRecipe extends Item2ItemRecipe {

	public static final String RECIPE_GROUP = "fission_reactor_recipe";
	public static final String MOD_ID = nuclearscience.References.ID;
	public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

	public FissionReactorRecipe(ResourceLocation id, CountableIngredient[] inputs, ItemStack output, double experience) {
		super(id, inputs, output, experience);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipeInit.FISSION_REACTOR_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return NuclearScienceRecipeInit.FISSION_REACTOR_TYPE.get();
	}

}
