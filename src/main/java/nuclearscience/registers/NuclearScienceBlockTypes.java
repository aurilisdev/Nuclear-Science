package nuclearscience.registers;

import static nuclearscience.registers.NuclearScienceBlocks.blockAtomicAssembler;
import static nuclearscience.registers.NuclearScienceBlocks.blockChemicalExtractor;
import static nuclearscience.registers.NuclearScienceBlocks.blockControlRodAssembly;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticSwitch;
import static nuclearscience.registers.NuclearScienceBlocks.blockFissionReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockFreezePlug;
import static nuclearscience.registers.NuclearScienceBlocks.blockFuelReprocessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockFusionReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockGasCentrifuge;
import static nuclearscience.registers.NuclearScienceBlocks.blockHeatExchanger;
import static nuclearscience.registers.NuclearScienceBlocks.blockMSRFuelPreProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMSReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockMeltedReactor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMoltenSaltSupplier;
import static nuclearscience.registers.NuclearScienceBlocks.blockNuclearBoiler;
import static nuclearscience.registers.NuclearScienceBlocks.blockParticleInjector;
import static nuclearscience.registers.NuclearScienceBlocks.blockPlasma;
import static nuclearscience.registers.NuclearScienceBlocks.blockQuantumCapacitor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioactiveProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioisotopeGenerator;
import static nuclearscience.registers.NuclearScienceBlocks.blockSiren;
import static nuclearscience.registers.NuclearScienceBlocks.blockTeleporter;
import static nuclearscience.registers.NuclearScienceBlocks.blockTurbine;

import com.google.common.collect.Sets;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileControlRodAssembly;
import nuclearscience.common.tile.TileElectromagneticSwitch;
import nuclearscience.common.tile.TileFissionReactorCore;
import nuclearscience.common.tile.TileFreezePlug;
import nuclearscience.common.tile.TileFuelReprocessor;
import nuclearscience.common.tile.TileFusionReactorCore;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileHeatExchanger;
import nuclearscience.common.tile.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.TileMSReactorCore;
import nuclearscience.common.tile.TileMeltedReactor;
import nuclearscience.common.tile.TileMoltenSaltSupplier;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TilePlasma;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.TileSteamFunnel;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.common.tile.TileTurbine;
import nuclearscience.common.tile.network.TileMoltenSaltPipe;

