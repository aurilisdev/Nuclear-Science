package nuclearscience.common.recipe.categories.fluiditem2fluid;

import java.util.List;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.common.recipe.recipeutils.FluidIngredient;
import voltaic.common.recipe.recipeutils.ProbableFluid;
import voltaic.common.recipe.recipeutils.ProbableItem;

public class NuclearBoilerRecipe extends FluidItem2FluidRecipe {

	public static final String RECIPE_GROUP = "nuclear_boiler_recipe";
	public static final ResourceLocation RECIPE_ID = NuclearScience.rl(RECIPE_GROUP);

	public NuclearBoilerRecipe(ResourceLocation group, List<CountableIngredient> inputItems, List<FluidIngredient> inputFluids, FluidStack outputFluid, double experience, int ticks, double usagePerTick, List<ProbableItem> itemBiproducts, List<ProbableFluid> fluidBiproducts) {
        super(group, inputItems, inputFluids, outputFluid, experience, ticks, usagePerTick, itemBiproducts, fluidBiproducts);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return NuclearScienceRecipies.NUCLEAR_BOILER_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return NuclearScienceRecipies.NUCLEAR_BOILER_TYPE;
	}

}
