package nuclearscience.common.tags;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import voltaic.Voltaic;

public class NuclearScienceTags {

	public static void init() {
		Fluids.init();
		Items.init();
		Blocks.init();
	}

	public static class Items {

		public static final INamedTag<Item> CELL_EMPTY = forgeTag("cells/empty");
		public static final INamedTag<Item> CELL_HEAVYWATER = forgeTag("cells/heavywater");
		public static final INamedTag<Item> CELL_DEUTERIUM = forgeTag("cells/deuterium");
		public static final INamedTag<Item> CELL_TRITIUM = forgeTag("cells/tritium");
		public static final INamedTag<Item> CELL_ANTIMATTER_SMALL = forgeTag("cells/anti_matter_small");
		public static final INamedTag<Item> CELL_ANTIMATTER_LARGE = forgeTag("cells/anti_matter_large");
		public static final INamedTag<Item> CELL_ANTIMATTER_VERY_LARGE = forgeTag("cells/anti_matter_very_large");
		public static final INamedTag<Item> CELL_DARK_MATTER = forgeTag("cells/dark_matter");

		public static final INamedTag<Item> FUELROD_URANIUM_LOW_EN = forgeTag("fuel_rods/leuo2");
		public static final INamedTag<Item> FUELROD_URANIUM_HIGH_EN = forgeTag("fuel_rods/heuo2");
		public static final INamedTag<Item> FUELROD_PLUTONIUM = forgeTag("fuel_rods/plutonium");
		public static final INamedTag<Item> FUELROD_SPENT = forgeTag("fuel_rods/spent");

		public static final INamedTag<Item> DUST_THORIUM = forgeTag("dusts/thorium");
		public static final INamedTag<Item> DUST_FISSILE = forgeTag("dusts/fissile");

		public static final INamedTag<Item> SALT_FISSILE = forgeTag("salts/fissile");

		public static final INamedTag<Item> OXIDE_PLUTONIUM = forgeTag("oxide/plutonium");
		public static final INamedTag<Item> OXIDE_ACTINIUM = forgeTag("oxide/actinium");

		public static final INamedTag<Item> NUGGET_POLONIUM = forgeTag("nuggets/polonium");

		public static final INamedTag<Item> PELLET_URANIUM235 = forgeTag("pellets/uranium235");
		public static final INamedTag<Item> PELLET_URANIUM238 = forgeTag("pellets/uranium238");
		public static final INamedTag<Item> PELLET_PLUTONIUM = forgeTag("pellets/plutonium");
		public static final INamedTag<Item> PELLET_POLONIUM = forgeTag("pellets/polonium");
		public static final INamedTag<Item> PELLET_LIFHT4PUF3 = forgeTag("pellets/lifht4puf3");
		public static final INamedTag<Item> PELLET_FLINAK = forgeTag("pellets/flinak");
		public static final INamedTag<Item> PELLET_ACTINIUM225 = forgeTag("pellets/actinium225");

		public static final INamedTag<Item> YELLOW_CAKE = forgeTag("yellow_cake_uranium");

		private static void init() {
		}

		private static INamedTag<Item> forgeTag(String name) {
			return ItemTags.createOptional(Voltaic.forgerl(name));
		}

	}

	public static class Blocks {

		public static final INamedTag<Block> PARTICLE_CONTAINMENT = forgeTag("particle_containment");
		public static final INamedTag<Block> FUSION_CONTAINMENT = forgeTag("fusion_containment");


		private static void init() {
		}

		private static INamedTag<Block> forgeTag(String name) {
			return BlockTags.createOptional(Voltaic.forgerl(name));
		}

	}

	public static class Fluids {

		public static final INamedTag<Fluid> DECONTAMINATION_FOAM = forgeTag("decontamination_foam");
		public static final INamedTag<Fluid> IODINE_SOLUTION = forgeTag("iodine_solution");
		public static final INamedTag<Fluid> METHANOL = forgeTag("methanol");
		public static final INamedTag<Fluid> STEAM = forgeTag("steam");
		public static final INamedTag<Fluid> URANIUM_HEXAFLUORIDE = forgeTag("uraniumhexafluoride");

		private static void init() {

		}

		private static INamedTag<Fluid> forgeTag(String name) {
			return FluidTags.createOptional(Voltaic.forgerl(name));
		}
	}

}
