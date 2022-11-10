package nuclearscience.registers;

import static electrodynamics.registers.UnifiedElectrodynamicsRegister.getSafeBlock;
import static electrodynamics.registers.UnifiedElectrodynamicsRegister.supplier;
import static nuclearscience.registers.NuclearScienceBlocks.blockAtomicAssembler;
import static nuclearscience.registers.NuclearScienceBlocks.blockChemicalExtractor;
import static nuclearscience.registers.NuclearScienceBlocks.blockControlRodAssembly;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagnet;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticBooster;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticGlass;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticSwitch;
import static nuclearscience.registers.NuclearScienceBlocks.blockFreezePlug;
import static nuclearscience.registers.NuclearScienceBlocks.blockFuelReprocessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockFusionReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockGasCentrifuge;
import static nuclearscience.registers.NuclearScienceBlocks.blockHeatExchanger;
import static nuclearscience.registers.NuclearScienceBlocks.blockMSRFuelPreProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMeltedReactor;
import static nuclearscience.registers.NuclearScienceBlocks.blockMoltenSaltSupplier;
import static nuclearscience.registers.NuclearScienceBlocks.blockMsrReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockNuclearBoiler;
import static nuclearscience.registers.NuclearScienceBlocks.blockParticleInjector;
import static nuclearscience.registers.NuclearScienceBlocks.blockPlasma;
import static nuclearscience.registers.NuclearScienceBlocks.blockQuantumCapacitor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioactiveProcessor;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioactiveSoil;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioisotopeGenerator;
import static nuclearscience.registers.NuclearScienceBlocks.blockReactorCore;
import static nuclearscience.registers.NuclearScienceBlocks.blockSiren;
import static nuclearscience.registers.NuclearScienceBlocks.blockTeleporter;
import static nuclearscience.registers.NuclearScienceBlocks.blockTurbine;
import static nuclearscience.registers.NuclearScienceBlocks.blocklead;

import java.util.HashMap;

import electrodynamics.api.ISubtype;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.item.ItemAntidote;
import nuclearscience.common.item.ItemCanisterLead;
import nuclearscience.common.item.ItemFrequencyCard;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;
import nuclearscience.common.item.ItemHazmatArmor.ArmorMaterialHazmat;
import nuclearscience.common.item.ItemRadioactive;

