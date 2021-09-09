package nuclearscience.common.item;

import java.util.ArrayList;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import nuclearscience.DeferredRegisters;

public class ItemCanisterLead extends ItemCanister {

    public ItemCanisterLead(Properties itemProperty) {
	super(itemProperty);
    }

    // TODO handle container item crafting

    @Override
    public ArrayList<Fluid> getWhitelistedFluids() {
	ArrayList<Fluid> whitelist = new ArrayList<>();
	for (RegistryObject<Fluid> fluid : DeferredRegisters.FLUIDS.getEntries()) {
	    whitelist.add(fluid.get());
	}
	return whitelist;
    }

}
