package nuclearscience.registers;

import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.NuclearScience;

public class NuclearScienceFluidTypes {
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, NuclearScience.ID);

	public static final DeferredHolder<FluidType, FluidType> FLUID_TYPE_IODINESOLUTION = FLUID_TYPES.register("iodinesolution", () -> NuclearScienceFluids.FLUID_IODINESOLUTION.get().getFluidType());
	public static final DeferredHolder<FluidType, FluidType> FLUID_TYPE_METHANOL = FLUID_TYPES.register("methanol", () -> NuclearScienceFluids.FLUID_METHANOL.get().getFluidType());
	public static final DeferredHolder<FluidType, FluidType> FLUID_TYPE_DECONTAMINATIONFOAM = FLUID_TYPES.register("decontaminationfoam", () -> NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM.get().getFluidType());

	public static final DeferredHolder<FluidType, FluidType> FLUID_TYPE_URANIUMHEXAFLUORIDE = FLUID_TYPES.register("uraniumhexafluoride", () -> NuclearScienceFluids.FLUID_URANIUMHEXAFLUORIDE.get().getFluidType());
}
