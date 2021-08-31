package nuclearscience.common.item;

import java.util.ArrayList;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.RegistryObject;
import nuclearscience.DeferredRegisters;

public class ItemCanisterLead extends ItemCanister {

    public ItemCanisterLead(Properties itemProperty) {
	super(itemProperty);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
	boolean isEmpty = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).map(m -> {
	    return m.getFluidInTank(0).getFluid().isEquivalentTo(Fluids.EMPTY);
	}).orElse(true);
	if (isEmpty) {
	    return new ItemStack(Items.AIR);
	}
	return new ItemStack(DeferredRegisters.ITEM_CANISTERLEAD.get());
    }

    @Override
    public ArrayList<Fluid> getWhitelistedFluids() {
	ArrayList<Fluid> whitelist = new ArrayList<>();
	for (RegistryObject<Fluid> fluid : DeferredRegisters.FLUIDS.getEntries()) {
	    whitelist.add(fluid.get());
	}
	return whitelist;
    }

}
