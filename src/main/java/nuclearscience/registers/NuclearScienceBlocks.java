package nuclearscience.registers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.block.BlockControlRodAssembly;
import nuclearscience.common.block.BlockElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticSwitch;
import nuclearscience.common.block.BlockFissionReactorCore;
import nuclearscience.common.block.BlockFuelReprocessor;
import nuclearscience.common.block.BlockFusionReactorCore;
import nuclearscience.common.block.BlockMSRFuelPreprocessor;
import nuclearscience.common.block.BlockMeltedReactor;
import nuclearscience.common.block.BlockMoltenSaltSupplier;
import nuclearscience.common.block.BlockPlasma;
import nuclearscience.common.block.BlockQuantumCapacitor;
import nuclearscience.common.block.BlockRadioactiveAir;
import nuclearscience.common.block.BlockRadioactiveProcessor;
import nuclearscience.common.block.BlockRadioactiveSoil;
import nuclearscience.common.block.BlockTeleporter;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileFreezePlug;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileHeatExchanger;
import nuclearscience.common.tile.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.TileMSReactorCore;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.TileSteamFunnel;

public class NuclearScienceBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final HashMap<ISubtype, RegistryObject<Block>> SUBTYPEBLOCKREGISTER_MAPPINGS = new HashMap<>();

	public static GenericMachineBlock blockGasCentrifuge;
	public static GenericMachineBlock blockNuclearBoiler;
	public static GenericMachineBlock blockChemicalExtractor;
	public static GenericMachineBlock blockRadioisotopeGenerator;
	public static BlockTurbine blockTurbine;
	public static BlockFissionReactorCore blockFissionReactorCore;
	public static BlockElectromagnet blockElectromagnet;
	public static BlockElectromagnet blockElectromagneticGlass;
	public static BlockElectromagneticBooster blockElectromagneticBooster;
	public static BlockElectromagneticSwitch blockElectromagneticSwitch;
	public static BlockFusionReactorCore blockFusionReactorCore;
	public static BlockPlasma blockPlasma;
	public static BlockMeltedReactor blockMeltedReactor;
	public static GenericMachineBlock blockParticleInjector;
	public static BlockQuantumCapacitor blockQuantumCapacitor;
	public static BlockTeleporter blockTeleporter;
	public static BlockControlRodAssembly blockControlRodAssembly;
	public static BlockFuelReprocessor blockFuelReprocessor;
	public static BlockRadioactiveProcessor blockRadioactiveProcessor;
	public static BlockMSRFuelPreprocessor blockMSRFuelPreProcessor;
	public static GenericMachineBlock blockFreezePlug;
	public static GenericMachineBlock blockMSReactorCore;
	public static GenericMachineBlock blockHeatExchanger;
	public static GenericMachineBlock blockSiren;
	public static GenericMachineBlock blockAtomicAssembler;
	public static BlockMoltenSaltSupplier blockMoltenSaltSupplier;
	public static BlockRadioactiveSoil blockRadioactiveSoil;
	public static BlockRadioactiveAir blockRadioactiveAir;
	public static Block blocklead;

	public static GenericMachineBlock blockSteamFunnel;

	static {
		BLOCKS.register("gascentrifuge", () -> blockGasCentrifuge = new GenericMachineBlock(TileGasCentrifuge::new));
		BLOCKS.register("nuclearboiler", () -> blockNuclearBoiler = new GenericMachineBlock(TileNuclearBoiler::new));
		BLOCKS.register("chemicalextractor", () -> blockChemicalExtractor = new GenericMachineBlock(TileChemicalExtractor::new));
		BLOCKS.register("radioisotopegenerator", () -> blockRadioisotopeGenerator = new GenericMachineBlock(TileRadioisotopeGenerator::new));
		BLOCKS.register("freezeplug", () -> blockFreezePlug = new GenericMachineBlock(TileFreezePlug::new));
		BLOCKS.register("turbine", () -> blockTurbine = new BlockTurbine());
		BLOCKS.register("steamfunnel", () -> blockSteamFunnel = new GenericMachineBlock(TileSteamFunnel::new));
		BLOCKS.register("fissionreactorcore", () -> blockFissionReactorCore = new BlockFissionReactorCore());
		BLOCKS.register("electromagnet", () -> blockElectromagnet = new BlockElectromagnet(false));
		BLOCKS.register("electromagneticglass", () -> blockElectromagneticGlass = new BlockElectromagnet(true));
		BLOCKS.register("electromagneticbooster", () -> blockElectromagneticBooster = new BlockElectromagneticBooster());
		BLOCKS.register("electromagneticswitch", () -> blockElectromagneticSwitch = new BlockElectromagneticSwitch());
		BLOCKS.register("fusionreactorcore", () -> blockFusionReactorCore = new BlockFusionReactorCore());
		BLOCKS.register("plasma", () -> blockPlasma = new BlockPlasma());
		BLOCKS.register("particleinjector", () -> blockParticleInjector = new GenericMachineBlock(TileParticleInjector::new));
		BLOCKS.register("quantumcapacitor", () -> blockQuantumCapacitor = new BlockQuantumCapacitor());
		BLOCKS.register("teleporter", () -> blockTeleporter = new BlockTeleporter());
		BLOCKS.register("controlrodassembly", () -> blockControlRodAssembly = new BlockControlRodAssembly());
		BLOCKS.register("fuelreprocessor", () -> blockFuelReprocessor = new BlockFuelReprocessor());
		BLOCKS.register("radioactiveprocessor", () -> blockRadioactiveProcessor = new BlockRadioactiveProcessor());
		BLOCKS.register("msrfuelpreprocessor", () -> blockMSRFuelPreProcessor = new BlockMSRFuelPreprocessor(TileMSRFuelPreProcessor::new));
		BLOCKS.register("blocklead", () -> blocklead = new Block(Properties.copy(Blocks.NETHERITE_BLOCK).strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()));
		BLOCKS.register("msreactorcore", () -> blockMSReactorCore = new GenericMachineBlock(TileMSReactorCore::new));
		BLOCKS.register("heatexchanger", () -> blockHeatExchanger = new GenericMachineBlock(TileHeatExchanger::new));
		BLOCKS.register("siren", () -> blockSiren = new GenericMachineBlock(TileSiren::new));
		BLOCKS.register("atomicassembler", () -> blockAtomicAssembler = new GenericMachineBlock(TileAtomicAssembler::new));
		BLOCKS.register("moltensaltsupplier", () -> blockMoltenSaltSupplier = new BlockMoltenSaltSupplier());
		for (SubtypeMoltenSaltPipe subtype : SubtypeMoltenSaltPipe.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), () -> new BlockMoltenSaltPipe(subtype)));
		}
		BLOCKS.register("meltedreactor", () -> blockMeltedReactor = new BlockMeltedReactor());
		BLOCKS.register("radioactiveair", () -> blockRadioactiveAir = new BlockRadioactiveAir());
		BLOCKS.register("radioactivesoil", () -> blockRadioactiveSoil = new BlockRadioactiveSoil());

	}

	public static Block[] getAllBlockForSubtype(ISubtype[] values) {
		List<Block> list = new ArrayList<>();
		for (ISubtype value : values) {
			list.add(SUBTYPEBLOCKREGISTER_MAPPINGS.get(value).get());
		}
		return list.toArray(new Block[] {});
	}

	public static Block getBlock(ISubtype value) {
		return SUBTYPEBLOCKREGISTER_MAPPINGS.get(value).get();
	}

}
