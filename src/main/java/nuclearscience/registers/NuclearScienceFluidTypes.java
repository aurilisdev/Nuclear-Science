package nuclearscience.registers;

import static nuclearscience.registers.NuclearScienceFluids.fluidAmmonia;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;

public class NuclearScienceFluidTypes {
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, References.ID);
	static {
		FLUID_TYPES.register("fluidammonia", () -> fluidAmmonia.getFluidType());
	}
}
