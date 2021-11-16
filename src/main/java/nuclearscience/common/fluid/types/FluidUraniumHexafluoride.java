package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;

public class FluidUraniumHexafluoride extends FluidNonPlaceable {
	
	public static final String FORGE_TAG = "uranium_hexafluoride";

    public FluidUraniumHexafluoride() {
	super(() -> DeferredRegisters.ITEM_CANISTERLEAD, References.ID, "uraniumhexafluoride", -431922120);
    }

}
