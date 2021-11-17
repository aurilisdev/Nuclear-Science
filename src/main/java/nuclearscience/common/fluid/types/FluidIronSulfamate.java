package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidIronSulfamate extends FluidNonPlaceable {

	public static final String FORGE_TAG = "iron_sulfamate";
	
    public FluidIronSulfamate() {
	super(() -> electrodynamics.DeferredRegisters.ITEM_CANISTERREINFORCED, nuclearscience.References.ID, "ironsulfamate");
    }

}
