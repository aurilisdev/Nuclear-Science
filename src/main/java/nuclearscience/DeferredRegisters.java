package nuclearscience;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import nuclearscience.common.block.BlockChemicalExtractor;
import nuclearscience.common.block.BlockControlRodAssembly;
import nuclearscience.common.block.BlockElectromagnet;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.BlockElectromagneticSwitch;
import nuclearscience.common.block.BlockFuelReprocessor;
import nuclearscience.common.block.BlockFusionReactorCore;
import nuclearscience.common.block.BlockGasCentrifuge;
import nuclearscience.common.block.BlockNuclearBoiler;
import nuclearscience.common.block.BlockParticleInjector;
import nuclearscience.common.block.BlockPlasma;
import nuclearscience.common.block.BlockQuantumCapacitor;
import nuclearscience.common.block.BlockRadioactiveProcessor;
import nuclearscience.common.block.BlockRadioisotopeGenerator;
import nuclearscience.common.block.BlockReactorCore;
import nuclearscience.common.block.BlockTeleporter;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.fluid.types.FluidAmmonia;
import nuclearscience.common.fluid.types.FluidIronSulfamate;
import nuclearscience.common.fluid.types.FluidUraniumHexafluoride;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
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
import nuclearscience.common.tile.TileFuelReprocessor;
import nuclearscience.common.tile.TileFusionReactorCore;
import nuclearscience.common.tile.TileGasCentrifuge;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.common.tile.TilePlasma;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.common.tile.TileReactorCore;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.common.tile.TileTurbine;

