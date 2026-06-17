package nuclearscience.registers;

import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.common.fluid.SimpleWaterBasedFluidType;
import voltaic.prefab.utilities.math.Color;

public class NuclearScienceFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, NuclearScience.ID);

    public static final DeferredHolder<Fluid, FluidNonPlaceable> FLUID_IODINESOLUTION = FLUIDS
	    .register("iodinesolution",
		    () -> new FluidNonPlaceable(ElectrodynamicsItems.ITEM_CANISTERREINFORCED,
			    new SimpleWaterBasedFluidType(NuclearScience.ID, "iodinesolution", "iodinesolution",
				    new Color(255, 251, 245, 255))));
    public static final DeferredHolder<Fluid, FluidNonPlaceable> FLUID_METHANOL = FLUIDS
	    .register("methanol",
		    () -> new FluidNonPlaceable(ElectrodynamicsItems.ITEM_CANISTERREINFORCED,
			    new SimpleWaterBasedFluidType(NuclearScience.ID, "methanol", "methanol",
				    new Color(245, 220, 255, 255))));
    public static final DeferredHolder<Fluid, FluidNonPlaceable> FLUID_DECONTAMINATIONFOAM = FLUIDS
	    .register("decontaminationfoam",
		    () -> new FluidNonPlaceable(ElectrodynamicsItems.ITEM_CANISTERREINFORCED,
			    new SimpleWaterBasedFluidType(NuclearScience.ID, "decontaminationfoam",
				    "decontaminationfoam", Color.WHITE)));
    public static final DeferredHolder<Fluid, FluidNonPlaceable> FLUID_URANIUMHEXAFLUORIDE = FLUIDS
	    .register("uraniumhexafluoride",
		    () -> new FluidNonPlaceable(ElectrodynamicsItems.ITEM_CANISTERREINFORCED,
			    new SimpleWaterBasedFluidType(NuclearScience.ID, "uraniumhexafluoride",
				    "uraniumhexafluoride", Color.WHITE)));
}
