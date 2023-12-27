package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.registers.ElectrodynamicsItems;

public class FluidAmmonia extends FluidNonPlaceable {

	public static final String FORGE_TAG = "ammonia";

	public FluidAmmonia() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED, nuclearscience.References.ID, "ammonia");
	}

}
