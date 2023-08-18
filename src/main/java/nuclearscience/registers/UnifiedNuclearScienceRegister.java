package nuclearscience.registers;

import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import nuclearscience.prefab.utils.NuclearTextUtils;

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
		NuclearScienceGases.GASES.register(bus);
	}

	static {
		// Machines
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockGasCentrifuge, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockNuclearBoiler, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockChemicalExtractor, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockParticleInjector, ElectroTextUtils.voltageTooltip(960));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockTeleporter, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockFuelReprocessor, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockRadioactiveProcessor, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockMSRFuelPreProcessor, ElectroTextUtils.voltageTooltip(240));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockMoltenSaltSupplier, ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockFusionReactorCore, ElectroTextUtils.voltageTooltip(480));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockFreezePlug, ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockAtomicAssembler, ElectroTextUtils.voltageTooltip(480));

		// Generators
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockRadioisotopeGenerator, ElectroTextUtils.voltageTooltip(120));

		// Misc
		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockQuantumCapacitor, ElectroTextUtils.voltageTooltip(1920));

		BlockItemDescriptable.addDescription(() -> NuclearScienceBlocks.blockSteamFunnel, NuclearTextUtils.tooltip("steamfunneluse"));
	}

}
