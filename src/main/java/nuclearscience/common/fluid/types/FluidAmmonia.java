package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;
import nuclearscience.References;

public class FluidAmmonia extends FluidNonPlaceable {

	public static final String FORGE_TAG = "ammonia";

	private final FluidType type;

	public FluidAmmonia() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "ammonia");
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
