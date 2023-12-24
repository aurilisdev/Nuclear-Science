package nuclearscience.common.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import nuclearscience.registers.NuclearScienceBlocks;

public class ItemGroupNuclearScience extends ItemGroup {

	public ItemGroupNuclearScience(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(NuclearScienceBlocks.blockGasCentrifuge);
	}
}