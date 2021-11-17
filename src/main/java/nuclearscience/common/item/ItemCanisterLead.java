package nuclearscience.common.item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.DeferredRegisters;

public class ItemCanisterLead extends ItemCanister {

	public static List<ResourceLocation> TAG_NAMES = new ArrayList<>();
	
    public ItemCanisterLead(Properties itemProperty) {
	super(itemProperty);
    }

    @Override
    public Pair<List<ResourceLocation>, List<Fluid>> getWhitelistedFluids() {
		ArrayList<Fluid> whitelist = new ArrayList<>();
		for (Fluid fluid : ForgeRegistries.FLUIDS.getValues()) {
		    if (fluid.getFilledBucket().getRegistryName().equals(DeferredRegisters.ITEM_CANISTERLEAD.get().getRegistryName())) {
			whitelist.add(fluid);
		    }
		}
		return Pair.of(TAG_NAMES, whitelist);
    }
    
    public static void addTag(Tags.IOptionalNamedTag<Fluid> tag) {
    	TAG_NAMES.add(tag.getName());
    }

}
