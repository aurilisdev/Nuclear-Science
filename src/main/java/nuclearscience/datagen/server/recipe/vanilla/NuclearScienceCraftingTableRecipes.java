package nuclearscience.datagen.server.recipe.vanilla;

import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.ShapedCraftingRecipeBuilder;
import voltaic.datagen.utils.server.recipe.ShapelessCraftingRecipeBuilder;

public class NuclearScienceCraftingTableRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(RecipeOutput output) {

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ANTIDOTE.get(), 3)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(ItemTags.FISHES)
				//
				.complete(NuclearScience.ID, "antidote", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base), 2)
				//
				.addPattern("LLL")
				//
				.addPattern("CCC")
				//
				.addPattern("LLL")
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.complete(NuclearScience.ID, "leadlined_block", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), 1)
				//
				.addPattern("BB")
				//
				.addPattern("BB")
				//
				.addPattern("BB")
				//
				.addKey('B', NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base))
				//
				.complete(NuclearScience.ID, "leadlined_door", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), 1)
				//
				.addPattern("BB")
				//
				.addPattern("BB")
				//
				.addKey('B', NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base))
				//
				.complete(NuclearScience.ID, "leadlined_trapdoor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass), 8)
				//
				.addPattern("GGG")
				//
				.addPattern("GBG")
				//
				.addPattern("GGG")
				//
				.addKey('B', NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base))
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.complete(NuclearScience.ID, "leadlined_glass", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE.get(), 1)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL)
				//
				.complete(NuclearScience.ID, "cellantimatter_large", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE.get(), 1)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.complete(NuclearScience.ID, "cellantimatter_verylarge", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get(), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("CEC")
				//
				.addPattern(" C ")
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(NuclearScience.ID, "cellelectromagnetic", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLEMPTY.get(), 4)
				//
				.addPattern("GTG")
				//
				.addPattern("T T")
				//
				.addPattern("GTG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('T', VoltaicTags.Items.INGOT_TIN)
				//
				.complete(NuclearScience.ID, "cellempty_glass", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CELLEMPTY.get(), 6)
				//
				.addPattern("GTG")
				//
				.addPattern("T T")
				//
				.addPattern("GTG")
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear))
				//
				.addKey('T', VoltaicTags.Items.INGOT_TIN)
				//
				.complete(NuclearScience.ID, "cellempty_clearglass", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet), 1)
				//
				.addPattern("BSB")
				//
				.addPattern("SMS")
				//
				.addPattern("BSB")
				//
				.addKey('B', VoltaicTags.Items.PLATE_BRONZE)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(NuclearScience.ID, "electromagnet_steelbronze", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet), 15)
				//
				.addPattern("THT")
				//
				.addPattern("HMH")
				//
				.addPattern("THT")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUM)
				//
				.addKey('H', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(NuclearScience.ID, "electromagnet_hslatitanium", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICBOOSTER.get(), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("EGE")
				//
				.addPattern(" C ")
				//
				.addKey('E', NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet))
				//
				.addKey('G', NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagneticglass))
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(NuclearScience.ID, "electromagneticbooster", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagneticglass), 1)
				//
				.addIngredient(NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet))
				//
				.addIngredient(Tags.Items.GLASS_BLOCKS)
				//
				.complete(NuclearScience.ID, "electromagneticglass", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICSWITCH.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_ELECTROMAGNETICBOOSTER.get())
				//
				.addIngredient(ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addIngredient(VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(NuclearScience.ID, "electromagneticswitch", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICGATEWAY.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_ELECTROMAGNETICSWITCH.get())
				//
				.addIngredient(ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addIngredient(VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.complete(NuclearScience.ID, "electromagneticgateway", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_ELECTROMAGNETICDIODE.get(), 1)
				//
				.addPattern("C")
				//
				.addPattern("S")
				//
				.addPattern("C")
				//
				.addKey('S', NuclearScienceItems.ITEM_ELECTROMAGNETICSWITCH.get())
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.complete(NuclearScience.ID, "electromagneticdiode", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FREQUENCYCARD.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("WCW")
				//
				.addPattern(" P ")
				//
				.addKey('P', VoltaicTags.Items.PLATE_IRON)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(NuclearScience.ID, "frequencycard_new", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FREQUENCYCARD.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_FREQUENCYCARD.get())
				//
				.complete(NuclearScience.ID, "frequencycard_reset", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELHEUO2.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GHG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.addKey('H', NuclearScienceTags.Items.PELLET_URANIUM235)
				//
				.complete(NuclearScience.ID, "fuelrod_uranium_highenrich", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELLEUO2.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GLG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.complete(NuclearScience.ID, "fuelrod_uranium_lowenrich", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_FUELPLUTONIUM.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GPG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS_BLOCKS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(NuclearScience.ID, "fuelrod_plutonium", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_MOLTENSALTPIPTE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic), 2)
				//
				.addPattern("CCC")
				//
				.addPattern("VVV")
				//
				.addPattern("CCC")
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.plate))
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.complete(NuclearScience.ID, "moltensaltpipe_ceramicvanadium", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base), 6)
				//
				.addPattern("VVV")
				//
				.addPattern("GGG")
				//
				.addPattern("VVV")
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear))
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.complete(NuclearScience.ID, "reactorlogisticscable", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_POLONIUM210.get(), 1)
				//
				.addPattern("PP")
				//
				.addPattern("PP")
				//
				.addKey('P', NuclearScienceTags.Items.NUGGET_POLONIUM)
				//
				.complete(NuclearScience.ID, "poloniumpellet_from_nuggets", output);

		addGear(output);
		addMachines(output);

	}

	public void addGear(RecipeOutput output) {
		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_CANISTERLEAD.get(), 1)
				//
				.addPattern("VLV")
				//
				.addPattern("LCL")
				//
				.addPattern("VLV")
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get())
				//
				.complete(NuclearScience.ID, "canisterlead", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_GEIGERCOUNTER.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PBP")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('B', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(NuclearScience.ID, "geigercounter", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATHELMET.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LHL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('H', Items.LEATHER_HELMET)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(NuclearScience.ID, "hazmathelmet", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATPLATE.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LcL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('c', Items.LEATHER_CHESTPLATE)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(NuclearScience.ID, "hazmatchestplate", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATLEGS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LlL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('l', Items.LEATHER_LEGGINGS)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(NuclearScience.ID, "hazmatleggings", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_HAZMATBOOTS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LBL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('B', Items.LEATHER_BOOTS)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.complete(NuclearScience.ID, "hazmatboots", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LHL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('H', NuclearScienceItems.ITEM_HAZMATHELMET.get())
				//
				.complete(NuclearScience.ID, "reinforcedhazmathelmet", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LcL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('c', NuclearScienceItems.ITEM_HAZMATPLATE.get())
				//
				.complete(NuclearScience.ID, "reinforcedhazmatchestplate", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LlL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('l', NuclearScienceItems.ITEM_HAZMATLEGS.get())
				//
				.complete(NuclearScience.ID, "reinforcedhazmatleggings", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LBL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('B', NuclearScienceItems.ITEM_HAZMATBOOTS.get())
				//
				.complete(NuclearScience.ID, "reinforcedhazmatboots", output);

	}

	public void addMachines(RecipeOutput output) {

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler), 1)
				//
				.addPattern("CCC")
				//
				.addPattern("SGS")
				//
				.addPattern("SSS")
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('G', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge))
				//
				.complete(NuclearScience.ID, "machine_atomicassembler", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor), 1)
				//
				.addPattern("SPS")
				//
				.addPattern("MCM")
				//
				.addPattern("SPS")
				//
				.addKey('S', VoltaicTags.Items.INGOT_STEEL)
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(NuclearScience.ID, "machine_chemicalextractor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chunkloader), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("CDC")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.complete(NuclearScience.ID, "machine_chunkloader", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber), 1)
				//
				.addPattern("PGP")
				//
				.addPattern("GCG")
				//
				.addPattern("OTO")
				//
				.addKey('P', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_BASIC)
				//
				.addKey('O', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel))
				//
				.complete(NuclearScience.ID, "machine_cloudchamber", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("BMB")
				//
				.addPattern("TCT")
				//
				.addKey('P', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('B', Items.IRON_BARS)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.tanksteel))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(NuclearScience.ID, "machine_falloutscrubber", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod), 1)
				//
				.addPattern("SsS")
				//
				.addPattern(" P ")
				//
				.addPattern("SCS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('s', VoltaicTags.Items.INGOT_SILVER)
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(NuclearScience.ID, "machine_fissioncontrolrod", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod), 1)
				//
				.addPattern(" C ")
				//
				.addPattern("CRC")
				//
				.addPattern(" C ")
				//
				.addKey('C', ItemTags.COALS)
				//
				.addKey('R', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod))
				//
				.complete(NuclearScience.ID, "machine_mscontrolrod", output);

		ShapelessCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod), 1)
				//
				.addIngredient(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod))
				//
				.complete(NuclearScience.ID, "machine_fissioncontrolrod_conversion", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("SCS")
				//
				.addPattern("SBS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('C', Tags.Items.STORAGE_BLOCKS_COPPER)
				//
				.addKey('B', VoltaicTags.Items.STORAGE_BLOCK_VANADIUMSTEEL)
				//
				.complete(NuclearScience.ID, "machine_freezeplug", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fuelreprocessor), 1)
				//
				.addPattern("VSV")
				//
				.addPattern("STS")
				//
				.addPattern("VSV")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(NuclearScience.ID, "machine_fuelreprocessor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("MEM")
				//
				.addPattern("PCP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(NuclearScience.ID, "machine_fissionreactorcore", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusionreactorcore), 1)
				//
				.addPattern("UEU")
				//
				.addPattern("ECE")
				//
				.addPattern("UEU")
				//
				.addKey('U', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('E', NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet))
				//
				.addKey('C', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore))
				//
				.complete(NuclearScience.ID, "machine_fusionreactorcore", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge), 1)
				//
				.addPattern("SES")
				//
				.addPattern("CGC")
				//
				.addPattern("BMB")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('E', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('C', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.addKey('G', VoltaicTags.Items.GEAR_STEEL)
				//
				.addKey('B', VoltaicTags.Items.PLATE_BRONZE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(NuclearScience.ID, "machine_gascentrifuge", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PPP")
				//
				.addPattern("SCS")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('S', VoltaicTags.Items.INGOT_SILVER)
				//
				.addKey('C', Tags.Items.STORAGE_BLOCKS_COPPER)
				//
				.complete(NuclearScience.ID, "machine_heatexchanger", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier), 1)
				//
				.addPattern("SVS")
				//
				.addPattern("TTT")
				//
				.addPattern("SVS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(NuclearScience.ID, "machine_moltensaltsupplier", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msrfuelpreprocessor), 1)
				//
				.addPattern("VLV")
				//
				.addPattern("LCL")
				//
				.addPattern("VEV")
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalmixer))
				//
				.addKey('E', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.complete(NuclearScience.ID, "machine_msrfuelpreprocessor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore), 1)
				//
				.addPattern("SVS")
				//
				.addPattern("VRV")
				//
				.addPattern("SPS")
				//
				.addKey('S', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('R', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore))
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(NuclearScience.ID, "machine_msrreactorcore", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("EFE")
				//
				.addPattern("PMP")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.addKey('F', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.electricarcfurnace))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(NuclearScience.ID, "machine_nuclearboiler", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector), 1)
				//
				.addPattern("MTM")
				//
				.addPattern("UDU")
				//
				.addPattern("MTM")
				//
				.addKey('M', NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet))
				//
				.addKey('T', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.upgradetransformer))
				//
				.addKey('U', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('D', Items.DISPENSER)
				//
				.complete(NuclearScience.ID, "machine_particleinjector", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.quantumcapacitor), 1)
				//
				.addPattern("DCD")
				//
				.addPattern("EDE")
				//
				.addPattern("DCD")
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('E', Tags.Items.ENDER_PEARLS)
				//
				.complete(NuclearScience.ID, "machine_quantumtunnel", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor), 1)
				//
				.addPattern("VTV")
				//
				.addPattern("VMV")
				//
				.addPattern("VCV")
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.addKey('M', ElectrodynamicsItems.ITEMS_MACHINE.getValue(SubtypeMachine.chemicalmixer))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.complete(NuclearScience.ID, "machine_radioactiveprocessor", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioisotopegenerator), 1)
				//
				.addPattern("VCV")
				//
				.addPattern("LEL")
				//
				.addPattern("VCV")
				//
				.addKey('V', VoltaicTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('L', VoltaicTags.Items.PLATE_LEAD)
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(NuclearScience.ID, "machine_radioisotopegenerator", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.siren), 1)
				//
				.addPattern("NPN")
				//
				.addKey('N', Items.NOTE_BLOCK)
				//
				.addKey('P', VoltaicTags.Items.PLATE_BRONZE)
				//
				.complete(NuclearScience.ID, "machine_siren", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("P P")
				//
				.addPattern("P P")
				//
				.addKey('P', VoltaicTags.Items.PLATE_BRONZE)
				//
				.complete(NuclearScience.ID, "machine_steamfunnel_bronze", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.steamfunnel), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("P P")
				//
				.addPattern("P P")
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.complete(NuclearScience.ID, "machine_steamfunnel_steel", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.teleporter), 1)
				//
				.addPattern("TCT")
				//
				.addPattern("HEH")
				//
				.addPattern("PDP")
				//
				.addKey('T', VoltaicTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('C', ElectrodynamicsItems.ITEMS_CERAMIC.getValue(SubtypeCeramic.fuse))
				//
				.addKey('H', VoltaicTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('E', VoltaicTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', Items.ENDER_PEARL)
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.complete(NuclearScience.ID, "machine_teleporter", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEM_TURBINE.get(), 1)
				//
				.addPattern(" W ")
				//
				.addPattern("PMP")
				//
				.addPattern(" P ")
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.gold))
				//
				.addKey('P', VoltaicTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(NuclearScience.ID, "machine_turbine", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller), 1)
				//
				.addPattern("#C#")
				//
				.addPattern("CPC")
				//
				.addPattern("#C#")
				//
				.addKey('#', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(NuclearScience.ID, "machine_logisticscontroller", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface), 1)
				//
				.addPattern("GCG")
				//
				.addPattern("MRM")
				//
				.addPattern("GLG")
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('R', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod))
				//
				.addKey('L', NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base))
				//
				.complete(NuclearScience.ID, "machine_fissioninterface", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface), 1)
				//
				.addPattern("GMG")
				//
				.addPattern("CRL")
				//
				.addPattern("GMG")
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.aluminum))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('R', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod))
				//
				.addKey('L', NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base))
				//
				.complete(NuclearScience.ID, "machine_msinterface", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface), 1)
				//
				.addPattern("GEG")
				//
				.addPattern("CMC")
				//
				.addPattern("GRG")
				//
				.addKey('G', NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagneticglass))
				//
				.addKey('E', NuclearScienceItems.ITEMS_ELECTROMAGNET.getValue(SubtypeElectromagent.electromagnet))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('R', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod))
				//
				.complete(NuclearScience.ID, "machine_fusioninterface", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule), 1)
				//
				.addPattern("#D#")
				//
				.addPattern("CRC")
				//
				.addPattern("#L#")
				//
				.addKey('#', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('D', Tags.Items.DUSTS_GLOWSTONE)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('R', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod))
				//
				.addKey('L', NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base))
				//
				.complete(NuclearScience.ID, "machine_controlrodmodule", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule), 1)
				//
				.addPattern("#P#")
				//
				.addPattern("CHC")
				//
				.addPattern("#L#")
				//
				.addKey('#', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_POLONIUM)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('H', Tags.Items.CHESTS)
				//
				.addKey('L', NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base))
				//
				.complete(NuclearScience.ID, "machine_supplymodule", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule), 1)
				//
				.addPattern("#G#")
				//
				.addPattern("#C#")
				//
				.addPattern("#L#")
				//
				.addKey('#', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('G', ElectrodynamicsItems.ITEMS_CUSTOMGLASS.getValue(SubtypeGlass.clear))
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('L', NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base))
				//
				.complete(NuclearScience.ID, "machine_monitormodule", output);

		ShapedCraftingRecipeBuilder.start(NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule), 1)
				//
				.addPattern("#R#")
				//
				.addPattern("#C#")
				//
				.addPattern("#M#")
				//
				.addKey('#', VoltaicTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('R', Items.COMPARATOR)
				//
				.addKey('C', VoltaicTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('M', NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.monitormodule))
				//
				.complete(NuclearScience.ID, "machine_thermometermodule", output);

	}

}
