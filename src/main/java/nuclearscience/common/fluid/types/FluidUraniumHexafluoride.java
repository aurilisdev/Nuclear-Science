package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceItems;

public class FluidUraniumHexafluoride extends FluidNonPlaceable {

	public static final String FORGE_TAG = "uranium_hexafluoride";

	public FluidUraniumHexafluoride() {
		super(() -> NuclearScienceItems.ITEM_CANISTERLEAD, References.ID, "uraniumhexafluoride", -431922120);
	}

}
