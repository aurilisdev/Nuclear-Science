package nuclearscience.common.fluid.types;

import electrodynamics.common.fluid.FluidNonPlaceable;
import electrodynamics.common.fluid.types.SimpleWaterBasedFluidType;
import net.minecraftforge.fluids.FluidType;
import nuclearscience.References;
import nuclearscience.common.fluid.IRadioactiveFluid;
import nuclearscience.registers.NuclearScienceItems;

public class FluidUraniumHexafluoride extends FluidNonPlaceable implements IRadioactiveFluid {

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

	@Override
	public double getRadiationRange() {
		return 10;
	}

	@Override
	public double getRadiationStrength() {
		return 1;
	}
}
