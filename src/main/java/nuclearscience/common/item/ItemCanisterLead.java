package nuclearscience.common.item;

import java.util.ArrayList;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.DeferredRegisters;

import net.minecraft.world.item.Item.Properties;

public class ItemCanisterLead extends ItemCanister {

    public ItemCanisterLead(Properties itemProperty) {
	super(itemProperty);
    }

    // TODO handle container item crafting

    @Override
    public ArrayList<Fluid> getWhitelistedFluids() {
	ArrayList<Fluid> whitelist = new ArrayList<>();
	for (Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
	    if (fluid.getBucket().getRegistryName().equals(DeferredRegisters.ITEM_CANISTERLEAD.get().getRegistryName())) {
		whitelist.add(fluid);
	    }
	}
	return whitelist;
    }

}
