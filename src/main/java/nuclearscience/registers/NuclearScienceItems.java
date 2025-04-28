package nuclearscience.registers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.item.ItemAntimatter;
import nuclearscience.common.item.ItemCanisterLead;
import nuclearscience.common.item.ItemFrequencyCard;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;
import nuclearscience.common.item.ItemHazmatArmor.ArmorMaterialHazmat;
import voltaic.api.creativetab.CreativeTabSupplier;
import voltaic.api.registration.BulkRegistryObject;
import voltaic.common.blockitem.BlockItemDescriptable;
import voltaic.common.item.ItemAntidote;
import voltaic.common.item.ItemIodineTablet;
import voltaic.common.item.ItemRadioactive;
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.item.ElectricItemProperties;
import voltaic.prefab.utilities.object.TransferPack;
import electrodynamics.registers.ElectrodynamicsItems;

public class NuclearScienceItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearScience.ID);
	
	/* BLOCKS */

	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeRadiationShielding> ITEMS_RADIATION_SHIELDING = new BulkRegistryObject<>(SubtypeRadiationShielding.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(subtype), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get())));

	public static final RegistryObject<BlockItemDescriptable> ITEM_TURBINE = ITEMS.register("turbine", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_TURBINE.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeNuclearMachine> ITEMS_NUCLEARMACHINE = new BulkRegistryObject<>(SubtypeNuclearMachine.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(subtype), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeMoltenSaltPipe> ITEMS_MOLTENSALTPIPTE = new BulkRegistryObject<>(SubtypeMoltenSaltPipe.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_MOLTENSALTPIPE.getValue(subtype), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get())));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeReactorLogisticsCable> ITEMS_REACTORLOGISTICSCABLE = new BulkRegistryObject<>(SubtypeReactorLogisticsCable.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_REACTORLOGISTICSCABLE.getValue(subtype), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get())));

	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeElectromagent> ITEMS_ELECTROMAGNET = new BulkRegistryObject<>(SubtypeElectromagent.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(subtype), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get())));

	//public static final RegistryObject<BlockItemDescriptable> ITEM_ELECTROMAGNET = ITEMS.register("electromagnet", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNET.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	//public static final RegistryObject<BlockItemDescriptable> ITEM_ELECTROMAGNETICGLASS = ITEMS.register("electromagneticglass", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGLASS.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ELECTROMAGNETICBOOSTER = ITEMS.register("electromagneticbooster", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ELECTROMAGNETICSWITCH = ITEMS.register("electromagneticswitch", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ELECTROMAGNETICGATEWAY = ITEMS.register("electromagneticgateway", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<BlockItemDescriptable> ITEM_ELECTROMAGNETICDIODE = ITEMS.register("electromagneticdiode", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE.get(), new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static final RegistryObject<BlockItemDescriptable> ITEM_MELTEDREACTOR = ITEMS.register("meltedreactor", () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get(), new Item.Properties(), null));
	public static final BulkRegistryObject<BlockItemDescriptable, SubtypeIrradiatedBlock> ITEMS_IRRADIATED = new BulkRegistryObject<>(SubtypeIrradiatedBlock.values(), subtype -> ITEMS.register(subtype.tag(), () -> new BlockItemDescriptable(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(subtype), new Item.Properties(), null)));

	/* ITEMS */

	public static final RegistryObject<Item> ITEM_URANIUM235 = ITEMS.register("uranium235", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_URANIUM238 = ITEMS.register("uranium238", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_PLUTONIUM239 = ITEMS.register("plutonium239", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_POLONIUM210 = ITEMS.register("polonium210", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_ACTINIUM225 = ITEMS.register("actinium225", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_LIFHT4PUF3 = ITEMS.register("lifthf4uf4", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FLINAK = ITEMS.register("flinak", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_YELLOWCAKE = ITEMS.register("yellowcake", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FISSILEDUST = ITEMS.register("fissiledust", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FISSILE_SALT = ITEMS.register("fissilesalt", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_PLUTONIUMOXIDE = ITEMS.register("plutoniumoxide", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_POLONIUM210_CHUNK = ITEMS.register("polonium210chunk", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_THORIANITEDUST = ITEMS.register("thorianitedust", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_ACTINIUMOXIDE = ITEMS.register("actiniumoxide", () -> new ItemRadioactive(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static final RegistryObject<Item> ITEM_FUELLEUO2 = ITEMS.register("fuelleuo2", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).durability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FUELHEUO2 = ITEMS.register("fuelheuo2", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).durability(24000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FUELPLUTONIUM = ITEMS.register("fuelplutonium", () -> new ItemRadioactive(new Item.Properties().stacksTo(1).durability(120000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FUELSPENT = ITEMS.register("fuelspent", () -> new ItemRadioactive(new Item.Properties().stacksTo(1), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLEMPTY = ITEMS.register("cellempty", () -> new ItemVoltaic(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLDEUTERIUM = ITEMS.register("celldeuterium", () -> new ItemVoltaic(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLTRITIUM = ITEMS.register("celltritium", () -> new ItemVoltaic(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLHEAVYWATER = ITEMS.register("cellheavywater", () -> new ItemVoltaic(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLELECTROMAGNETIC = ITEMS.register("cellelectromagnetic", () -> new ItemVoltaic(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERSMALL = ITEMS.register("cellantimattersmall", () -> new ItemAntimatter(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERLARGE = ITEMS.register("cellantimatterlarge", () -> new ItemAntimatter(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLANTIMATTERVERYLARGE = ITEMS.register("cellantimatterverylarge", () -> new ItemAntimatter(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CELLDARKMATTER = ITEMS.register("celldarkmatter", () -> new ItemAntimatter(new Item.Properties().durability(4), () -> NuclearScienceCreativeTabs.MAIN.get()));

	public static final RegistryObject<Item> ITEM_GEIGERCOUNTER = ITEMS.register("geigercounter", () -> new ItemGeigerCounter((ElectricItemProperties) new ElectricItemProperties().capacity(1666666.66667).extract(TransferPack.joulesVoltage(694.4444444458333, 120.0)).receive(TransferPack.joulesVoltage(694.4444444458333, 120.0)).setIsEnergyStorageOnly().stacksTo(1), () -> NuclearScienceCreativeTabs.MAIN.get(), item -> ElectrodynamicsItems.ITEM_BATTERY.get()));
	public static final RegistryObject<Item> ITEM_HAZMATHELMET = ITEMS.register("hazmathelmet", () -> new ItemHazmatArmor(ArmorMaterialHazmat.hazmat, Type.HELMET, new Item.Properties().durability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATPLATE = ITEMS.register("hazmatplate", () -> new ItemHazmatArmor(ArmorMaterialHazmat.hazmat, Type.CHESTPLATE, new Item.Properties().durability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATLEGS = ITEMS.register("hazmatlegs", () -> new ItemHazmatArmor(ArmorMaterialHazmat.hazmat, Type.LEGGINGS, new Item.Properties().durability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_HAZMATBOOTS = ITEMS.register("hazmatboots", () -> new ItemHazmatArmor(ArmorMaterialHazmat.hazmat, Type.BOOTS, new Item.Properties().durability(26000), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATHELMET = ITEMS.register("reinforcedhazmathelmet", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.HELMET, new Item.Properties().stacksTo(1).durability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATPLATE = ITEMS.register("reinforcedhazmatplate", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.CHESTPLATE, new Item.Properties().stacksTo(1).durability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATLEGS = ITEMS.register("reinforcedhazmatlegs", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.LEGGINGS, new Item.Properties().stacksTo(1).durability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_REINFORCEDHAZMATBOOTS = ITEMS.register("reinforcedhazmatboots", () -> new ItemHazmatArmor(ArmorMaterialHazmat.reinforcedhazmat, Type.BOOTS, new Item.Properties().stacksTo(1).durability(26000 * 5), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_ANTIDOTE = ITEMS.register("antidote", () -> new ItemAntidote(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_IODINETABLET = ITEMS.register("iodinetablet", () -> new ItemIodineTablet(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_FREQUENCYCARD = ITEMS.register("frequencycard", () -> new ItemFrequencyCard(new Item.Properties(), () -> NuclearScienceCreativeTabs.MAIN.get()));
	public static final RegistryObject<Item> ITEM_CANISTERLEAD = ITEMS.register("canisterlead", () -> new ItemCanisterLead(new Item.Properties().stacksTo(1), () -> NuclearScienceCreativeTabs.MAIN.get()));


	@EventBusSubscriber(value = Dist.CLIENT, modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
	private static class NuclearCreativeRegistry {

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
