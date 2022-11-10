package nuclearscience.registers;

import static nuclearscience.registers.NuclearScienceBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS;
import static nuclearscience.registers.NuclearScienceBlocks.blockAtomicAssembler;
import static nuclearscience.registers.NuclearScienceBlocks.blockChemicalExtractor;
import static nuclearscience.registers.NuclearScienceBlocks.blockFreezePlug;
import static nuclearscience.registers.NuclearScienceBlocks.blockFuelReprocessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockFusionReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockGasCentrifuge;
import static nuclearscience.registers.NuclearScienceBlocks.blockMSRFuelPreProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMoltenSaltSupplier;
import static nuclearscience.registers.NuclearScienceBlocks.blockNuclearBoiler;
import static nuclearscience.registers.NuclearScienceBlocks.blockParticleInjector;
import static nuclearscience.registers.NuclearScienceBlocks.blockQuantumCapacitor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioactiveProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioisotopeGenerator;
import static nuclearscience.registers.NuclearScienceBlocks.blockTeleporter;

import electrodynamics.api.ISubtype;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;

public class UnifiedNuclearScienceRegister {
	public static void register(IEventBus bus) {
		NuclearScienceBlocks.BLOCKS.register(bus);
		NuclearScienceItems.ITEMS.register(bus);
		NuclearScienceBlockTypes.BLOCK_ENTITY_TYPES.register(bus);
		NuclearScienceMenuTypes.MENU_TYPES.register(bus);
		NuclearScienceFluids.FLUIDS.register(bus);
		NuclearScienceFluidTypes.FLUID_TYPES.register(bus);
		NuclearScienceEntities.ENTITIES.register(bus);
		NuclearScienceSounds.SOUNDS.register(bus);
	}

	static {
		// Machines
		BlockItemDescriptable.addDescription(() -> blockGasCentrifuge, "|translate|tooltip.voltage.240");
		BlockItemDescriptable.addDescription(() -> blockNuclearBoiler, "|translate|tooltip.voltage.240");
		BlockItemDescriptable.addDescription(() -> blockChemicalExtractor, "|translate|tooltip.voltage.240");
		BlockItemDescriptable.addDescription(() -> blockParticleInjector, "|translate|tooltip.voltage.960");
		BlockItemDescriptable.addDescription(() -> blockTeleporter, "|translate|tooltip.voltage.480");
		BlockItemDescriptable.addDescription(() -> blockFuelReprocessor, "|translate|tooltip.voltage.480");
		BlockItemDescriptable.addDescription(() -> blockRadioactiveProcessor, "|translate|tooltip.voltage.480");
		BlockItemDescriptable.addDescription(() -> blockMSRFuelPreProcessor, "|translate|tooltip.voltage.240");
		BlockItemDescriptable.addDescription(() -> blockMoltenSaltSupplier, "|translate|tooltip.voltage.120");
		BlockItemDescriptable.addDescription(() -> blockFusionReactorCore, "|translate|tooltip.voltage.480");
		BlockItemDescriptable.addDescription(() -> blockFreezePlug, "|translate|tooltip.voltage.120");
		BlockItemDescriptable.addDescription(() -> blockAtomicAssembler, "|translate|tooltip.voltage.480");

		// Generators
		BlockItemDescriptable.addDescription(() -> blockRadioisotopeGenerator, "|translate|tooltip.voltage.120");

		// Misc
		BlockItemDescriptable.addDescription(() -> blockQuantumCapacitor, "|translate|tooltip.voltage.1920");
	}

	public static Block getSafeBlock(ISubtype type) {
		return SUBTYPEBLOCKREGISTER_MAPPINGS.get(type).get();
	}
}
