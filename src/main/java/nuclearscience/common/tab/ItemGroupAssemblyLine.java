package nuclearscience.common.tab;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupAssemblyLine extends ItemGroup {

	public ItemGroupAssemblyLine(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Blocks.BARREL);
	}
}