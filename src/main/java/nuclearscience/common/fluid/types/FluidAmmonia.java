package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;

public class FluidAmmonia extends FluidNonPlaceable {

    public FluidAmmonia() {
		super(() -> electrodynamics.DeferredRegisters.ITEM_CANISTERREINFORCED, nuclearscience.References.ID, "ammonia");
	}

}
