package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceItems;

public class FluidUraniumHexafluoride extends FluidNonPlaceable {

	public static final String FORGE_TAG = "uranium_hexafluoride";

	private final FluidType type;

	public FluidUraniumHexafluoride() {
		super(() -> NuclearScienceItems.ITEM_CANISTERLEAD);
		type = new SimpleWaterBasedFluidType(References.ID, "fluiduraniumhexafluoride", -431922120);
	}

	@Override
	public FluidType getFluidType() {
		return type;
	}
}