public class NuclearScienceItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.ID);
	public static final HashMap<ISubtype, RegistryObject<Item>> SUBTYPEITEMREGISTER_MAPPINGS = new HashMap<>();

	static {
		ITEMS.register("gascentrifuge", supplier(() -> new BlockItemDescriptable(() -> blockGasCentrifuge, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("nuclearboiler", supplier(() -> new BlockItemDescriptable(() -> blockNuclearBoiler, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("chemicalextractor", supplier(() -> new BlockItemDescriptable(() -> blockChemicalExtractor, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("radioisotopegenerator", supplier(() -> new BlockItemDescriptable(() -> blockRadioisotopeGenerator, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("freezeplug", supplier(() -> new BlockItemDescriptable(() -> blockFreezePlug, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("turbine", supplier(() -> new BlockItemDescriptable(() -> blockTurbine, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("reactorcore", supplier(() -> new BlockItemDescriptable(() -> blockReactorCore, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("fuelreprocessor", supplier(() -> new BlockItemDescriptable(() -> blockFuelReprocessor, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("radioactiveprocessor", supplier(() -> new BlockItemDescriptable(() -> blockRadioactiveProcessor, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("msrfuelpreprocessor", supplier(() -> new BlockItemDescriptable(() -> blockMSRFuelPreProcessor, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("blocklead", supplier(() -> new BlockItemDescriptable(() -> blocklead, new BlockItem.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("electromagnet", supplier(() -> new BlockItemDescriptable(() -> blockElectromagnet, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("electromagneticglass", supplier(() -> new BlockItemDescriptable(() -> blockElectromagneticGlass, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("electromagneticbooster", supplier(() -> new BlockItemDescriptable(() -> blockElectromagneticBooster, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("electromagneticswitch", supplier(() -> new BlockItemDescriptable(() -> blockElectromagneticSwitch, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("fusionreactorcore", supplier(() -> new BlockItemDescriptable(() -> blockFusionReactorCore, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("plasma", supplier(() -> new BlockItemDescriptable(() -> blockPlasma, new Item.Properties())));
		ITEMS.register("particleinjector", supplier(() -> new BlockItemDescriptable(() -> blockParticleInjector, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("quantumcapacitor", supplier(() -> new BlockItemDescriptable(() -> blockQuantumCapacitor, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("teleporter", supplier(() -> new BlockItemDescriptable(() -> blockTeleporter, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("controlrodassembly", supplier(() -> new BlockItemDescriptable(() -> blockControlRodAssembly, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("msrreactorcore", supplier(() -> new BlockItemDescriptable(() -> blockMsrReactorCore, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("heatexchanger", supplier(() -> new BlockItemDescriptable(() -> blockHeatExchanger, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("moltensaltsupplier", supplier(() -> new BlockItemDescriptable(() -> blockMoltenSaltSupplier, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("siren", supplier(() -> new BlockItemDescriptable(() -> blockSiren, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("atomicassembler", supplier(() -> new BlockItemDescriptable(() -> blockAtomicAssembler, new Item.Properties().tab(References.NUCLEARTAB))));
		for (SubtypeMoltenSaltPipe subtype : SubtypeMoltenSaltPipe.values()) {
			ITEMS.register(subtype.tag(), supplier(() -> new BlockItemDescriptable(() -> getSafeBlock(subtype), new Item.Properties().tab(References.NUCLEARTAB)), subtype));
		}
		ITEMS.register("radioactivesoil", supplier(() -> new BlockItemDescriptable(() -> blockRadioactiveSoil, new Item.Properties().tab(References.NUCLEARTAB))));
		ITEMS.register("meltedreactor", supplier(() -> new BlockItemDescriptable(() -> blockMeltedReactor, new Item.Properties().tab(References.NUCLEARTAB))));
	}

	public static final RegistryObject<Item> ITEM_URANIUM235 = ITEMS.register("uranium235", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_URANIUM238 = ITEMS.register("uranium238", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_PLUTONIUM239 = ITEMS.register("plutonium239", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_POLONIUM210 = ITEMS.register("polonium210", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_POLONIUM210_CHUNK = ITEMS.register("polonium210chunk", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_LIFHT4PUF3 = ITEMS.register("lifthf4uf4", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_FLINAK = ITEMS.register("flinak", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_FISSILEDUST = ITEMS.register("fissiledust", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_PLUTONIUMOXIDE = ITEMS.register("plutoniumoxide", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_THORIANITEDUST = ITEMS.register("thorianitedust", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB))));

	public static final RegistryObject<Item> ITEM_CELLEMPTY = ITEMS.register("cellempty", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLELECTROMAGNETIC = ITEMS.register("cellelectromagnetic", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERSMALL = ITEMS.register("cellantimattersmall", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERLARGE = ITEMS.register("cellantimatterlarge", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERVERYLARGE = ITEMS.register("cellantimatterverylarge", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CELLDARKMATTER = ITEMS.register("celldarkmatter", supplier(() -> new Item(new Item.Properties().tab(References.NUCLEARTAB).defaultDurability(4))));
	public static final RegistryObject<Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(24000))));
	public static final RegistryObject<Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
	public static final RegistryObject<Item> ITEM_FUELSPENT = ITEMS.register("fuelspent", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1))));
	// Incredibly long life-span, but hard to get
	public static final RegistryObject<Item> ITEM_FUELPLUTONIUM = ITEMS.register("fuelplutonium", supplier(() -> new ItemRadioactive(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(120000))));

	public static final RegistryObject<Item> ITEM_GEIGERCOUNTER = ITEMS.register("geigercounter", supplier(() -> new ItemGeigerCounter(new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
	public static final RegistryObject<Item> ITEM_HAZMATBOOTS = ITEMS.register("hazmatboots", supplier(() -> new ItemHazmatArmor(EquipmentSlot.FEET, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
	public static final RegistryObject<Item> ITEM_HAZMATHELMET = ITEMS.register("hazmathelmet", supplier(() -> new ItemHazmatArmor(EquipmentSlot.HEAD, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
	public static final RegistryObject<Item> ITEM_HAZMATLEGS = ITEMS.register("hazmatlegs", supplier(() -> new ItemHazmatArmor(EquipmentSlot.LEGS, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
	public static final RegistryObject<Item> ITEM_HAZMATPLATE = ITEMS.register("hazmatplate", supplier(() -> new ItemHazmatArmor(EquipmentSlot.CHEST, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000))));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATBOOTS = ITEMS.register("reinforcedhazmatboots", supplier(() -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, EquipmentSlot.FEET, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000 * 5))));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATHELMET = ITEMS.register("reinforcedhazmathelmet", supplier(() -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, EquipmentSlot.HEAD, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000 * 5))));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATLEGS = ITEMS.register("reinforcedhazmatlegs", supplier(() -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, EquipmentSlot.LEGS, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000 * 5))));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATPLATE = ITEMS.register("reinforcedhazmatplate", supplier(() -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, EquipmentSlot.CHEST, new Item.Properties().tab(References.NUCLEARTAB).stacksTo(1).defaultDurability(26000 * 5))));
	public static final RegistryObject<Item> ITEM_ANTIDOTE = ITEMS.register("antidote", supplier(() -> new ItemAntidote(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_FREQUENCYCARD = ITEMS.register("frequencycard", supplier(() -> new ItemFrequencyCard(new Item.Properties().tab(References.NUCLEARTAB))));
	public static final RegistryObject<Item> ITEM_CANISTERLEAD = ITEMS.register("canisterlead", supplier(() -> new ItemCanisterLead(new Item.Properties().stacksTo(1).tab(References.NUCLEARTAB))));

}
