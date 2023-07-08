package nuclearscience.compatibility.jei.utils.psuedorecipes;

import electrodynamics.common.recipe.recipeutils.GasIngredient;
import net.minecraft.world.item.ItemStack;

public class PsuedoGasCentrifugeRecipe {

	public GasIngredient inputGasStack;
	public ItemStack output1;
	public ItemStack output2;
	public ItemStack biproduct;

	public PsuedoGasCentrifugeRecipe(GasIngredient inputGas, ItemStack output1, ItemStack output2, ItemStack biproduct) {
		inputGasStack = inputGas;
		this.output1 = output1;
		this.output2 = output2;
		this.biproduct = biproduct;
	}

}
