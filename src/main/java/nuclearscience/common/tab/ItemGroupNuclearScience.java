package nuclearscience.common.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import nuclearscience.registers.NuclearScienceBlocks;

public class ItemGroupNuclearScience extends CreativeModeTab {

	public ItemGroupNuclearScience(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(NuclearScienceBlocks.blockGasCentrifuge);
	}
}