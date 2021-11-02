package nuclearscience.compatability.jei.recipecategories.psuedorecipes;

import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoGasCentrifugeRecipe extends O2ORecipe {

    public FluidStack INPUT_FLUID_STACK;
    public ItemStack OUTPUT_1_ITEM;
    public ItemStack OUTPUT_2_ITEM;

    public PsuedoGasCentrifugeRecipe(FluidStack inputFluid, ItemStack output1, ItemStack output2) {
	super(null, null, null);
	INPUT_FLUID_STACK = inputFluid;
	OUTPUT_1_ITEM = output1;
	OUTPUT_2_ITEM = output2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
	return null;
    }

    @Override
    public RecipeType<?> getType() {
	return null;
    }

}
