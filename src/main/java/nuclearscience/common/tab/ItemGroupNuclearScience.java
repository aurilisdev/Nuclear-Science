package nuclearscience.common.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.registers.NuclearScienceItems;

public class ItemGroupNuclearScience extends ItemGroup {

	public ItemGroupNuclearScience(String label) {
		super(label);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge));
	}
}