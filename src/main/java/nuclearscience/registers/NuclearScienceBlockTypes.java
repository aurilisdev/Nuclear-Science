package nuclearscience.registers;

import static nuclearscience.registers.NuclearScienceBlocks.blockAtomicAssembler;
import static nuclearscience.registers.NuclearScienceBlocks.blockChemicalExtractor;
import static nuclearscience.registers.NuclearScienceBlocks.blockControlRodAssembly;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticSwitch;
import static nuclearscience.registers.NuclearScienceBlocks.blockFreezePlug;
import static nuclearscience.registers.NuclearScienceBlocks.blockFuelReprocessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockFusionReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockGasCentrifuge;
import static nuclearscience.registers.NuclearScienceBlocks.blockHeatExchanger;
import static nuclearscience.registers.NuclearScienceBlocks.blockMSRFuelPreProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMeltedReactor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMoltenSaltSupplier;
import static nuclearscience.registers.NuclearScienceBlocks.blockMSReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockNuclearBoiler;
import static nuclearscience.registers.NuclearScienceBlocks.blockParticleInjector;
import static nuclearscience.registers.NuclearScienceBlocks.blockPlasma;
import static nuclearscience.registers.NuclearScienceBlocks.blockQuantumCapacitor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioactiveProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioisotopeGenerator;
import static nuclearscience.registers.NuclearScienceBlocks.blockFissionReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockSiren;
import static nuclearscience.registers.NuclearScienceBlocks.blockTeleporter;
import static nuclearscience.registers.NuclearScienceBlocks.blockTurbine;

import com.google.common.collect.Sets;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileControlRodAssembly;
import nuclearscience.common.tile.TileElectromagneticSwitch;
import nuclearscience.common.tile.TileFuelReprocessor;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.common.tile.TileTurbine;
import nuclearscience.common.tile.fissionreactor.TileMeltedReactor;
import nuclearscience.common.tile.fusionreactor.TileFusionReactorCore;
import nuclearscience.common.tile.fusionreactor.TilePlasma;
import nuclearscience.common.tile.msreactor.TileFreezePlug;
import nuclearscience.common.tile.msreactor.TileHeatExchanger;
import nuclearscience.common.tile.msreactor.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;
import nuclearscience.common.tile.msreactor.TileMoltenSaltSupplier;
import nuclearscience.common.tile.saltpipe.TileMoltenSaltPipe;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;

