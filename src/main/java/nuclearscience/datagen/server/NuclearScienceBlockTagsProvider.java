package nuclearscience.datagen.server;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceBlockTagsProvider extends BlockTagsProvider {

	public NuclearScienceBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(NuclearScienceBlocks.blockAtomicAssembler, NuclearScienceBlocks.blockChemicalExtractor, NuclearScienceBlocks.blockControlRodAssembly, NuclearScienceBlocks.blockElectromagnet, NuclearScienceBlocks.blockElectromagneticBooster, NuclearScienceBlocks.blockElectromagneticGlass, NuclearScienceBlocks.blockElectromagneticSwitch,
				NuclearScienceBlocks.blockFreezePlug, NuclearScienceBlocks.blockFuelReprocessor, NuclearScienceBlocks.blockFusionReactorCore, NuclearScienceBlocks.blockGasCentrifuge, NuclearScienceBlocks.blockHeatExchanger, NuclearScienceBlocks.blocklead, NuclearScienceBlocks.blockMeltedReactor, NuclearScienceBlocks.blockMoltenSaltSupplier, NuclearScienceBlocks.blockMSRFuelPreProcessor,
				NuclearScienceBlocks.blockMsrReactorCore, NuclearScienceBlocks.blockNuclearBoiler, NuclearScienceBlocks.blockParticleInjector, NuclearScienceBlocks.blockQuantumCapacitor, NuclearScienceBlocks.blockRadioactiveProcessor, NuclearScienceBlocks.blockRadioisotopeGenerator, NuclearScienceBlocks.blockReactorCore, NuclearScienceBlocks.blockSiren, NuclearScienceBlocks.blockTeleporter,
				NuclearScienceBlocks.blockTurbine);
		
		tag(BlockTags.NEEDS_STONE_TOOL).add(NuclearScienceBlocks.blockAtomicAssembler, NuclearScienceBlocks.blockChemicalExtractor, NuclearScienceBlocks.blockControlRodAssembly, NuclearScienceBlocks.blockElectromagnet, NuclearScienceBlocks.blockElectromagneticBooster, NuclearScienceBlocks.blockElectromagneticGlass, NuclearScienceBlocks.blockElectromagneticSwitch,
				NuclearScienceBlocks.blockFreezePlug, NuclearScienceBlocks.blockFuelReprocessor, NuclearScienceBlocks.blockFusionReactorCore, NuclearScienceBlocks.blockGasCentrifuge, NuclearScienceBlocks.blockHeatExchanger, NuclearScienceBlocks.blocklead, NuclearScienceBlocks.blockMeltedReactor, NuclearScienceBlocks.blockMoltenSaltSupplier, NuclearScienceBlocks.blockMSRFuelPreProcessor,
				NuclearScienceBlocks.blockMsrReactorCore, NuclearScienceBlocks.blockNuclearBoiler, NuclearScienceBlocks.blockParticleInjector, NuclearScienceBlocks.blockQuantumCapacitor, NuclearScienceBlocks.blockRadioactiveProcessor, NuclearScienceBlocks.blockRadioisotopeGenerator, NuclearScienceBlocks.blockReactorCore, NuclearScienceBlocks.blockSiren, NuclearScienceBlocks.blockTeleporter,
				NuclearScienceBlocks.blockTurbine);
		
		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(NuclearScienceBlocks.blockRadioactiveSoil);
		
		tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(NuclearScienceBlocks.blockRadioactiveSoil);
		

	}

}
