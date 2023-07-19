package nuclearscience.registers;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;
import nuclearscience.common.fluid.types.FluidAmmonia;

public class NuclearScienceFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);

	public static FluidAmmonia fluidAmmonia;

	static {
		FLUIDS.register("fluidammonia", () -> fluidAmmonia = new FluidAmmonia());
	}
}
