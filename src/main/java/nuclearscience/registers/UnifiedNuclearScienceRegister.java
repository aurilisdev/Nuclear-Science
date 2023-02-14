package nuclearscience.registers;

import static nuclearscience.registers.NuclearScienceBlocks.SUBTYPEBLOCKREGISTER_MAPPINGS;

import electrodynamics.api.ISubtype;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import nuclearscience.prefab.utils.TextUtils;

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
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockGasCentrifuge, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockNuclearBoiler, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockChemicalExtractor, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockParticleInjector, TextUtils.tooltip("machine.voltage.960"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockTeleporter, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockFuelReprocessor, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockRadioactiveProcessor, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockMSRFuelPreProcessor, TextUtils.tooltip("machine.voltage.240"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockMoltenSaltSupplier, TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockFusionReactorCore, TextUtils.tooltip("machine.voltage.480"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockFreezePlug, TextUtils.tooltip("machine.voltage.120"));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockAtomicAssembler, TextUtils.tooltip("machine.voltage.480"));

		// Generators
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockRadioisotopeGenerator, TextUtils.tooltip("machine.voltage.120"));

		// Misc
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockQuantumCapacitor, TextUtils.tooltip("machine.voltage.1920"));
	}

	public static Block getSafeBlock(ISubtype type) {
		return SUBTYPEBLOCKREGISTER_MAPPINGS.get(type).get();
	}
}
