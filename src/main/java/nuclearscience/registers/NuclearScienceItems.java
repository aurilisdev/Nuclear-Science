package nuclearscience.registers;

import static nuclearscience.registers.NuclearScienceBlocks.blockAtomicAssembler;
import static nuclearscience.registers.NuclearScienceBlocks.blockChemicalExtractor;
import static nuclearscience.registers.NuclearScienceBlocks.blockControlRodAssembly;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagnet;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticBooster;
import static nuclearscience.registers.NuclearScienceBlocks.blockElectromagneticGlass;
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
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioactiveSoil;
import static nuclearscience.registers.NuclearScienceBlocks.blockRadioisotopeGenerator;
import static nuclearscience.registers.NuclearScienceBlocks.blockSiren;
import static nuclearscience.registers.NuclearScienceBlocks.blockTeleporter;
import static nuclearscience.registers.NuclearScienceBlocks.blockTurbine;
import static nuclearscience.registers.NuclearScienceBlocks.blocklead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.api.ISubtype;
import electrodynamics.api.creativetab.CreativeTabSupplier;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.common.item.ItemElectrodynamics;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
		ITEMS.register("gascentrifuge", () -> new BlockItemDescriptable(() -> blockGasCentrifuge, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("nuclearboiler", () -> new BlockItemDescriptable(() -> blockNuclearBoiler, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("chemicalextractor", () -> new BlockItemDescriptable(() -> blockChemicalExtractor, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("radioisotopegenerator", () -> new BlockItemDescriptable(() -> blockRadioisotopeGenerator, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("freezeplug", () -> new BlockItemDescriptable(() -> blockFreezePlug, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("turbine", () -> new BlockItemDescriptable(() -> blockTurbine, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("steamfunnel", () -> new BlockItemDescriptable(() -> NuclearScienceBlocks.blockSteamFunnel, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("fissionreactorcore", () -> new BlockItemDescriptable(() -> blockFissionReactorCore, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("fuelreprocessor", () -> new BlockItemDescriptable(() -> blockFuelReprocessor, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("radioactiveprocessor", () -> new BlockItemDescriptable(() -> blockRadioactiveProcessor, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("msrfuelpreprocessor", () -> new BlockItemDescriptable(() -> blockMSRFuelPreProcessor, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("blocklead", () -> new BlockItemDescriptable(() -> blocklead, new BlockItem.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("electromagnet", () -> new BlockItemDescriptable(() -> blockElectromagnet, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("electromagneticglass", () -> new BlockItemDescriptable(() -> blockElectromagneticGlass, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("electromagneticbooster", () -> new BlockItemDescriptable(() -> blockElectromagneticBooster, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("electromagneticswitch", () -> new BlockItemDescriptable(() -> blockElectromagneticSwitch, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("fusionreactorcore", () -> new BlockItemDescriptable(() -> blockFusionReactorCore, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("plasma", () -> new BlockItemDescriptable(() -> blockPlasma, new Item.Properties(), null));
		ITEMS.register("particleinjector", () -> new BlockItemDescriptable(() -> blockParticleInjector, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("quantumcapacitor", () -> new BlockItemDescriptable(() -> blockQuantumCapacitor, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("teleporter", () -> new BlockItemDescriptable(() -> blockTeleporter, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("controlrodassembly", () -> new BlockItemDescriptable(() -> blockControlRodAssembly, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("msreactorcore", () -> new BlockItemDescriptable(() -> blockMSReactorCore, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("heatexchanger", () -> new BlockItemDescriptable(() -> blockHeatExchanger, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("moltensaltsupplier", () -> new BlockItemDescriptable(() -> blockMoltenSaltSupplier, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("siren", () -> new BlockItemDescriptable(() -> blockSiren, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("atomicassembler", () -> new BlockItemDescriptable(() -> blockAtomicAssembler, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		for (SubtypeMoltenSaltPipe subtype : SubtypeMoltenSaltPipe.values()) {
			SUBTYPEITEMREGISTER_MAPPINGS.put(subtype, ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(() -> NuclearScienceBlocks.getBlock(subtype), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get())));
		}
		ITEMS.register("radioactivesoil", () -> new BlockItemDescriptable(() -> blockRadioactiveSoil, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
		ITEMS.register("meltedreactor", () -> new BlockItemDescriptable(() -> blockMeltedReactor, new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	}

	public static final RegistryObject<Item> ITEM_URANIUM235 = ITEMS.register("uranium235", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_URANIUM238 = ITEMS.register("uranium238", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_PLUTONIUM239 = ITEMS.register("plutonium239", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_POLONIUM210 = ITEMS.register("polonium210", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_POLONIUM210_CHUNK = ITEMS.register("polonium210chunk", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_ACTINIUM225 = ITEMS.register("actinium225", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_LIFHT4PUF3 = ITEMS.register("lifthf4uf4", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FISSILE_SALT = ITEMS.register("fissilesalt", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FLINAK = ITEMS.register("flinak", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FISSILEDUST = ITEMS.register("fissiledust", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_PLUTONIUMOXIDE = ITEMS.register("plutoniumoxide", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_THORIANITEDUST = ITEMS.register("thorianitedust", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_ACTINIUMOXIDE = ITEMS.register("actiniumoxide", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static final RegistryObject<Item> ITEM_CELLEMPTY = ITEMS.register("cellempty", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLELECTROMAGNETIC = ITEMS.register("cellelectromagnetic", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERSMALL = ITEMS.register("cellantimattersmall", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERLARGE = ITEMS.register("cellantimatterlarge", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERVERYLARGE = ITEMS.register("cellantimatterverylarge", () -> new ItemElectrodynamics(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLDARKMATTER = ITEMS.register("celldarkmatter", () -> new ItemElectrodynamics(new Item.Properties().defaultDurability(4), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).defaultDurability(24000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).defaultDurability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FUELSPENT = ITEMS.register("fuelspent", () -> new ItemRadioactive(new Item.Properties().stacksTo(1), () -> NuclearScienceCreativeTabs.MAIN.get()));
	// Incredibly long life-span, but hard to get
	public static final RegistryObject<Item> ITEM_FUELPLUTONIUM = ITEMS.register("fuelplutonium", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).defaultDurability(120000), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static final RegistryObject<Item> ITEM_GEIGERCOUNTER = ITEMS.register("geigercounter", () -> new ItemGeigerCounter(new Item.Properties().defaultDurability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATBOOTS = ITEMS.register("hazmatboots", () -> new ItemHazmatArmor(Type.BOOTS, new Item.Properties().defaultDurability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATHELMET = ITEMS.register("hazmathelmet", () -> new ItemHazmatArmor(Type.HELMET, new Item.Properties().defaultDurability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATLEGS = ITEMS.register("hazmatlegs", () -> new ItemHazmatArmor(Type.LEGGINGS, new Item.Properties().defaultDurability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATPLATE = ITEMS.register("hazmatplate", () -> new ItemHazmatArmor(Type.CHESTPLATE, new Item.Properties().defaultDurability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATBOOTS = ITEMS.register("reinforcedhazmatboots", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.BOOTS, new Item.Properties().stacksTo(1).defaultDurability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATHELMET = ITEMS.register("reinforcedhazmathelmet", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.HELMET, new Item.Properties().stacksTo(1).defaultDurability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATLEGS = ITEMS.register("reinforcedhazmatlegs", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.LEGGINGS, new Item.Properties().stacksTo(1).defaultDurability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATPLATE = ITEMS.register("reinforcedhazmatplate", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.CHESTPLATE, new Item.Properties().stacksTo(1).defaultDurability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_ANTIDOTE = ITEMS.register("antidote", () -> new ItemAntidote(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FREQUENCYCARD = ITEMS.register("frequencycard", () -> new ItemFrequencyCard(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CANISTERLEAD = ITEMS.register("canisterlead", () -> new ItemCanisterLead(new Item.Properties().stacksTo(1), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static Item[] getAllItemForSubtype(ISubtype[] values) {
		List<Item> list = new ArrayList<>();
		for (ISubtype value : values) {
			list.add(SUBTYPEITEMREGISTER_MAPPINGS.get(value).get());
		}
		return list.toArray(new Item[] {});
	}

	public static Item getItem(ISubtype value) {
		return SUBTYPEITEMREGISTER_MAPPINGS.get(value).get();
	}
	
	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ElectroCreativeRegistry {

		@SubscribeEvent
		public static void registerItems(BuildCreativeModeTabContentsEvent event) {

			ITEMS.getEntries().forEach(reg -> {

				CreativeTabSupplier supplier = (CreativeTabSupplier) reg.get();

				if (supplier.hasCreativeTab() && supplier.isAllowedInCreativeTab(event.getTab())) {
					List<ItemStack> toAdd = new ArrayList<>();
					supplier.addCreativeModeItems(event.getTab(), toAdd);
					event.acceptAll(toAdd);
				}

			});

		}

	}

}
