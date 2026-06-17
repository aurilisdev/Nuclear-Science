package nuclearscience.datagen.server;

import java.util.List;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.datagen.utils.server.loottable.BaseLootTablesProvider;

public class NuclearScienceLootTablesProvider extends BaseLootTablesProvider {

    public NuclearScienceLootTablesProvider() {
	super(NuclearScience.ID);
    }

    @Override
    protected void generate() {

	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler),
		NuclearScienceTiles.TILE_ATOMICASSEMBLER, true, false, false, true, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor),
		NuclearScienceTiles.TILE_CHEMICALEXTRACTOR, true, true, false, true, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagnet));
	addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get());
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagneticglass));
	addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get());
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug),
		NuclearScienceTiles.TILE_FREEZEPLUG, true, false, false, false, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor),
		NuclearScienceTiles.TILE_FUELREPROCESSOR, true, false, false, true, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore));
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge),
		NuclearScienceTiles.TILE_GASCENTRIFUGE, true, true, false, true, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base));
	add(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door),
		createSinglePropConditionTable(
			NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door),
			DoorBlock.HALF, DoubleBlockHalf.LOWER));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass));
	addSimpleBlock(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get());
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier),
		NuclearScienceTiles.TILE_MOLTENSALTSUPPLIER, true, false, false, false, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor),
		NuclearScienceTiles.TILE_MSRFUELPREPROCESSOR, true, true, false, true, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore));
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler),
		NuclearScienceTiles.TILE_CHEMICALBOILER, true, true, false, true, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector),
		NuclearScienceTiles.TILE_PARTICLEINJECTOR, true, false, false, true, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor),
		NuclearScienceTiles.TILE_QUANTUMCAPACITOR, false, false, false, true, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor),
		NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR, true, true, false, false, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood));
	add(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass), createSilkTouchOnlyTable(
		NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass)));
	addMachineTable(
		NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator),
		NuclearScienceTiles.TILE_RADIOISOTOPEGENERATOR, true, false, false, false, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore),
		NuclearScienceTiles.TILE_REACTORCORE, true, false, false, false, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.siren));
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter),
		NuclearScienceTiles.TILE_TELEPORTER, false, false, false, true, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCK_TURBINE.get());
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod));
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber),
		NuclearScienceTiles.TILE_CLOUDCHAMBER, false, true, false, true, false);
	addMachineTable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber),
		NuclearScienceTiles.TILE_FALLOUTSCRUBBER, false, true, false, true, false);
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule));
	addSimpleBlock(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule));
	addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY.get());
	addSimpleBlock(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE.get());

    }

    @Override
    public List<Block> getExcludedBlocks() {
	return List.of(NuclearScienceBlocks.BLOCK_PLASMA.get(), NuclearScienceBlocks.BLOCK_RADIOACTIVEAIR.get());
    }

}
