package nuclearscience.common.item;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
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
			if (fluid.getBucket() != null && DeferredRegisters.ITEM_CANISTERLEAD.get() != null && fluid.getBucket().builtInRegistryHolder().key().location().equals(DeferredRegisters.ITEM_CANISTERLEAD.get().builtInRegistryHolder().key().location())) {
				whitelist.add(fluid);
			}
		}
		return Pair.of(TAG_NAMES, whitelist);
	}

	public static void addTag(TagKey<Fluid> tag) {
		TAG_NAMES.add(tag.location());
	}

}
