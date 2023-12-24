package nuclearscience.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;
import nuclearscience.common.fluid.types.FluidAmmonia;
import nuclearscience.common.fluid.types.FluidUraniumHexafluoride;

public class NuclearScienceFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);

	public static FluidUraniumHexafluoride fluidUraniumHexafluoride;
	public static FluidAmmonia fluidAmmonia;

	static {
		FLUIDS.register("fluiduraniumhexafluoride", supplier(() -> fluidUraniumHexafluoride = new FluidUraniumHexafluoride()));
		FLUIDS.register("fluidammonia", supplier(() -> fluidAmmonia = new FluidAmmonia()));
	}
}
