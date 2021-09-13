package nuclearscience.common.recipe.categories.fluid3items2item.specificmachines;

import java.util.List;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.IFluidRecipe;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;

public class MSRFuelPreProcessorRecipe extends ElectrodynamicsRecipe implements IFluidRecipe {

    public static final String RECIPE_GROUP = "msrfuel_preprocessor_recipe";
    public static final String MOD_ID = nuclearscience.References.ID;
    public static final ResourceLocation RECIPE_ID = new ResourceLocation(MOD_ID, RECIPE_GROUP);

    private FluidIngredient INPUT_FLUID;
    private CountableIngredient INPUT_ITEM1;
    private CountableIngredient INPUT_ITEM2;
    private CountableIngredient INPUT_ITEM3;

    private ItemStack OUTPUT_ITEM;

    @Override
    public IRecipeSerializer<?> getSerializer() {
	return NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
	return Registry.RECIPE_TYPE.getOrDefault(RECIPE_ID);
    }

    @Override
    public boolean matchesRecipe(ComponentProcessor pr) {
	if (INPUT_ITEM1.testStack(pr.getInput()) && INPUT_ITEM2.testStack(pr.getSecondInput()) && INPUT_ITEM3.testStack(pr.getThirdInput())) {
	    AbstractFluidHandler<?> fluid = pr.getHolder().getComponent(ComponentType.FluidHandler);
	    List<Fluid> inputFluids = fluid.getValidInputFluids();
	    for (int i = 0; i < inputFluids.size(); i++) {
		FluidTank tank = fluid.getTankFromFluid(inputFluids.get(i), true);
		if (tank != null && tank.getFluid().getFluid().isEquivalentTo(INPUT_FLUID.getFluidStack().getFluid())
			&& tank.getFluidAmount() >= INPUT_FLUID.getFluidStack().getAmount()) {
		    return true;
		}
	    }

	}
	return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
	return OUTPUT_ITEM;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
	return OUTPUT_ITEM;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
	return NonNullList.from(null, INPUT_ITEM1, INPUT_ITEM2, INPUT_ITEM3, INPUT_FLUID);
    }

}
