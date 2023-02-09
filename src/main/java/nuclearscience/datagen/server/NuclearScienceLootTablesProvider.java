package nuclearscience.datagen.server;

import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import net.minecraft.data.DataGenerator;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceLootTablesProvider extends ElectrodynamicsLootTablesProvider {

	public NuclearScienceLootTablesProvider(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void addTables() {

		addIETable(NuclearScienceBlocks.blockAtomicAssembler, NuclearScienceBlockTypes.TILE_ATOMICASSEMBLER);
		addIEFTable(NuclearScienceBlocks.blockChemicalExtractor, NuclearScienceBlockTypes.TILE_CHEMICALEXTRACTOR);
		addSimpleBlock(NuclearScienceBlocks.blockControlRodAssembly);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagnet);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagneticBooster);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagneticGlass);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagneticSwitch);
		addIETable(NuclearScienceBlocks.blockFreezePlug, NuclearScienceBlockTypes.TILE_FREEZEPLUG);
		addIETable(NuclearScienceBlocks.blockFuelReprocessor, NuclearScienceBlockTypes.TILE_FUELREPROCESSOR);
		addSimpleBlock(NuclearScienceBlocks.blockFusionReactorCore);
		addIEFTable(NuclearScienceBlocks.blockGasCentrifuge, NuclearScienceBlockTypes.TILE_GASCENTRIFUGE);
		addSimpleBlock(NuclearScienceBlocks.blockHeatExchanger);
		addSimpleBlock(NuclearScienceBlocks.blocklead);
		addSimpleBlock(NuclearScienceBlocks.blockMeltedReactor);
		addIETable(NuclearScienceBlocks.blockMoltenSaltSupplier, NuclearScienceBlockTypes.TILE_MOLTENSALTSUPPLIER);
		addIEFTable(NuclearScienceBlocks.blockMSRFuelPreProcessor, NuclearScienceBlockTypes.TILE_MSRFUELPREPROCESSOR);
		addSimpleBlock(NuclearScienceBlocks.blockMsrReactorCore);
		addIEFTable(NuclearScienceBlocks.blockNuclearBoiler, NuclearScienceBlockTypes.TILE_CHEMICALBOILER);
		addIETable(NuclearScienceBlocks.blockParticleInjector, NuclearScienceBlockTypes.TILE_PARTICLEINJECTOR);
		addETable(NuclearScienceBlocks.blockQuantumCapacitor, NuclearScienceBlockTypes.TILE_QUANTUMCAPACITOR);
		addIEFTable(NuclearScienceBlocks.blockRadioactiveProcessor, NuclearScienceBlockTypes.TILE_RADIOACTIVEPROCESSOR);
		addSimpleBlock(NuclearScienceBlocks.blockRadioactiveSoil);
		addITable(NuclearScienceBlocks.blockRadioisotopeGenerator, NuclearScienceBlockTypes.TILE_RADIOISOTOPEGENERATOR);
		addITable(NuclearScienceBlocks.blockReactorCore, NuclearScienceBlockTypes.TILE_REACTORCORE);
		addSimpleBlock(NuclearScienceBlocks.blockSiren);
		addETable(NuclearScienceBlocks.blockTeleporter, NuclearScienceBlockTypes.TILE_TELEPORTER);
		addSimpleBlock(NuclearScienceBlocks.blockTurbine);
		addSimpleBlock(NuclearScienceBlocks.getBlock(SubtypeMoltenSaltPipe.vanadiumsteelceramic));

	}

}
