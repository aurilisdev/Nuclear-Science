package nuclearscience.common.tags;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import nuclearscience.common.fluid.types.FluidAmmonia;
import nuclearscience.common.fluid.types.FluidIronSulfamate;
import nuclearscience.common.fluid.types.FluidUraniumHexafluoride;
import nuclearscience.common.item.ItemCanisterLead;

public class NuclearScienceTags {
	
	public static List<Tags.IOptionalNamedTag<Fluid>> FLUID_TAGS = new ArrayList<>();
	
	public static void init() {
		Fluids.init();
	}
	
	public static List<Tags.IOptionalNamedTag<Fluid>> getFluidTags(){
		return FLUID_TAGS;
	}
	
	public static class Fluids {
		
		public static final Tags.IOptionalNamedTag<Fluid> URANIUM_HEXAFLUORIDE = forgeTag(FluidUraniumHexafluoride.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> IRON_SULFAMATE = forgeTag(FluidIronSulfamate.FORGE_TAG);
		public static final Tags.IOptionalNamedTag<Fluid> AMMONIA = forgeTag(FluidAmmonia.FORGE_TAG);
		
		private static void init() {
			FLUID_TAGS.add(URANIUM_HEXAFLUORIDE);
			FLUID_TAGS.add(IRON_SULFAMATE);
			FLUID_TAGS.add(AMMONIA);
			
			ItemCanister.addTag(IRON_SULFAMATE);
			ItemCanister.addTag(AMMONIA);
			
			ItemCanisterLead.addTag(URANIUM_HEXAFLUORIDE);
		}
		
		private static Tags.IOptionalNamedTag<Fluid> forgeTag(String name){
			return FluidTags.createOptional(new ResourceLocation("forge", name));
		}
	}
}