public class NuclearScienceBlockTypes {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, References.ID);

	public static final RegistryObject<BlockEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = BLOCK_ENTITY_TYPES.register("gascentrifuge", () -> new BlockEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(blockGasCentrifuge), null));
	public static final RegistryObject<BlockEntityType<TileNuclearBoiler>> TILE_CHEMICALBOILER = BLOCK_ENTITY_TYPES.register("nuclearboiler", () -> new BlockEntityType<>(TileNuclearBoiler::new, Sets.newHashSet(blockNuclearBoiler), null));
	public static final RegistryObject<BlockEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = BLOCK_ENTITY_TYPES.register("chemicalextractor", () -> new BlockEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(blockChemicalExtractor), null));
	public static final RegistryObject<BlockEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = BLOCK_ENTITY_TYPES.register("radioisotopegenerator", () -> new BlockEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(blockRadioisotopeGenerator), null));
	public static final RegistryObject<BlockEntityType<TileMoltenSaltSupplier>> TILE_MOLTENSALTSUPPLIER = BLOCK_ENTITY_TYPES.register("moltensaltsupplier", () -> new BlockEntityType<>(TileMoltenSaltSupplier::new, Sets.newHashSet(blockMoltenSaltSupplier), null));
	public static final RegistryObject<BlockEntityType<TileFreezePlug>> TILE_FREEZEPLUG = BLOCK_ENTITY_TYPES.register("freezeplug", () -> new BlockEntityType<>(TileFreezePlug::new, Sets.newHashSet(blockFreezePlug), null));
	public static final RegistryObject<BlockEntityType<TileTurbine>> TILE_TURBINE = BLOCK_ENTITY_TYPES.register("turbine", () -> new BlockEntityType<>(TileTurbine::new, Sets.newHashSet(blockTurbine), null));
	public static final RegistryObject<BlockEntityType<TileFissionReactorCore>> TILE_REACTORCORE = BLOCK_ENTITY_TYPES.register("reactorcore", () -> new BlockEntityType<>(TileFissionReactorCore::new, Sets.newHashSet(blockFissionReactorCore), null));
	public static final RegistryObject<BlockEntityType<TileFusionReactorCore>> TILE_FUSIONREACTORCORE = BLOCK_ENTITY_TYPES.register("fusionreactorcore", () -> new BlockEntityType<>(TileFusionReactorCore::new, Sets.newHashSet(blockFusionReactorCore), null));
	public static final RegistryObject<BlockEntityType<TileParticleInjector>> TILE_PARTICLEINJECTOR = BLOCK_ENTITY_TYPES.register("particleinjector", () -> new BlockEntityType<>(TileParticleInjector::new, Sets.newHashSet(blockParticleInjector), null));
	public static final RegistryObject<BlockEntityType<TileElectromagneticSwitch>> TILE_ELECTROMAGNETICSWITCH = BLOCK_ENTITY_TYPES.register("electromagneticswitch", () -> new BlockEntityType<>(TileElectromagneticSwitch::new, Sets.newHashSet(blockElectromagneticSwitch), null));
	public static final RegistryObject<BlockEntityType<TilePlasma>> TILE_PLASMA = BLOCK_ENTITY_TYPES.register("plasma", () -> new BlockEntityType<>(TilePlasma::new, Sets.newHashSet(blockPlasma), null));
	public static final RegistryObject<BlockEntityType<TileMeltedReactor>> TILE_MELTEDREACTOR = BLOCK_ENTITY_TYPES.register("meltedreactor", () -> new BlockEntityType<>(TileMeltedReactor::new, Sets.newHashSet(blockMeltedReactor), null));
	public static final RegistryObject<BlockEntityType<TileQuantumCapacitor>> TILE_QUANTUMCAPACITOR = BLOCK_ENTITY_TYPES.register("quantumcapacitor", () -> new BlockEntityType<>(TileQuantumCapacitor::new, Sets.newHashSet(blockQuantumCapacitor), null));
	public static final RegistryObject<BlockEntityType<TileFuelReprocessor>> TILE_FUELREPROCESSOR = BLOCK_ENTITY_TYPES.register("fuelreprocessor", () -> new BlockEntityType<>(TileFuelReprocessor::new, Sets.newHashSet(blockFuelReprocessor), null));
	public static final RegistryObject<BlockEntityType<TileRadioactiveProcessor>> TILE_RADIOACTIVEPROCESSOR = BLOCK_ENTITY_TYPES.register("radioactiveprocessor", () -> new BlockEntityType<>(TileRadioactiveProcessor::new, Sets.newHashSet(blockRadioactiveProcessor), null));
	public static final RegistryObject<BlockEntityType<TileMSRFuelPreProcessor>> TILE_MSRFUELPREPROCESSOR = BLOCK_ENTITY_TYPES.register("msrfuelpreprocessor", () -> new BlockEntityType<>(TileMSRFuelPreProcessor::new, Sets.newHashSet(blockMSRFuelPreProcessor), null));
	public static final RegistryObject<BlockEntityType<TileMSReactorCore>> TILE_MSRREACTORCORE = BLOCK_ENTITY_TYPES.register("msrreactorcore", () -> new BlockEntityType<>(TileMSReactorCore::new, Sets.newHashSet(blockMSReactorCore), null));
	public static final RegistryObject<BlockEntityType<TileHeatExchanger>> TILE_HEATEXCHANGER = BLOCK_ENTITY_TYPES.register("heatexchanger", () -> new BlockEntityType<>(TileHeatExchanger::new, Sets.newHashSet(blockHeatExchanger), null));
	public static final RegistryObject<BlockEntityType<TileTeleporter>> TILE_TELEPORTER = BLOCK_ENTITY_TYPES.register("teleporter", () -> new BlockEntityType<>(TileTeleporter::new, Sets.newHashSet(blockTeleporter), null));
	public static final RegistryObject<BlockEntityType<TileControlRodAssembly>> TILE_CONTROLRODASSEMBLY = BLOCK_ENTITY_TYPES.register("controlrodassembly", () -> new BlockEntityType<>(TileControlRodAssembly::new, Sets.newHashSet(blockControlRodAssembly), null));
	public static final RegistryObject<BlockEntityType<TileMoltenSaltPipe>> TILE_MOLTENSALTPIPE = BLOCK_ENTITY_TYPES.register("moltensaltpipegenerictile", () -> new BlockEntityType<>(TileMoltenSaltPipe::new, BlockMoltenSaltPipe.PIPESET, null));
	public static final RegistryObject<BlockEntityType<TileSiren>> TILE_SIREN = BLOCK_ENTITY_TYPES.register("siren", () -> new BlockEntityType<>(TileSiren::new, Sets.newHashSet(blockSiren), null));
	public static final RegistryObject<BlockEntityType<TileAtomicAssembler>> TILE_ATOMICASSEMBLER = BLOCK_ENTITY_TYPES.register("atomicassembler", () -> new BlockEntityType<>(TileAtomicAssembler::new, Sets.newHashSet(blockAtomicAssembler), null));

	public static final RegistryObject<BlockEntityType<TileSteamFunnel>> TILE_STEAMFUNNEL = BLOCK_ENTITY_TYPES.register("steamfunnel", () -> new BlockEntityType<>(TileSteamFunnel::new, Sets.newHashSet(NuclearScienceBlocks.blockSteamFunnel), null));

}
