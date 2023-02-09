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
import electrodynamics.prefab.utilities.TextUtils;
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
		BlockItemDescriptable.addDescription(() -> blockGasCentrifuge, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> blockNuclearBoiler, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> blockChemicalExtractor, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> blockParticleInjector, TextUtils.tooltip("machine.voltage.960"));
		BlockItemDescriptable.addDescription(() -> blockTeleporter, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> blockFuelReprocessor, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> blockRadioactiveProcessor, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> blockMSRFuelPreProcessor, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> blockMoltenSaltSupplier, TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> blockFusionReactorCore, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> blockFreezePlug, TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> blockAtomicAssembler, TextUtils.tooltip("machine.voltage.480"));

		// Generators
		BlockItemDescriptable.addDescription(() -> blockRadioisotopeGenerator, TextUtils.tooltip("machine.voltage.120"));

		// Misc
		BlockItemDescriptable.addDescription(() -> blockQuantumCapacitor, TextUtils.tooltip("machine.voltage.1920"));
	}

	public static Block getSafeBlock(ISubtype type) {
		return SUBTYPEBLOCKREGISTER_MAPPINGS.get(type).get();
	}
}
