package nuclearscience.common.item;

import java.util.ArrayList;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.DeferredRegisters;

public class ItemCanisterLead extends ItemCanister {

    public ItemCanisterLead(Properties itemProperty) {
	super(itemProperty);
    }

    // TODO handle container item crafting

    @Override
    public ArrayList<Fluid> getWhitelistedFluids() {
	ArrayList<Fluid> whitelist = new ArrayList<>();
	for(Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
		if(fluid.getFilledBucket().getRegistryName().equals(DeferredRegisters.ITEM_CANISTERLEAD.get().getRegistryName())) {
			whitelist.add(fluid);
		}
	}
	return whitelist;
    }

}
