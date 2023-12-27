package nuclearscience.compatibility.jei.utils.psuedorecipes;

import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.item.ItemStack;

public class PsuedoGasCentrifugeRecipe {

	public FluidIngredient inputFluidStack;
	public ItemStack output1;
	public ItemStack output2;
	public ItemStack biproduct;

	public PsuedoGasCentrifugeRecipe(FluidIngredient inputGas, ItemStack output1, ItemStack output2, ItemStack biproduct) {
		inputFluidStack = inputGas;
		this.output1 = output1;
		this.output2 = output2;
		this.biproduct = biproduct;
	}

}
