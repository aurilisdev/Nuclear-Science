package nuclearscience;

import java.util.HashMap;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.api.ISubtype;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.common.block.BlockControlRodAssembly;
import nuclearscience.common.block.BlockElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticSwitch;
import nuclearscience.common.block.BlockFuelReprocessor;
import nuclearscience.common.block.BlockFusionReactorCore;
import nuclearscience.common.block.BlockMeltedReactor;
import nuclearscience.common.block.BlockMoltenSaltSupplier;
import nuclearscience.common.block.BlockPlasma;
import nuclearscience.common.block.BlockQuantumCapacitor;
import nuclearscience.common.block.BlockRadioactiveProcessor;
import nuclearscience.common.block.BlockRadioactiveSoil;
import nuclearscience.common.block.BlockReactorCore;
import nuclearscience.common.block.BlockTeleporter;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.fluid.types.FluidAmmonia;
import nuclearscience.common.fluid.types.FluidIronSulfamate;
import nuclearscience.common.fluid.types.FluidUraniumHexafluoride;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.inventory.container.ContainerMSRFuelPreProcessor;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.item.ItemAntidote;
import nuclearscience.common.item.ItemCanisterLead;
import nuclearscience.common.item.ItemFrequencyCard;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;
import nuclearscience.common.item.ItemRadioactive;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.common.tile.TileControlRodAssembly;
import nuclearscience.common.tile.TileElectromagneticSwitch;
import nuclearscience.common.tile.TileFreezePlug;
import nuclearscience.common.tile.TileFuelReprocessor;
import nuclearscience.common.tile.TileFusionReactorCore;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileHeatExchanger;
import nuclearscience.common.tile.TileMSRFuelPreProcessor;
import nuclearscience.common.tile.TileMSRReactorCore;
import nuclearscience.common.tile.TileMeltedReactor;
import nuclearscience.common.tile.TileMoltenSaltSupplier;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TilePlasma;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileReactorCore;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.common.tile.TileTurbine;
import nuclearscience.common.tile.network.TileMoltenSaltPipe;

