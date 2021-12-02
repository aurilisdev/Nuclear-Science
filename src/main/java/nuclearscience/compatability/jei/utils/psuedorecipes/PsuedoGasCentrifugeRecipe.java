package nuclearscience.compatability.jei.utils.psuedorecipes;

import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoGasCentrifugeRecipe extends Item2ItemRecipe {

    public FluidStack inputFluidStack;
    public ItemStack output1;
    public ItemStack output2;

    public PsuedoGasCentrifugeRecipe(FluidStack inputFluid, ItemStack output1, ItemStack output2) {
	super(null, null, null);
	inputFluidStack = inputFluid;
	this.output1 = output1;
	this.output2 = output2;
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
