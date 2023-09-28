package nuclearscience.datagen.server;

import java.util.List;

import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import net.minecraft.world.level.block.Block;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceLootTablesProvider extends ElectrodynamicsLootTablesProvider {

	public NuclearScienceLootTablesProvider() {
		super(References.ID);
	}

	@Override
	protected void generate() {

		addMachineTable(NuclearScienceBlocks.blockAtomicAssembler, NuclearScienceBlockTypes.TILE_ATOMICASSEMBLER, true, false, false, true, false);
		addMachineTable(NuclearScienceBlocks.blockChemicalExtractor, NuclearScienceBlockTypes.TILE_CHEMICALEXTRACTOR, true, true, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.blockControlRodAssembly);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagnet);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagneticBooster);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagneticGlass);
		addSimpleBlock(NuclearScienceBlocks.blockElectromagneticSwitch);
		addMachineTable(NuclearScienceBlocks.blockFreezePlug, NuclearScienceBlockTypes.TILE_FREEZEPLUG, true, false, false, false, false);
		addMachineTable(NuclearScienceBlocks.blockFuelReprocessor, NuclearScienceBlockTypes.TILE_FUELREPROCESSOR, true, false, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.blockFusionReactorCore);
		addMachineTable(NuclearScienceBlocks.blockGasCentrifuge, NuclearScienceBlockTypes.TILE_GASCENTRIFUGE, true, true, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.blockHeatExchanger);
		addSimpleBlock(NuclearScienceBlocks.blocklead);
		addSimpleBlock(NuclearScienceBlocks.blockMeltedReactor);
		addMachineTable(NuclearScienceBlocks.blockMoltenSaltSupplier, NuclearScienceBlockTypes.TILE_MOLTENSALTSUPPLIER, true, false, false, false, false);
		addMachineTable(NuclearScienceBlocks.blockMSRFuelPreProcessor, NuclearScienceBlockTypes.TILE_MSRFUELPREPROCESSOR, true, true, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.blockMSReactorCore);
		addMachineTable(NuclearScienceBlocks.blockNuclearBoiler, NuclearScienceBlockTypes.TILE_CHEMICALBOILER, true, true, false, true, false);
		addMachineTable(NuclearScienceBlocks.blockParticleInjector, NuclearScienceBlockTypes.TILE_PARTICLEINJECTOR, true, false, false, true, false);
		addMachineTable(NuclearScienceBlocks.blockQuantumCapacitor, NuclearScienceBlockTypes.TILE_QUANTUMCAPACITOR, false, false, false, true, false);
		addMachineTable(NuclearScienceBlocks.blockRadioactiveProcessor, NuclearScienceBlockTypes.TILE_RADIOACTIVEPROCESSOR, true, true, false, false, false);
		addSimpleBlock(NuclearScienceBlocks.blockRadioactiveSoil);
		addMachineTable(NuclearScienceBlocks.blockRadioisotopeGenerator, NuclearScienceBlockTypes.TILE_RADIOISOTOPEGENERATOR, true, false, false, false, false);
		addMachineTable(NuclearScienceBlocks.blockFissionReactorCore, NuclearScienceBlockTypes.TILE_REACTORCORE, true, false, false, false, false);
		addSimpleBlock(NuclearScienceBlocks.blockSiren);
		addMachineTable(NuclearScienceBlocks.blockTeleporter, NuclearScienceBlockTypes.TILE_TELEPORTER, false, false, false, true, false);
		addSimpleBlock(NuclearScienceBlocks.blockTurbine);
		addSimpleBlock(NuclearScienceBlocks.getBlock(SubtypeMoltenSaltPipe.vanadiumsteelceramic));
		addSimpleBlock(NuclearScienceBlocks.blockSteamFunnel);

	}

	@Override
	public List<Block> getExcludedBlocks() {
		return List.of(NuclearScienceBlocks.blockPlasma, NuclearScienceBlocks.blockRadioactiveAir);
	}

}
