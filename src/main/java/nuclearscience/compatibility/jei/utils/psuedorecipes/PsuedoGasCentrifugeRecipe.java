package nuclearscience.compatibility.jei.utils.psuedorecipes;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class PsuedoGasCentrifugeRecipe {

	public FluidStack inputFluidStack;
	public ItemStack output1;
	public ItemStack output2;
	public ItemStack biproduct;

	public PsuedoGasCentrifugeRecipe(FluidStack inputFluid, ItemStack output1, ItemStack output2, ItemStack biproduct) {
		inputFluidStack = inputFluid;
		this.output1 = output1;
		this.output2 = output2;
		this.biproduct = biproduct;
	}

}
