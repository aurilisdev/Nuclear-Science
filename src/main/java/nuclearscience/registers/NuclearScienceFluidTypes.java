package nuclearscience.registers;

import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.NuclearScience;

public class NuclearScienceFluidTypes {
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, NuclearScience.ID);
	
	public static final RegistryObject<FluidType> FLUID_TYPE_IODINESOLUTION = FLUID_TYPES.register("iodinesolution", () -> NuclearScienceFluids.FLUID_IODINESOLUTION.get().getFluidType());
	public static final RegistryObject<FluidType> FLUID_TYPE_METHANOL = FLUID_TYPES.register("methanol", () -> NuclearScienceFluids.FLUID_METHANOL.get().getFluidType());
	public static final RegistryObject<FluidType> FLUID_TYPE_DECONTAMINATIONFOAM = FLUID_TYPES.register("decontaminationfoam", () -> NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM.get().getFluidType());
	public static final RegistryObject<FluidType> FLUID_TYPE_STEAM = FLUID_TYPES.register("steam", () -> NuclearScienceFluids.FLUID_STEAM.get().getFluidType());
	public static final RegistryObject<FluidType> FLUID_TYPE_URANIUMHEXAFLUORIDE = FLUID_TYPES.register("uraniumhexafluoride", () -> NuclearScienceFluids.FLUID_URANIUMHEXAFLUORIDE.get().getFluidType());
}
