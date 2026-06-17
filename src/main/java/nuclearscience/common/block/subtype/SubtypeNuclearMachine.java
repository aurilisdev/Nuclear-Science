package nuclearscience.common.block.subtype;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import nuclearscience.common.block.voxelshapes.NuclearScienceVoxelShapes;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileChunkloader;
import nuclearscience.common.tile.TileCloudChamber;
import nuclearscience.common.tile.TileFalloutScrubber;
import nuclearscience.common.tile.TileFuelReprocessor;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.TileSteamFunnel;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.common.tile.accelerator.TileParticleInjector;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.fusion.TileFusionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileController;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFissionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileFusionInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.TileMSInterface;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileHeatExchanger;
import nuclearscience.common.tile.reactor.moltensalt.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltSupplier;
import voltaic.api.ISubtype;
import voltaic.api.multiblock.subnodebased.parent.IMultiblockParentBlock;
import voltaic.api.tile.IMachine;
import voltaic.api.tile.MachineProperties;
import voltaic.common.block.voxelshapes.VoxelShapeProvider;

public enum SubtypeNuclearMachine implements ISubtype, IMachine {

    gascentrifuge(true, TileGasCentrifuge::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.GAS_CENTRIFUGE)),
    nuclearboiler(true, TileNuclearBoiler::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.NUCLEAR_BOILER)),
    chemicalextractor(true, TileChemicalExtractor::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.CHEMICAL_EXTRACTOR)),
    fuelreprocessor(true, TileFuelReprocessor::new,
	    MachineProperties.builder().setLitBrightness(15)
		    .setShapeProvider(NuclearScienceVoxelShapes.FUEL_REPROCESSOR)),
    radioactiveprocessor(true, TileRadioactiveProcessor::new, MachineProperties.builder().setLitBrightness(15)),
    cloudchamber(true, TileCloudChamber::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.CLOUD_CHAMBER)),
    falloutscrubber(true, TileFalloutScrubber::new),
    radioisotopegenerator(true, TileRadioisotopeGenerator::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.RADIOISOTROPIC_GENERATOR)),
    fissionreactorcore(true, TileFissionReactorCore::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.FISSION_REACTOR_CORE)),
    fissioncontrolrod(true, TileControlRod.TileFissionControlRod::new), siren(true, TileSiren::new),
    steamfunnel(true, TileSteamFunnel::new),
    msrfuelpreprocessor(true, TileMSRFuelPreProcessor::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.MSR_FUEL_PREPROCESSOR)),
    freezeplug(true, TileFreezePlug::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.FREEZE_PLUG)),
    msreactorcore(true, TileMSReactorCore::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.MS_REACTOR_CORE)),
    heatexchanger(true, TileHeatExchanger::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.HEAT_EXCHANGER)),
    moltensaltsupplier(true, TileMoltenSaltSupplier::new,
	    MachineProperties.builder().setLitBrightness(15)
		    .setShapeProvider(NuclearScienceVoxelShapes.MOLTEN_SALT_SUPPLIER)),
    mscontrolrod(true, TileControlRod.TileMSControlRod::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.MS_CONTROL_ROD)),
    fusionreactorcore(true, TileFusionReactorCore::new, MachineProperties.builder().setLitBrightness(15)),
    particleinjector(true, TileParticleInjector::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.PARTICLE_INJECTOR)),
    logisticscontroller(true, TileController::new, MachineProperties.builder().setLitBrightness(15)),
    fissioninterface(true, TileFissionInterface::new),
    msinterface(true, TileMSInterface::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.MS_INTERFACE)),
    fusioninterface(true, TileFusionInterface::new),
    controlrodmodule(true, TileControlRodModule::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.CONTROL_ROD_MODULE)),
    supplymodule(true, TileSupplyModule::new, MachineProperties.builder().setUsesLit()),
    monitormodule(true, TileMonitorModule::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.MONITOR_MODULE).setUsesLit()),
    thermometermodule(true, TileThermometerModule::new, MachineProperties.builder().setUsesLit()),

    teleporter(true, TileTeleporter::new,
	    MachineProperties.builder().setShapeProvider(NuclearScienceVoxelShapes.TELEPORTER).setLitBrightness(15)),
    chunkloader(true, TileChunkloader::new), atomicassembler(true, TileAtomicAssembler::new),
    quantumcapacitor(true, TileQuantumTunnel::new);

    private final BlockEntityType.BlockEntitySupplier<BlockEntity> blockEntitySupplier;
    private final boolean showInItemGroup;
    private final MachineProperties properties;

    private SubtypeNuclearMachine(boolean showInItemGroup,
	    BlockEntityType.BlockEntitySupplier<BlockEntity> blockEntitySupplier) {
	this(showInItemGroup, blockEntitySupplier, MachineProperties.DEFAULT);
    }

    private SubtypeNuclearMachine(boolean showInItemGroup,
	    BlockEntityType.BlockEntitySupplier<BlockEntity> blockEntitySupplier, MachineProperties properties) {
	this.showInItemGroup = showInItemGroup;
	this.blockEntitySupplier = blockEntitySupplier;
	this.properties = properties;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<BlockEntity> getBlockEntitySupplier() {
	return this.blockEntitySupplier;
    }

    @Override
    public int getLitBrightness() {
	return this.properties.litBrightness;
    }

    @Override
    public RenderShape getRenderShape() {
	return this.properties.renderShape;
    }

    @Override
    public boolean isMultiblock() {
	return this.properties.isMultiblock;
    }

    @Override
    public boolean propegatesLightDown() {
	return this.properties.propegatesLightDown;
    }

    @Override
    public String tag() {
	return this.name();
    }

    @Override
    public String forgeTag() {
	return this.tag();
    }

    @Override
    public boolean isItem() {
	return false;
    }

    @Override
    public boolean isPlayerStorable() {
	return false;
    }

    @Override
    public IMultiblockParentBlock.SubnodeWrapper getSubnodes() {
	return this.properties.wrapper;
    }

    @Override
    public VoxelShapeProvider getVoxelShapeProvider() {
	return this.properties.provider;
    }

    @Override
    public boolean usesLit() {
	return properties.usesLit;
    }

    public boolean showInItemGroup() {
	return this.showInItemGroup;
    }
}
