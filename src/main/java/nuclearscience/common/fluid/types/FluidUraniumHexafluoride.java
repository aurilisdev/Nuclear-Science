package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraftforge.fluids.FluidType;
import nuclearscience.References;

public class FluidUraniumHexafluoride extends FluidNonPlaceable {

	public static final String FORGE_TAG = "uranium_hexafluoride";

	private final FluidType type;

	public FluidUraniumHexafluoride() {
		super(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED);
		type = new SimpleWaterBasedFluidType(References.ID, "uraniumhexafluoride", -431922120);
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
