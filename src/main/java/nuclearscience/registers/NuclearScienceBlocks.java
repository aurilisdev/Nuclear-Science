package nuclearscience.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;
import nuclearscience.common.block.BlockControlRodAssembly;
import nuclearscience.common.block.BlockElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticSwitch;
import nuclearscience.common.block.BlockFuelReprocessor;
import nuclearscience.common.block.BlockFusionReactorCore;
import nuclearscience.common.block.BlockMSRFuelPreProcessor;
import nuclearscience.common.block.BlockMeltedReactor;
import nuclearscience.common.block.BlockMoltenSaltSupplier;
import nuclearscience.common.block.BlockPlasma;
import nuclearscience.common.block.BlockQuantumCapacitor;
import nuclearscience.common.block.BlockRadioactiveAir;
import nuclearscience.common.block.BlockRadioactiveProcessor;
import nuclearscience.common.block.BlockRadioactiveSoil;
import nuclearscience.common.block.BlockFissionReactorCore;
import nuclearscience.common.block.BlockTeleporter;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileSiren;
import nuclearscience.common.tile.msreactor.TileFreezePlug;
import nuclearscience.common.tile.msreactor.TileHeatExchanger;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;

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
	public static BlockMSRFuelPreProcessor blockMSRFuelPreProcessor;
	public static GenericMachineBlock blockFreezePlug;
	public static GenericMachineBlock blockMSReactorCore;
	public static GenericMachineBlock blockHeatExchanger;
	public static GenericMachineBlock blockSiren;
	public static GenericMachineBlock blockAtomicAssembler;
	public static BlockMoltenSaltSupplier blockMoltenSaltSupplier;
	public static BlockRadioactiveSoil blockRadioactiveSoil;
	public static BlockRadioactiveAir blockRadioactiveAir;
	public static Block blocklead;

	static {
		BLOCKS.register("gascentrifuge", supplier(() -> blockGasCentrifuge = new GenericMachineBlock(world -> new TileGasCentrifuge())));
		BLOCKS.register("nuclearboiler", supplier(() -> blockNuclearBoiler = new GenericMachineBlock(world -> new TileNuclearBoiler())));
		BLOCKS.register("chemicalextractor", supplier(() -> blockChemicalExtractor = new GenericMachineBlock(world -> new TileChemicalExtractor())));
		BLOCKS.register("radioisotopegenerator", supplier(() -> blockRadioisotopeGenerator = new GenericMachineBlock(world -> new TileRadioisotopeGenerator())));
		BLOCKS.register("freezeplug", supplier(() -> blockFreezePlug = new GenericMachineBlock(world -> new TileFreezePlug())));
		BLOCKS.register("turbine", supplier(() -> blockTurbine = new BlockTurbine()));
		BLOCKS.register("reactorcore", supplier(() -> blockFissionReactorCore = new BlockFissionReactorCore()));
		BLOCKS.register("electromagnet", supplier(() -> blockElectromagnet = new BlockElectromagnet(false)));
		BLOCKS.register("electromagneticglass", supplier(() -> blockElectromagneticGlass = new BlockElectromagnet(true)));
		BLOCKS.register("electromagneticbooster", supplier(() -> blockElectromagneticBooster = new BlockElectromagneticBooster()));
		BLOCKS.register("electromagneticswitch", supplier(() -> blockElectromagneticSwitch = new BlockElectromagneticSwitch()));
		BLOCKS.register("fusionreactorcore", supplier(() -> blockFusionReactorCore = new BlockFusionReactorCore()));
		BLOCKS.register("plasma", supplier(() -> blockPlasma = new BlockPlasma()));
		BLOCKS.register("particleinjector", supplier(() -> blockParticleInjector = new GenericMachineBlock(world -> new TileParticleInjector())));
		BLOCKS.register("quantumcapacitor", supplier(() -> blockQuantumCapacitor = new BlockQuantumCapacitor()));
		BLOCKS.register("teleporter", supplier(() -> blockTeleporter = new BlockTeleporter()));
		BLOCKS.register("controlrodassembly", supplier(() -> blockControlRodAssembly = new BlockControlRodAssembly()));
		BLOCKS.register("fuelreprocessor", supplier(() -> blockFuelReprocessor = new BlockFuelReprocessor()));
		BLOCKS.register("radioactiveprocessor", supplier(() -> blockRadioactiveProcessor = new BlockRadioactiveProcessor()));
		BLOCKS.register("msrfuelpreprocessor", supplier(() -> blockMSRFuelPreProcessor = new BlockMSRFuelPreProcessor()));
		BLOCKS.register("blocklead", supplier(() -> blocklead = new Block(Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(1))));
		BLOCKS.register("msrreactorcore", supplier(() -> blockMSReactorCore = new GenericMachineBlock(world -> new TileMSReactorCore())));
		BLOCKS.register("heatexchanger", supplier(() -> blockHeatExchanger = new GenericMachineBlock(world -> new TileHeatExchanger())));
		BLOCKS.register("siren", supplier(() -> blockSiren = new GenericMachineBlock(world -> new TileSiren())));
		BLOCKS.register("atomicassembler", supplier(() -> blockAtomicAssembler = new GenericMachineBlock(world -> new TileAtomicAssembler())));
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
