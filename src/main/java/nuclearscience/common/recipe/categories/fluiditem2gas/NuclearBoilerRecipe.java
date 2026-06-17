package nuclearscience.common.recipe.categories.fluiditem2gas;

import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.api.gas.GasStack;
import voltaic.common.recipe.categories.fluiditem2gas.FluidItem2GasRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableGas;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class NuclearBoilerRecipe extends FluidItem2GasRecipe {

    public static final String RECIPE_GROUP = "nuclear_boiler_recipe";
    public static final ResourceLocation RECIPE_ID = NuclearScience.rl(RECIPE_GROUP);

    public NuclearBoilerRecipe(ResourceLocation group, List<CountableIngredient> inputItems,
	    List<FluidIngredient> inputFluids, GasStack outputGas, double experience, int ticks, double usagePerTick,
	    List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts, List<ProbableGas> gasBiproducts) {
	super(group, inputItems, inputFluids, outputGas, experience, ticks, usagePerTick, itemBiproducts,
		fluidBiproducts, gasBiproducts);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipies.NUCLEAR_BOILER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
	return NuclearScienceRecipies.NUCLEAR_BOILER_TYPE.get();
    }

}