public class DeferredRegisters {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, References.ID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.ID);
    public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, RegistryObject<Block>> SUBTYPEBLOCKREGISTER_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, Item> SUBTYPEITEM_MAPPINGS = new HashMap<>();
    public static final HashMap<Item, ISubtype> ITEMSUBTYPE_MAPPINGS = new HashMap<>();
    public static final HashMap<ISubtype, Block> SUBTYPEBLOCK_MAPPINGS = new HashMap<>();

    public static FluidUraniumHexafluoride fluidUraniumHexafluoride;
    public static FluidIronSulfamate fluidIronSulfamate;
    public static FluidAmmonia fluidAmmonia;

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
    public static GenericMachineBlock blockMSRFuelPreProcessor;
    public static GenericMachineBlock blockFreezePlug;
    public static GenericMachineBlock blockMsrReactorCore;
    public static GenericMachineBlock blockHeatExchanger;
    public static BlockMoltenSaltSupplier blockMoltenSaltSupplier;
    public static BlockRadioactiveSoil blockRadioactiveSoil;

    public static Block blocklead;

    static {
	BLOCKS.register("gascentrifuge", supplier(blockGasCentrifuge = new GenericMachineBlock(TileGasCentrifuge::new)));
	BLOCKS.register("nuclearboiler", supplier(blockNuclearBoiler = new GenericMachineBlock(TileNuclearBoiler::new)));
	BLOCKS.register("chemicalextractor", supplier(blockChemicalExtractor = new GenericMachineBlock(TileChemicalExtractor::new)));
	BLOCKS.register("radioisotopegenerator", supplier(blockRadioisotopeGenerator = new GenericMachineBlock(TileRadioisotopeGenerator::new)));
	BLOCKS.register("freezeplug", supplier(blockFreezePlug = new GenericMachineBlock(TileFreezePlug::new)));
	BLOCKS.register("turbine", supplier(blockTurbine = new BlockTurbine()));
	BLOCKS.register("reactorcore", supplier(blockReactorCore = new BlockReactorCore()));
	BLOCKS.register("electromagnet", supplier(blockElectromagnet = new BlockElectromagnet(false)));
	BLOCKS.register("electromagneticglass", supplier(blockElectromagneticGlass = new BlockElectromagnet(true)));
	BLOCKS.register("electromagneticbooster", supplier(blockElectromagneticBooster = new BlockElectromagneticBooster()));
	BLOCKS.register("electromagneticswitch", supplier(blockElectromagneticSwitch = new BlockElectromagneticSwitch()));
	BLOCKS.register("fusionreactorcore", supplier(blockFusionReactorCore = new BlockFusionReactorCore()));
	BLOCKS.register("plasma", supplier(blockPlasma = new BlockPlasma()));
	BLOCKS.register("particleinjector", supplier(blockParticleInjector = new GenericMachineBlock(TileParticleInjector::new)));
	BLOCKS.register("quantumcapacitor", supplier(blockQuantumCapacitor = new BlockQuantumCapacitor()));
	BLOCKS.register("teleporter", supplier(blockTeleporter = new BlockTeleporter()));
	BLOCKS.register("controlrodassembly", supplier(blockControlRodAssembly = new BlockControlRodAssembly()));
	BLOCKS.register("fuelreprocessor", supplier(blockFuelReprocessor = new BlockFuelReprocessor()));
	BLOCKS.register("radioactiveprocessor", supplier(blockRadioactiveProcessor = new BlockRadioactiveProcessor()));
	BLOCKS.register("msrfuelpreprocessor", supplier(blockMSRFuelPreProcessor = new GenericMachineBlock(TileMSRFuelPreProcessor::new)));
	BLOCKS.register("blocklead", supplier(blocklead = new Block(
		Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops())));
	BLOCKS.register("msrreactorcore", supplier(blockMsrReactorCore = new GenericMachineBlock(TileMSRReactorCore::new)));
	BLOCKS.register("heatexchanger", supplier(blockHeatExchanger = new GenericMachineBlock(TileHeatExchanger::new)));
	BLOCKS.register("moltensaltsupplier", supplier(blockMoltenSaltSupplier = new BlockMoltenSaltSupplier()));
	for (SubtypeMoltenSaltPipe subtype : SubtypeMoltenSaltPipe.values()) {
	    SUBTYPEBLOCKREGISTER_MAPPINGS.put(subtype, BLOCKS.register(subtype.tag(), supplier(new BlockMoltenSaltPipe(subtype), subtype)));
	}
	BLOCKS.register("meltedreactor", supplier(blockMeltedReactor = new BlockMeltedReactor()));
	BLOCKS.register("radioactivesoil", supplier(blockRadioactiveSoil = new BlockRadioactiveSoil()));
	ITEMS.register("gascentrifuge", supplier(new BlockItemDescriptable(blockGasCentrifuge, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("nuclearboiler", supplier(new BlockItemDescriptable(blockNuclearBoiler, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("chemicalextractor",
		supplier(new BlockItemDescriptable(blockChemicalExtractor, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("radioisotopegenerator",
		supplier(new BlockItemDescriptable(blockRadioisotopeGenerator, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("freezeplug", supplier(new BlockItemDescriptable(blockFreezePlug, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("turbine", supplier(new BlockItemDescriptable(blockTurbine, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("reactorcore", supplier(new BlockItemDescriptable(blockReactorCore, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("fuelreprocessor",
		supplier(new BlockItemDescriptable(blockFuelReprocessor, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("radioactiveprocessor",
		supplier(new BlockItemDescriptable(blockRadioactiveProcessor, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("msrfuelpreprocessor",
		supplier(new BlockItemDescriptable(blockMSRFuelPreProcessor, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("blocklead", supplier(new BlockItemDescriptable(blocklead, new BlockItem.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("electromagnet", supplier(new BlockItemDescriptable(blockElectromagnet, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("electromagneticglass",
		supplier(new BlockItemDescriptable(blockElectromagneticGlass, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("electromagneticbooster",
		supplier(new BlockItemDescriptable(blockElectromagneticBooster, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("electromagneticswitch",
		supplier(new BlockItemDescriptable(blockElectromagneticSwitch, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("fusionreactorcore",
		supplier(new BlockItemDescriptable(blockFusionReactorCore, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("plasma", supplier(new BlockItemDescriptable(blockPlasma, new Item.Properties())));
	ITEMS.register("particleinjector",
		supplier(new BlockItemDescriptable(blockParticleInjector, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("quantumcapacitor",
		supplier(new BlockItemDescriptable(blockQuantumCapacitor, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("teleporter", supplier(new BlockItemDescriptable(blockTeleporter, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("controlrodassembly",
		supplier(new BlockItemDescriptable(blockControlRodAssembly, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("msrreactorcore", supplier(new BlockItemDescriptable(blockMsrReactorCore, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("heatexchanger", supplier(new BlockItemDescriptable(blockHeatExchanger, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("moltensaltsupplier",
		supplier(new BlockItemDescriptable(blockMoltenSaltSupplier, new Item.Properties().tab(References.NUCLEARTAB))));
	for (SubtypeMoltenSaltPipe subtype : SubtypeMoltenSaltPipe.values()) {
	    ITEMS.register(subtype.tag(), supplier(
		    new BlockItemDescriptable(SUBTYPEBLOCK_MAPPINGS.get(subtype), new Item.Properties().tab(References.NUCLEARTAB)), subtype));
	}
	ITEMS.register("radioactivesoil",
		supplier(new BlockItemDescriptable(blockRadioactiveSoil, new Item.Properties().tab(References.NUCLEARTAB))));
	ITEMS.register("meltedreactor", supplier(new BlockItemDescriptable(blockMeltedReactor, new Item.Properties().tab(References.NUCLEARTAB))));
	FLUIDS.register("fluiduraniumhexafluoride", supplier(fluidUraniumHexafluoride = new FluidUraniumHexafluoride()));

	FLUIDS.register("fluidironsulfamate", supplier(fluidIronSulfamate = new FluidIronSulfamate()));
	FLUIDS.register("fluidammonia", supplier(fluidAmmonia = new FluidAmmonia()));

	BlockItemDescriptable.addDescription(blockGasCentrifuge, "|translate|tooltip.gascentrifuge.voltage");
	BlockItemDescriptable.addDescription(blockNuclearBoiler, "|translate|tooltip.nuclearboiler.voltage");
	BlockItemDescriptable.addDescription(blockChemicalExtractor, "|translate|tooltip.chemicalextractor.voltage");
	BlockItemDescriptable.addDescription(blockParticleInjector, "|translate|tooltip.particleinjector.voltage");
	BlockItemDescriptable.addDescription(blockQuantumCapacitor, "|translate|tooltip.quantumcapacitor.voltage");
	BlockItemDescriptable.addDescription(blockTeleporter, "|translate|tooltip.teleporter.voltage");
	BlockItemDescriptable.addDescription(blockFuelReprocessor, "|translate|tooltip.fuelreprocessor.voltage");
	BlockItemDescriptable.addDescription(blockRadioactiveProcessor, "|translate|tooltip.radioactiveprocessor.voltage");

    }

    public static final RegistryObject<Item> ITEM_URANIUM235 = ITEMS.register("uranium235",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_URANIUM238 = ITEMS.register("uranium238",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_PLUTONIUM239 = ITEMS.register("plutonium239",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_LIFHT4PUF3 = ITEMS.register("lifthf4uf4",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_FLINAK = ITEMS.register("flinak",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));

    public static final RegistryObject<Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_FISSILEDUST = ITEMS.register("fissiledust",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_PLUTONIUMOXIDE = ITEMS.register("plutoniumoxide",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_THORIANITEDUST = ITEMS.register("thorianitedust",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));

    public static final RegistryObject<Item> ITEM_CELLEMPTY = ITEMS.register("cellempty",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLELECTROMAGNETIC = ITEMS.register("cellelectromagnetic",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLANTIMATTERSMALL = ITEMS.register("cellantimattersmall",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLANTIMATTERLARGE = ITEMS.register("cellantimatterlarge",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLDARKMATTER = ITEMS.register("celldarkmatter",
	    supplier(new Item(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(24000))));
    public static final RegistryObject<Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
    public static final RegistryObject<Item> ITEM_FUELSPENT = ITEMS.register("fuelspent",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1))));
    // Incredibly long life-span, but hard to get
    public static final RegistryObject<Item> ITEM_FUELPLUTONIUM = ITEMS.register("fuelplutonium",
	    supplier(new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(120000))));

    public static final RegistryObject<Item> ITEM_GEIGERCOUNTER = ITEMS.register("geigercounter",
	    supplier(new ItemGeigerCounter(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATBOOTS = ITEMS.register("hazmatboots",
	    supplier(new ItemHazmatArmor(EquipmentSlot.FEET, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATHELMET = ITEMS.register("hazmathelmet",
	    supplier(new ItemHazmatArmor(EquipmentSlot.HEAD, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATLEGS = ITEMS.register("hazmatlegs",
	    supplier(new ItemHazmatArmor(EquipmentSlot.LEGS, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATPLATE = ITEMS.register("hazmatplate", supplier(
	    new ItemHazmatArmor(EquipmentSlot.CHEST, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
    public static final RegistryObject<Item> ITEM_ANTIDOTE = ITEMS.register("antidote",
	    supplier(new ItemAntidote(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_FREQUENCYCARD = ITEMS.register("frequencycard",
	    supplier(new ItemFrequencyCard(new Item.Properties().tab(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CANISTERLEAD = ITEMS.register("canisterlead",
	    supplier(new ItemCanisterLead(new Item.Properties().stacksTo(1).tab(References.NUCLEARTAB))));

    public static final RegistryObject<BlockEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = TILES.register("gascentrifuge",
	    () -> new BlockEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(blockGasCentrifuge), null));
    public static final RegistryObject<BlockEntityType<TileNuclearBoiler>> TILE_CHEMICALBOILER = TILES.register("nuclearboiler",
	    () -> new BlockEntityType<>(TileNuclearBoiler::new, Sets.newHashSet(blockNuclearBoiler), null));
    public static final RegistryObject<BlockEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = TILES.register("chemicalextractor",
	    () -> new BlockEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(blockChemicalExtractor), null));
    public static final RegistryObject<BlockEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = TILES.register(
	    "radioisotopegenerator", () -> new BlockEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(blockRadioisotopeGenerator), null));
    public static final RegistryObject<BlockEntityType<TileMoltenSaltSupplier>> TILE_MOLTENSALTSUPPLIER = TILES.register("moltensaltsupplier",
	    () -> new BlockEntityType<>(TileMoltenSaltSupplier::new, Sets.newHashSet(blockMoltenSaltSupplier), null));
    public static final RegistryObject<BlockEntityType<TileFreezePlug>> TILE_FREEZEPLUG = TILES.register("freezeplug",
	    () -> new BlockEntityType<>(TileFreezePlug::new, Sets.newHashSet(blockFreezePlug), null));
    public static final RegistryObject<BlockEntityType<TileTurbine>> TILE_TURBINE = TILES.register("turbine",
	    () -> new BlockEntityType<>(TileTurbine::new, Sets.newHashSet(blockTurbine), null));
    public static final RegistryObject<BlockEntityType<TileReactorCore>> TILE_REACTORCORE = TILES.register("reactorcore",
	    () -> new BlockEntityType<>(TileReactorCore::new, Sets.newHashSet(blockReactorCore), null));
    public static final RegistryObject<BlockEntityType<TileFusionReactorCore>> TILE_FUSIONREACTORCORE = TILES.register("fusionreactorcore",
	    () -> new BlockEntityType<>(TileFusionReactorCore::new, Sets.newHashSet(blockFusionReactorCore), null));
    public static final RegistryObject<BlockEntityType<TileParticleInjector>> TILE_PARTICLEINJECTOR = TILES.register("particleinjector",
	    () -> new BlockEntityType<>(TileParticleInjector::new, Sets.newHashSet(blockParticleInjector), null));
    public static final RegistryObject<BlockEntityType<TileElectromagneticSwitch>> TILE_ELECTROMAGNETICSWITCH = TILES.register(
	    "electromagneticswitch", () -> new BlockEntityType<>(TileElectromagneticSwitch::new, Sets.newHashSet(blockElectromagneticSwitch), null));
    public static final RegistryObject<BlockEntityType<TilePlasma>> TILE_PLASMA = TILES.register("plasma",
	    () -> new BlockEntityType<>(TilePlasma::new, Sets.newHashSet(blockPlasma), null));
    public static final RegistryObject<BlockEntityType<TileMeltedReactor>> TILE_MELTEDREACTOR = TILES.register("meltedreactor",
	    () -> new BlockEntityType<>(TileMeltedReactor::new, Sets.newHashSet(blockMeltedReactor), null));
    public static final RegistryObject<BlockEntityType<TileQuantumCapacitor>> TILE_QUANTUMCAPACITOR = TILES.register("quantumcapacitor",
	    () -> new BlockEntityType<>(TileQuantumCapacitor::new, Sets.newHashSet(blockQuantumCapacitor), null));

    public static final RegistryObject<BlockEntityType<TileFuelReprocessor>> TILE_FUELREPROCESSOR = TILES.register("fuelreprocessor",
	    () -> new BlockEntityType<>(TileFuelReprocessor::new, Sets.newHashSet(blockFuelReprocessor), null));
    public static final RegistryObject<BlockEntityType<TileRadioactiveProcessor>> TILE_RADIOACTIVEPROCESSOR = TILES.register("radioactiveprocessor",
	    () -> new BlockEntityType<>(TileRadioactiveProcessor::new, Sets.newHashSet(blockRadioactiveProcessor), null));
    public static final RegistryObject<BlockEntityType<TileMSRFuelPreProcessor>> TILE_MSRFUELPREPROCESSOR = TILES.register("msrfuelpreprocessor",
	    () -> new BlockEntityType<>(TileMSRFuelPreProcessor::new, Sets.newHashSet(blockMSRFuelPreProcessor), null));
    public static final RegistryObject<BlockEntityType<TileMSRReactorCore>> TILE_MSRREACTORCORE = TILES.register("msrreactorcore",
	    () -> new BlockEntityType<>(TileMSRReactorCore::new, Sets.newHashSet(blockMsrReactorCore), null));
    public static final RegistryObject<BlockEntityType<TileHeatExchanger>> TILE_HEATEXCHANGER = TILES.register("heatexchanger",
	    () -> new BlockEntityType<>(TileHeatExchanger::new, Sets.newHashSet(blockHeatExchanger), null));

    public static final RegistryObject<BlockEntityType<TileTeleporter>> TILE_TELEPORTER = TILES.register("teleporter",
	    () -> new BlockEntityType<>(TileTeleporter::new, Sets.newHashSet(blockTeleporter), null));

    public static final RegistryObject<BlockEntityType<TileControlRodAssembly>> TILE_CONTROLRODASSEMBLY = TILES.register("controlrodassembly",
	    () -> new BlockEntityType<>(TileControlRodAssembly::new, Sets.newHashSet(blockControlRodAssembly), null));

    public static final RegistryObject<BlockEntityType<TileMoltenSaltPipe>> TILE_MOLTENSALTPIPE = TILES.register("moltensaltpipegenerictile",
	    () -> new BlockEntityType<>(TileMoltenSaltPipe::new, BlockMoltenSaltPipe.PIPESET, null));

    public static final RegistryObject<MenuType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = CONTAINERS.register("gascentrifuge",
	    () -> new MenuType<>(ContainerGasCentrifuge::new));
    public static final RegistryObject<MenuType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = CONTAINERS.register("nuclearboiler",
	    () -> new MenuType<>(ContainerNuclearBoiler::new));
    public static final RegistryObject<MenuType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = CONTAINERS.register("chemicalextractor",
	    () -> new MenuType<>(ContainerChemicalExtractor::new));
    public static final RegistryObject<MenuType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = CONTAINERS
	    .register("radioisotopegenerator", () -> new MenuType<>(ContainerRadioisotopeGenerator::new));
    public static final RegistryObject<MenuType<ContainerFreezePlug>> CONTAINER_FREEZEPLUG = CONTAINERS.register("freezeplug",
	    () -> new MenuType<>(ContainerFreezePlug::new));
    public static final RegistryObject<MenuType<ContainerReactorCore>> CONTAINER_REACTORCORE = CONTAINERS.register("reactorcore",
	    () -> new MenuType<>(ContainerReactorCore::new));
    public static final RegistryObject<MenuType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = CONTAINERS.register("particleinjetor",
	    () -> new MenuType<>(ContainerParticleInjector::new));
    public static final RegistryObject<MenuType<ContainerQuantumCapacitor>> CONTAINER_QUANTUMCAPACITOR = CONTAINERS.register("quantumcapacitor",
	    () -> new MenuType<>(ContainerQuantumCapacitor::new));
    public static final RegistryObject<MenuType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = CONTAINERS
	    .register("radioactiveprocessor", () -> new MenuType<>(ContainerRadioactiveProcessor::new));
    public static final RegistryObject<MenuType<ContainerMSRFuelPreProcessor>> CONTAINER_MSRFUELPREPROCESSOR = CONTAINERS
	    .register("msrfuelpreprocessor", () -> new MenuType<>(ContainerMSRFuelPreProcessor::new));
    public static final RegistryObject<MenuType<ContainerMSRReactorCore>> CONTAINER_MSRREACTORCORE = CONTAINERS.register("msrreactorcore",
	    () -> new MenuType<>(ContainerMSRReactorCore::new));
    public static final RegistryObject<MenuType<ContainerMoltenSaltSupplier>> CONTAINER_MOLTENSALTSUPPLIER = CONTAINERS.register("moltensaltsupplier",
	    () -> new MenuType<>(ContainerMoltenSaltSupplier::new));

    public static final RegistryObject<EntityType<EntityParticle>> ENTITY_PARTICLE = ENTITIES.register("particle", () -> EntityType.Builder
	    .<EntityParticle>of(EntityParticle::new, MobCategory.MISC).clientTrackingRange(8).build(References.ID + ".particle"));

    private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
	return () -> entry;
    }

    private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry, ISubtype en) {
	if (entry instanceof Block block) {
	    SUBTYPEBLOCK_MAPPINGS.put(en, block);
	} else if (entry instanceof Item item) {
	    SUBTYPEITEM_MAPPINGS.put(en, item);
	    ITEMSUBTYPE_MAPPINGS.put(item, en);
	}
	return supplier(entry);
    }
}
