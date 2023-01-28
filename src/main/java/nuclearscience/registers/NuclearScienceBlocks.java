package nuclearscience.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.block.BlockControlRodAssembly;
import nuclearscience.common.block.BlockElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticSwitch;
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
import nuclearscience.common.block.BlockReactorCore;
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
import nuclearscience.common.tile.TileMSRReactorCore;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;

public class NuclearScienceBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
	public static final HashMap<ISubtype, RegistryObject<Block>> SUBTYPEBLOCKREGISTER_MAPPINGS = new HashMap<>();

	public static GenericMachineBlock blockGasCentrifuge;
	public static GenericMachineBlock blockNuclearBoiler;
	public static GenericMachineBlock blockChemicalExtractor;
	public static GenericMachineBlock blockRadioisotopeGenerator;
	public static BlockTurbine blockTurbine;
	public static BlockReactorCore blockReactorCore;
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
	public static GenericMachineBlock blockMsrReactorCore;
	public static GenericMachineBlock blockHeatExchanger;
	public static GenericMachineBlock blockSiren;
	public static GenericMachineBlock blockAtomicAssembler;
	public static BlockMoltenSaltSupplier blockMoltenSaltSupplier;
	public static BlockRadioactiveSoil blockRadioactiveSoil;
	public static BlockRadioactiveAir blockRadioactiveAir;
	public static Block blocklead;

	static {
		BLOCKS.register("gascentrifuge", supplier(() -> blockGasCentrifuge = new GenericMachineBlock(TileGasCentrifuge::new)));
		BLOCKS.register("nuclearboiler", supplier(() -> blockNuclearBoiler = new GenericMachineBlock(TileNuclearBoiler::new)));
		BLOCKS.register("chemicalextractor", supplier(() -> blockChemicalExtractor = new GenericMachineBlock(TileChemicalExtractor::new)));
		BLOCKS.register("radioisotopegenerator", supplier(() -> blockRadioisotopeGenerator = new GenericMachineBlock(TileRadioisotopeGenerator::new)));
		BLOCKS.register("freezeplug", supplier(() -> blockFreezePlug = new GenericMachineBlock(TileFreezePlug::new)));
		BLOCKS.register("turbine", supplier(() -> blockTurbine = new BlockTurbine()));
		BLOCKS.register("reactorcore", supplier(() -> blockReactorCore = new BlockReactorCore()));
		BLOCKS.register("electromagnet", supplier(() -> blockElectromagnet = new BlockElectromagnet(false)));
		BLOCKS.register("electromagneticglass", supplier(() -> blockElectromagneticGlass = new BlockElectromagnet(true)));
		BLOCKS.register("electromagneticbooster", supplier(() -> blockElectromagneticBooster = new BlockElectromagneticBooster()));
		BLOCKS.register("electromagneticswitch", supplier(() -> blockElectromagneticSwitch = new BlockElectromagneticSwitch()));
		BLOCKS.register("fusionreactorcore", supplier(() -> blockFusionReactorCore = new BlockFusionReactorCore()));
		BLOCKS.register("plasma", supplier(() -> blockPlasma = new BlockPlasma()));
		BLOCKS.register("particleinjector", supplier(() -> blockParticleInjector = new GenericMachineBlock(TileParticleInjector::new)));
		BLOCKS.register("quantumcapacitor", supplier(() -> blockQuantumCapacitor = new BlockQuantumCapacitor()));
		BLOCKS.register("teleporter", supplier(() -> blockTeleporter = new BlockTeleporter()));
		BLOCKS.register("controlrodassembly", supplier(() -> blockControlRodAssembly = new BlockControlRodAssembly()));
		BLOCKS.register("fuelreprocessor", supplier(() -> blockFuelReprocessor = new BlockFuelReprocessor()));
		BLOCKS.register("radioactiveprocessor", supplier(() -> blockRadioactiveProcessor = new BlockRadioactiveProcessor()));
		BLOCKS.register("msrfuelpreprocessor", supplier(() -> blockMSRFuelPreProcessor = new BlockMSRFuelPreprocessor(TileMSRFuelPreProcessor::new)));
		BLOCKS.register("blocklead", supplier(() -> blocklead = new Block(Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops())));
		BLOCKS.register("msrreactorcore", supplier(() -> blockMsrReactorCore = new GenericMachineBlock(TileMSRReactorCore::new)));
		BLOCKS.register("heatexchanger", supplier(() -> blockHeatExchanger = new GenericMachineBlock(TileHeatExchanger::new)));
		BLOCKS.register("siren", supplier(() -> blockSiren = new GenericMachineBlock(TileSiren::new)));
		BLOCKS.register("atomicassembler", supplier(() -> blockAtomicAssembler = new GenericMachineBlock(TileAtomicAssembler::new)));
		BLOCKS.register("moltensaltsupplier", supplier(() -> blockMoltenSaltSupplier = new BlockMoltenSaltSupplier()));
		for (SubtypeMoltenSaltPipe subtype : SubtypeMoltenSaltPipe.values()) {
			SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(() -> new BlockMoltenSaltPipe(subtype), subtype)));
		}
		BLOCKS.register("meltedreactor", supplier(() -> blockMeltedReactor = new BlockMeltedReactor()));
		BLOCKS.register("radioactiveair", supplier(() -> blockRadioactiveAir = new BlockRadioactiveAir()));
		BLOCKS.register("radioactivesoil", supplier(() -> blockRadioactiveSoil = new BlockRadioactiveSoil()));

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
