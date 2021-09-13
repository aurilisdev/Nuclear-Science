package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidIronSulfamate extends FluidNonPlaceable {

    public FluidIronSulfamate() {
	super(() -> electrodynamics.DeferredRegisters.ITEM_CANISTERREINFORCED, nuclearscience.References.ID, "ironsulfamate");
    }

}
