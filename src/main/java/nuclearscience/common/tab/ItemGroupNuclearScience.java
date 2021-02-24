package nuclearscience.common.tab;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import nuclearscience.DeferredRegisters;

public class ItemGroupNuclearScience extends ItemGroup {

    public ItemGroupNuclearScience(String label) {
	super(label);
    }

    @Override
    public ItemStack createIcon() {
	return new ItemStack(DeferredRegisters.blockGasCentrifuge);
    }
}