public class NuclearScienceBlockTypes {
	public static final DeferredRegister<TileEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);

	public static final RegistryObject<TileEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = BLOCK_ENTITY_TYPES.register("gascentrifuge", () -> new TileEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(blockGasCentrifuge), null));
	public static final RegistryObject<TileEntityType<TileNuclearBoiler>> TILE_CHEMICALBOILER = BLOCK_ENTITY_TYPES.register("nuclearboiler", () -> new TileEntityType<>(TileNuclearBoiler::new, Sets.newHashSet(blockNuclearBoiler), null));
	public static final RegistryObject<TileEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = BLOCK_ENTITY_TYPES.register("chemicalextractor", () -> new TileEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(blockChemicalExtractor), null));
	public static final RegistryObject<TileEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = BLOCK_ENTITY_TYPES.register("radioisotopegenerator", () -> new TileEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(blockRadioisotopeGenerator), null));
	public static final RegistryObject<TileEntityType<TileMoltenSaltSupplier>> TILE_MOLTENSALTSUPPLIER = BLOCK_ENTITY_TYPES.register("moltensaltsupplier", () -> new TileEntityType<>(TileMoltenSaltSupplier::new, Sets.newHashSet(blockMoltenSaltSupplier), null));
	public static final RegistryObject<TileEntityType<TileFreezePlug>> TILE_FREEZEPLUG = BLOCK_ENTITY_TYPES.register("freezeplug", () -> new TileEntityType<>(TileFreezePlug::new, Sets.newHashSet(blockFreezePlug), null));
	public static final RegistryObject<TileEntityType<TileTurbine>> TILE_TURBINE = BLOCK_ENTITY_TYPES.register("turbine", () -> new TileEntityType<>(TileTurbine::new, Sets.newHashSet(blockTurbine), null));
	public static final RegistryObject<TileEntityType<TileFissionReactorCore>> TILE_REACTORCORE = BLOCK_ENTITY_TYPES.register("reactorcore", () -> new TileEntityType<>(TileFissionReactorCore::new, Sets.newHashSet(blockFissionReactorCore), null));
	public static final RegistryObject<TileEntityType<TileFusionReactorCore>> TILE_FUSIONREACTORCORE = BLOCK_ENTITY_TYPES.register("fusionreactorcore", () -> new TileEntityType<>(TileFusionReactorCore::new, Sets.newHashSet(blockFusionReactorCore), null));
	public static final RegistryObject<TileEntityType<TileParticleInjector>> TILE_PARTICLEINJECTOR = BLOCK_ENTITY_TYPES.register("particleinjector", () -> new TileEntityType<>(TileParticleInjector::new, Sets.newHashSet(blockParticleInjector), null));
	public static final RegistryObject<TileEntityType<TileElectromagneticSwitch>> TILE_ELECTROMAGNETICSWITCH = BLOCK_ENTITY_TYPES.register("electromagneticswitch", () -> new TileEntityType<>(TileElectromagneticSwitch::new, Sets.newHashSet(blockElectromagneticSwitch), null));
	public static final RegistryObject<TileEntityType<TilePlasma>> TILE_PLASMA = BLOCK_ENTITY_TYPES.register("plasma", () -> new TileEntityType<>(TilePlasma::new, Sets.newHashSet(blockPlasma), null));
	public static final RegistryObject<TileEntityType<TileMeltedReactor>> TILE_MELTEDREACTOR = BLOCK_ENTITY_TYPES.register("meltedreactor", () -> new TileEntityType<>(TileMeltedReactor::new, Sets.newHashSet(blockMeltedReactor), null));
	public static final RegistryObject<TileEntityType<TileQuantumCapacitor>> TILE_QUANTUMCAPACITOR = BLOCK_ENTITY_TYPES.register("quantumcapacitor", () -> new TileEntityType<>(TileQuantumCapacitor::new, Sets.newHashSet(blockQuantumCapacitor), null));
	public static final RegistryObject<TileEntityType<TileFuelReprocessor>> TILE_FUELREPROCESSOR = BLOCK_ENTITY_TYPES.register("fuelreprocessor", () -> new TileEntityType<>(TileFuelReprocessor::new, Sets.newHashSet(blockFuelReprocessor), null));
	public static final RegistryObject<TileEntityType<TileRadioactiveProcessor>> TILE_RADIOACTIVEPROCESSOR = BLOCK_ENTITY_TYPES.register("radioactiveprocessor", () -> new TileEntityType<>(TileRadioactiveProcessor::new, Sets.newHashSet(blockRadioactiveProcessor), null));
	public static final RegistryObject<TileEntityType<TileMSRFuelPreProcessor>> TILE_MSRFUELPREPROCESSOR = BLOCK_ENTITY_TYPES.register("msrfuelpreprocessor", () -> new TileEntityType<>(TileMSRFuelPreProcessor::new, Sets.newHashSet(blockMSRFuelPreProcessor), null));
	public static final RegistryObject<TileEntityType<TileMSReactorCore>> TILE_MSRREACTORCORE = BLOCK_ENTITY_TYPES.register("msrreactorcore", () -> new TileEntityType<>(TileMSReactorCore::new, Sets.newHashSet(blockMSReactorCore), null));
	public static final RegistryObject<TileEntityType<TileHeatExchanger>> TILE_HEATEXCHANGER = BLOCK_ENTITY_TYPES.register("heatexchanger", () -> new TileEntityType<>(TileHeatExchanger::new, Sets.newHashSet(blockHeatExchanger), null));
	public static final RegistryObject<TileEntityType<TileTeleporter>> TILE_TELEPORTER = BLOCK_ENTITY_TYPES.register("teleporter", () -> new TileEntityType<>(TileTeleporter::new, Sets.newHashSet(blockTeleporter), null));
	public static final RegistryObject<TileEntityType<TileControlRodAssembly>> TILE_CONTROLRODASSEMBLY = BLOCK_ENTITY_TYPES.register("controlrodassembly", () -> new TileEntityType<>(TileControlRodAssembly::new, Sets.newHashSet(blockControlRodAssembly), null));
	public static final RegistryObject<TileEntityType<TileMoltenSaltPipe>> TILE_MOLTENSALTPIPE = BLOCK_ENTITY_TYPES.register("moltensaltpipegenerictile", () -> new TileEntityType<>(TileMoltenSaltPipe::new, BlockMoltenSaltPipe.PIPESET, null));
	public static final RegistryObject<TileEntityType<TileSiren>> TILE_SIREN = BLOCK_ENTITY_TYPES.register("siren", () -> new TileEntityType<>(TileSiren::new, Sets.newHashSet(blockSiren), null));
	public static final RegistryObject<TileEntityType<TileAtomicAssembler>> TILE_ATOMICASSEMBLER = BLOCK_ENTITY_TYPES.register("atomicassembler", () -> new TileEntityType<>(TileAtomicAssembler::new, Sets.newHashSet(blockAtomicAssembler), null));

}
