package nuclearscience.common.tags;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import nuclearscience.common.fluid.types.FluidAmmonia;
import nuclearscience.common.fluid.types.FluidUraniumHexafluoride;
import nuclearscience.common.item.ItemCanisterLead;

public class NuclearScienceTags {

	public static List<TagKey<Fluid>> FLUID_TAGS = new ArrayList<>();

	public static void init() {
		Fluids.init();
	}

	public static List<TagKey<Fluid>> getFluidTags() {
		return FLUID_TAGS;
	}

	public static class Fluids {

		public static final TagKey<Fluid> URANIUM_HEXAFLUORIDE = forgeTag(FluidUraniumHexafluoride.FORGE_TAG);
		public static final TagKey<Fluid> AMMONIA = forgeTag(FluidAmmonia.FORGE_TAG);

		private static void init() {
			FLUID_TAGS.add(URANIUM_HEXAFLUORIDE);
			FLUID_TAGS.add(AMMONIA);

			ItemCanister.addTag(AMMONIA);

			ItemCanisterLead.addTag(URANIUM_HEXAFLUORIDE);
		}

		private static TagKey<Fluid> forgeTag(String name) {
			return FluidTags.create(new ResourceLocation("forge", name));
		}
	}
}
