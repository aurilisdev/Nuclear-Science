package nuclearscience.common.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.registers.NuclearScienceItems;

public class ItemGroupNuclearScience extends CreativeModeTab {

	public ItemGroupNuclearScience(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge));
	}
}