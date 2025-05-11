package nuclearscience.registers;

import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.NuclearScience;
import voltaic.common.fluid.FluidNonPlaceable;
import voltaic.prefab.utilities.math.Color;

public class NuclearScienceFluids {
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, NuclearScience.ID);

	public static final RegistryObject<FluidNonPlaceable> FLUID_IODINESOLUTION = FLUIDS.register("iodinesolution", () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), NuclearScience.ID, "iodinesolution", "iodinesolution", new Color(255, 251, 245, 255)));
	public static final RegistryObject<FluidNonPlaceable> FLUID_METHANOL = FLUIDS.register("methanol", () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), NuclearScience.ID, "methanol", "methanol", new Color(245, 220, 255, 255)));
	public static final RegistryObject<FluidNonPlaceable> FLUID_DECONTAMINATIONFOAM = FLUIDS.register("decontaminationfoam", () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), NuclearScience.ID, "decontaminationfoam","decontaminationfoam", Color.WHITE));
	public static final RegistryObject<FluidNonPlaceable> FLUID_STEAM = FLUIDS.register("steam", () -> new FluidNonPlaceable(() -> ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get(), NuclearScience.ID, "steam","steam", Color.WHITE));
	public static final RegistryObject<FluidNonPlaceable> FLUID_URANIUMHEXAFLUORIDE = FLUIDS.register("uraniumhexafluoride", () -> new FluidNonPlaceable(() -> NuclearScienceItems.ITEM_CANISTERLEAD.get(), NuclearScience.ID, "uraniumhexafluoride","uraniumhexafluoride", Color.WHITE));

}