public class DeferredRegisters {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, References.ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.ID);

    public static FluidUraniumHexafluoride fluidUraniumHexafluoride;
    public static FluidIronSulfamate fluidIronSulfamate;
    public static FluidAmmonia fluidAmmonia;

    public static BlockGasCentrifuge blockGasCentrifuge;
    public static BlockNuclearBoiler blockNuclearBoiler;
    public static BlockChemicalExtractor blockChemicalExtractor;
    public static BlockRadioisotopeGenerator blockRadioisotopeGenerator;
    public static BlockTurbine blockTurbine;
    public static BlockReactorCore blockReactorCore;
    public static BlockElectromagnet blockElectromagnet;
    public static BlockElectromagnet blockElectromagneticGlass;
    public static BlockElectromagneticBooster blockElectromagneticBooster;
    public static BlockElectromagneticSwitch blockElectromagneticSwitch;
    public static BlockFusionReactorCore blockFusionReactorCore;
    public static BlockPlasma blockPlasma;
    public static BlockParticleInjector blockParticleInjector;
    public static BlockQuantumCapacitor blockQuantumCapacitor;
    public static BlockTeleporter blockTeleporter;
    public static BlockControlRodAssembly blockControlRodAssembly;
    public static BlockFuelReprocessor blockFuelReprocessor;
    public static BlockRadioactiveProcessor blockRadioactiveProcessor;

    public static Block blocklead;

    static {
	BLOCKS.register("gascentrifuge", supplier(blockGasCentrifuge = new BlockGasCentrifuge()));
	BLOCKS.register("nuclearboiler", supplier(blockNuclearBoiler = new BlockNuclearBoiler()));
	BLOCKS.register("chemicalextractor", supplier(blockChemicalExtractor = new BlockChemicalExtractor()));
	BLOCKS.register("radioisotopegenerator", supplier(blockRadioisotopeGenerator = new BlockRadioisotopeGenerator()));
	BLOCKS.register("turbine", supplier(blockTurbine = new BlockTurbine()));
	BLOCKS.register("reactorcore", supplier(blockReactorCore = new BlockReactorCore()));
	BLOCKS.register("electromagnet", supplier(blockElectromagnet = new BlockElectromagnet(false)));
	BLOCKS.register("electromagneticglass", supplier(blockElectromagneticGlass = new BlockElectromagnet(true)));
	BLOCKS.register("electromagneticbooster", supplier(blockElectromagneticBooster = new BlockElectromagneticBooster()));
	BLOCKS.register("electromagneticswitch", supplier(blockElectromagneticSwitch = new BlockElectromagneticSwitch()));
	BLOCKS.register("fusionreactorcore", supplier(blockFusionReactorCore = new BlockFusionReactorCore()));
	BLOCKS.register("plasma", supplier(blockPlasma = new BlockPlasma()));
	BLOCKS.register("particleinjector", supplier(blockParticleInjector = new BlockParticleInjector()));
	BLOCKS.register("quantumcapacitor", supplier(blockQuantumCapacitor = new BlockQuantumCapacitor()));
	BLOCKS.register("teleporter", supplier(blockTeleporter = new BlockTeleporter()));

	BLOCKS.register("controlrodassembly", supplier(blockControlRodAssembly = new BlockControlRodAssembly()));
	BLOCKS.register("fuelreprocessor", supplier(blockFuelReprocessor = new BlockFuelReprocessor()));
	BLOCKS.register("radioactiveprocessor", supplier(blockRadioactiveProcessor = new BlockRadioactiveProcessor()));

	BLOCKS.register("blocklead", supplier(blocklead = new Block(Properties.create(Material.IRON, MaterialColor.BLACK)
		.hardnessAndResistance(5.0f, 3.0f).sound(SoundType.METAL).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(1))));

	ITEMS.register("gascentrifuge", supplier(new BlockItemDescriptable(blockGasCentrifuge, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("nuclearboiler", supplier(new BlockItemDescriptable(blockNuclearBoiler, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("chemicalextractor",
		supplier(new BlockItemDescriptable(blockChemicalExtractor, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("radioisotopegenerator",
		supplier(new BlockItemDescriptable(blockRadioisotopeGenerator, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("turbine", supplier(new BlockItemDescriptable(blockTurbine, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("reactorcore", supplier(new BlockItemDescriptable(blockReactorCore, new Item.Properties().group(References.NUCLEARTAB))));

	ITEMS.register("fuelreprocessor",
		supplier(new BlockItemDescriptable(blockFuelReprocessor, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("radioactiveprocessor",
		supplier(new BlockItemDescriptable(blockRadioactiveProcessor, new Item.Properties().group(References.NUCLEARTAB))));

	ITEMS.register("blocklead", supplier(new BlockItemDescriptable(blocklead, new BlockItem.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("electromagnet", supplier(new BlockItemDescriptable(blockElectromagnet, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("electromagneticglass",
		supplier(new BlockItemDescriptable(blockElectromagneticGlass, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("electromagneticbooster",
		supplier(new BlockItemDescriptable(blockElectromagneticBooster, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("electromagneticswitch",
		supplier(new BlockItemDescriptable(blockElectromagneticSwitch, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("fusionreactorcore",
		supplier(new BlockItemDescriptable(blockFusionReactorCore, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("plasma", supplier(new BlockItemDescriptable(blockPlasma, new Item.Properties())));
	ITEMS.register("particleinjector",
		supplier(new BlockItemDescriptable(blockParticleInjector, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("quantumcapacitor",
		supplier(new BlockItemDescriptable(blockQuantumCapacitor, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("teleporter", supplier(new BlockItemDescriptable(blockTeleporter, new Item.Properties().group(References.NUCLEARTAB))));
	ITEMS.register("controlrodassembly",
		supplier(new BlockItemDescriptable(blockControlRodAssembly, new Item.Properties().group(References.NUCLEARTAB))));

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
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_URANIUM238 = ITEMS.register("uranium238",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));

    public static final RegistryObject<Item> ITEM_FISSILEDUST = ITEMS.register("fissiledust",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_PLUTONIUMOXIDE = ITEMS.register("plutoniumoxide",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_PLUTONIUM239 = ITEMS.register("plutonium239",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_THORIANITEDUST = ITEMS.register("thorianitedust",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB))));

    public static final RegistryObject<Item> ITEM_CELLEMPTY = ITEMS.register("cellempty",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLELECTROMAGNETIC = ITEMS.register("cellelectromagnetic",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLANTIMATTERSMALL = ITEMS.register("cellantimattersmall",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLANTIMATTERLARGE = ITEMS.register("cellantimatterlarge",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CELLDARKMATTER = ITEMS.register("celldarkmatter",
	    supplier(new Item(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(24000))));
    public static final RegistryObject<Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(26000))));

    public static final RegistryObject<Item> ITEM_FUELSPENT = ITEMS.register("fuelspent",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1))));
    // Incredibly long life-span, but hard to get
    public static final RegistryObject<Item> ITEM_FUELPLUTONIUM = ITEMS.register("fuelplutonium",
	    supplier(new ItemRadioactive(new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(96000))));

    public static final RegistryObject<Item> ITEM_GEIGERCOUNTER = ITEMS.register("geigercounter",
	    supplier(new ItemGeigerCounter(new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATBOOTS = ITEMS.register("hazmatboots", supplier(
	    new ItemHazmatArmor(EquipmentSlotType.FEET, new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATHELMET = ITEMS.register("hazmathelmet", supplier(
	    new ItemHazmatArmor(EquipmentSlotType.HEAD, new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATLEGS = ITEMS.register("hazmatlegs", supplier(
	    new ItemHazmatArmor(EquipmentSlotType.LEGS, new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(26000))));
    public static final RegistryObject<Item> ITEM_HAZMATPLATE = ITEMS.register("hazmatplate", supplier(new ItemHazmatArmor(EquipmentSlotType.CHEST,
	    new Item.Properties().group(References.NUCLEARTAB).maxStackSize(1).defaultMaxDamage(26000))));
    public static final RegistryObject<Item> ITEM_ANTIDOTE = ITEMS.register("antidote",
	    supplier(new ItemAntidote(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_FREQUENCYCARD = ITEMS.register("frequencycard",
	    supplier(new ItemFrequencyCard(new Item.Properties().group(References.NUCLEARTAB))));
    public static final RegistryObject<Item> ITEM_CANISTERLEAD = ITEMS.register("canisterlead",
	    supplier(new ItemCanisterLead(new Item.Properties().maxStackSize(1).group(References.NUCLEARTAB))));

    public static final RegistryObject<TileEntityType<TileGasCentrifuge>> TILE_GASCENTRIFUGE = TILES.register("gascentrifuge",
	    () -> new TileEntityType<>(TileGasCentrifuge::new, Sets.newHashSet(blockGasCentrifuge), null));
    public static final RegistryObject<TileEntityType<TileNuclearBoiler>> TILE_CHEMICALBOILER = TILES.register("nuclearboiler",
	    () -> new TileEntityType<>(TileNuclearBoiler::new, Sets.newHashSet(blockNuclearBoiler), null));
    public static final RegistryObject<TileEntityType<TileChemicalExtractor>> TILE_CHEMICALEXTRACTOR = TILES.register("chemicalextractor",
	    () -> new TileEntityType<>(TileChemicalExtractor::new, Sets.newHashSet(blockChemicalExtractor), null));
    public static final RegistryObject<TileEntityType<TileRadioisotopeGenerator>> TILE_RADIOISOTOPEGENERATOR = TILES.register("radioisotopegenerator",
	    () -> new TileEntityType<>(TileRadioisotopeGenerator::new, Sets.newHashSet(blockRadioisotopeGenerator), null));
    public static final RegistryObject<TileEntityType<TileTurbine>> TILE_TURBINE = TILES.register("turbine",
	    () -> new TileEntityType<>(TileTurbine::new, Sets.newHashSet(blockTurbine), null));
    public static final RegistryObject<TileEntityType<TileReactorCore>> TILE_REACTORCORE = TILES.register("reactorcore",
	    () -> new TileEntityType<>(TileReactorCore::new, Sets.newHashSet(blockReactorCore), null));
    public static final RegistryObject<TileEntityType<TileFusionReactorCore>> TILE_FUSIONREACTORCORE = TILES.register("fusionreactorcore",
	    () -> new TileEntityType<>(TileFusionReactorCore::new, Sets.newHashSet(blockFusionReactorCore), null));
    public static final RegistryObject<TileEntityType<TileParticleInjector>> TILE_PARTICLEINJECTOR = TILES.register("particleinjector",
	    () -> new TileEntityType<>(TileParticleInjector::new, Sets.newHashSet(blockParticleInjector), null));
    public static final RegistryObject<TileEntityType<TileElectromagneticSwitch>> TILE_ELECTROMAGNETICSWITCH = TILES.register("electromagneticswitch",
	    () -> new TileEntityType<>(TileElectromagneticSwitch::new, Sets.newHashSet(blockElectromagneticSwitch), null));
    public static final RegistryObject<TileEntityType<TilePlasma>> TILE_PLASMA = TILES.register("plasma",
	    () -> new TileEntityType<>(TilePlasma::new, Sets.newHashSet(blockPlasma), null));
    public static final RegistryObject<TileEntityType<TileQuantumCapacitor>> TILE_QUANTUMCAPACITOR = TILES.register("quantumcapacitor",
	    () -> new TileEntityType<>(TileQuantumCapacitor::new, Sets.newHashSet(blockQuantumCapacitor), null));

    public static final RegistryObject<TileEntityType<TileFuelReprocessor>> TILE_FUELREPROCESSOR = TILES.register("fuelreprocessor",
	    () -> new TileEntityType<>(TileFuelReprocessor::new, Sets.newHashSet(blockFuelReprocessor), null));
    public static final RegistryObject<TileEntityType<TileRadioactiveProcessor>> TILE_RADIOACTIVEPROCESSOR = TILES.register("radioactiveprocessor",
	    () -> new TileEntityType<>(TileRadioactiveProcessor::new, Sets.newHashSet(blockRadioactiveProcessor), null));

    public static final RegistryObject<TileEntityType<TileTeleporter>> TILE_TELEPORTER = TILES.register("teleporter",
	    () -> new TileEntityType<>(TileTeleporter::new, Sets.newHashSet(blockTeleporter), null));

    public static final RegistryObject<TileEntityType<TileControlRodAssembly>> TILE_CONTROLRODASSEMBLY = TILES.register("controlrodassembly",
	    () -> new TileEntityType<>(TileControlRodAssembly::new, Sets.newHashSet(blockControlRodAssembly), null));

    public static final RegistryObject<ContainerType<ContainerGasCentrifuge>> CONTAINER_GASCENTRIFUGE = CONTAINERS.register("gascentrifuge",
	    () -> new ContainerType<>(ContainerGasCentrifuge::new));
    public static final RegistryObject<ContainerType<ContainerNuclearBoiler>> CONTAINER_NUCLEARBOILER = CONTAINERS.register("nuclearboiler",
	    () -> new ContainerType<>(ContainerNuclearBoiler::new));
    public static final RegistryObject<ContainerType<ContainerChemicalExtractor>> CONTAINER_CHEMICALEXTRACTOR = CONTAINERS
	    .register("chemicalextractor", () -> new ContainerType<>(ContainerChemicalExtractor::new));
    public static final RegistryObject<ContainerType<ContainerRadioisotopeGenerator>> CONTAINER_RADIOISOTOPEGENERATOR = CONTAINERS
	    .register("radioisotopegenerator", () -> new ContainerType<>(ContainerRadioisotopeGenerator::new));
    public static final RegistryObject<ContainerType<ContainerReactorCore>> CONTAINER_REACTORCORE = CONTAINERS.register("reactorcore",
	    () -> new ContainerType<>(ContainerReactorCore::new));
    public static final RegistryObject<ContainerType<ContainerParticleInjector>> CONTAINER_PARTICLEINJECTOR = CONTAINERS.register("particleinjetor",
	    () -> new ContainerType<>(ContainerParticleInjector::new));
    public static final RegistryObject<ContainerType<ContainerQuantumCapacitor>> CONTAINER_QUANTUMCAPACITOR = CONTAINERS.register("quantumcapacitor",
	    () -> new ContainerType<>(ContainerQuantumCapacitor::new));
    public static final RegistryObject<ContainerType<ContainerRadioactiveProcessor>> CONTAINER_RADIOACTIVEPROCESSOR = CONTAINERS
	    .register("radioactiveprocessor", () -> new ContainerType<>(ContainerRadioactiveProcessor::new));

    public static final RegistryObject<EntityType<EntityParticle>> ENTITY_PARTICLE = ENTITIES.register("particle", () -> EntityType.Builder
	    .<EntityParticle>create(EntityParticle::new, EntityClassification.MISC).trackingRange(8).build(References.ID + ".particle"));

    private static <T extends IForgeRegistryEntry<T>> Supplier<? extends T> supplier(T entry) {
	return () -> entry;
    }
}
