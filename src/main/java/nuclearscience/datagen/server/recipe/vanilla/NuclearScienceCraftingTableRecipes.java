package nuclearscience.datagen.server.recipe.vanilla;

import java.util.function.Consumer;

import electrodynamics.common.block.subtype.SubtypeGlass;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.ElectrodynamicsShapedCraftingRecipe;
import electrodynamics.datagen.utils.recipe.ElectrodynamicsShapelessCraftingRecipe;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceCraftingTableRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		ElectrodynamicsShapelessCraftingRecipe.start(NuclearScienceItems.ITEM_ANTIDOTE.get(), 3)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(Items.GLASS_BOTTLE)
				//
				.addIngredient(ItemTags.FISHES)
				//
				.complete(References.ID, "antidote", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blocklead.asItem(), 2)
				//
				.addPattern("LLL")
				//
				.addPattern("CCC")
				//
				.addPattern("LLL")
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.getItem(SubtypeCeramic.plate))
				//
				.complete(References.ID, "leadshielding", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE.get(), 1)
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
				.complete(References.ID, "cellantimatter_large", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE.get(), 1)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.addIngredient(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE)
				//
				.complete(References.ID, "cellantimatter_verylarge", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get(), 1)
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
				.complete(References.ID, "cellelectromagnetic", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_CELLEMPTY.get(), 4)
				//
				.addPattern("GTG")
				//
				.addPattern("T T")
				//
				.addPattern("GTG")
				//
				.addKey('G', Tags.Items.GLASS)
				//
				.addKey('T', ElectrodynamicsTags.Items.INGOT_TIN)
				//
				.complete(References.ID, "cellempty_glass", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_CELLEMPTY.get(), 6)
				//
				.addPattern("GTG")
				//
				.addPattern("T T")
				//
				.addPattern("GTG")
				//
				.addKey('G', ElectrodynamicsItems.getItem(SubtypeGlass.clear))
				//
				.addKey('T', ElectrodynamicsTags.Items.INGOT_TIN)
				//
				.complete(References.ID, "cellempty_clearglass", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockElectromagnet.asItem(), 1)
				//
				.addPattern("BSB")
				//
				.addPattern("SMS")
				//
				.addPattern("BSB")
				//
				.addKey('B', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "electromagnet_steelbronze", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockElectromagnet.asItem(), 15)
				//
				.addPattern("THT")
				//
				.addPattern("HMH")
				//
				.addPattern("THT")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUM)
				//
				.addKey('H', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "electromagnet_hslatitanium", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockElectromagneticBooster.asItem(), 1)
				//
				.addPattern("EGE")
				//
				.addKey('E', NuclearScienceBlocks.blockElectromagnet.asItem())
				//
				.addKey('G', NuclearScienceBlocks.blockElectromagneticGlass.asItem())
				//
				.complete(References.ID, "electromagneticbooster", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(NuclearScienceBlocks.blockElectromagneticGlass.asItem(), 1)
				//
				.addIngredient(NuclearScienceBlocks.blockElectromagnet.asItem())
				//
				.addIngredient(Tags.Items.GLASS)
				//
				.complete(References.ID, "electromagneticglass", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(NuclearScienceBlocks.blockElectromagneticSwitch.asItem(), 1)
				//
				.addIngredient(NuclearScienceBlocks.blockElectromagneticBooster.asItem())
				//
				.addIngredient(ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "electromagneticswitch", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_FREQUENCYCARD.get(), 1)
				//
				.addPattern(" P ")
				//
				.addPattern("WCW")
				//
				.addPattern(" P ")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_IRON)
				//
				.addKey('W', ElectrodynamicsItems.getItem(SubtypeWire.copper))
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "frequencycard_new", consumer);

		ElectrodynamicsShapelessCraftingRecipe.start(NuclearScienceItems.ITEM_FREQUENCYCARD.get(), 1)
				//
				.addIngredient(NuclearScienceItems.ITEM_FREQUENCYCARD.get())
				//
				.complete(References.ID, "frequencycard_reset", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_FUELHEUO2.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GHG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.addKey('H', NuclearScienceTags.Items.PELLET_URANIUM235)
				//
				.complete(References.ID, "fuelrod_uranium_highenrich", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_FUELLEUO2.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GLG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.complete(References.ID, "fuelrod_uranium_lowenrich", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_FUELPLUTONIUM.get(), 1)
				//
				.addPattern("GLG")
				//
				.addPattern("GPG")
				//
				.addPattern("GLG")
				//
				.addKey('G', Tags.Items.GLASS)
				//
				.addKey('L', NuclearScienceTags.Items.PELLET_URANIUM238)
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(References.ID, "fuelrod_plutonium", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.getItem(SubtypeMoltenSaltPipe.vanadiumsteelceramic), 2)
				//
				.addPattern("CCC")
				//
				.addPattern("VVV")
				//
				.addPattern("CCC")
				//
				.addKey('C', ElectrodynamicsItems.getItem(SubtypeCeramic.plate))
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.complete(References.ID, "moltensaltpipe_ceramicvanadium", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_POLONIUM210.get(), 1)
				//
				.addPattern("PP")
				//
				.addPattern("PP")
				//
				.addKey('P', NuclearScienceTags.Items.NUGGET_POLONIUM)
				//
				.complete(References.ID, "poloniumpellet_from_nuggets", consumer);

		addGear(consumer);
		addMachines(consumer);

	}

	public void addGear(Consumer<FinishedRecipe> consumer) {
		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_CANISTERLEAD.get(), 1)
				//
				.addPattern("VLV")
				//
				.addPattern("LCL")
				//
				.addPattern("VLV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_CANISTERREINFORCED.get())
				//
				.complete(References.ID, "canisterlead", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_GEIGERCOUNTER.get(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PBP")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('C', ElectrodynamicsItems.ITEM_COIL.get())
				//
				.complete(References.ID, "geigercounter", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_HAZMATHELMET.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LHL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('H', Items.LEATHER_HELMET)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmathelmet", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_HAZMATPLATE.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LcL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('c', Items.LEATHER_CHESTPLATE)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmatchestplate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_HAZMATLEGS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LlL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('l', Items.LEATHER_LEGGINGS)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmatleggings", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_HAZMATBOOTS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LBL")
				//
				.addPattern("WCW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('B', Items.LEATHER_BOOTS)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "hazmatboots", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LHL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('H', NuclearScienceItems.ITEM_HAZMATHELMET.get())
				//
				.complete(References.ID, "reinforcedhazmathelmet", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LcL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('c', NuclearScienceItems.ITEM_HAZMATPLATE.get())
				//
				.complete(References.ID, "reinforcedhazmatchestplate", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LlL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('l', NuclearScienceItems.ITEM_HAZMATLEGS.get())
				//
				.complete(References.ID, "reinforcedhazmatleggings", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("LBL")
				//
				.addPattern("WLW")
				//
				.addKey('W', ItemTags.WOOL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('B', NuclearScienceItems.ITEM_HAZMATBOOTS.get())
				//
				.complete(References.ID, "reinforcedhazmatboots", consumer);

	}

	public void addMachines(Consumer<FinishedRecipe> consumer) {

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockAtomicAssembler.asItem(), 1)
				//
				.addPattern("CCC")
				//
				.addPattern("SGS")
				//
				.addPattern("SSS")
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('G', NuclearScienceBlocks.blockGasCentrifuge.asItem())
				//
				.complete(References.ID, "atomicassembler", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockChemicalExtractor.asItem(), 1)
				//
				.addPattern("SPS")
				//
				.addPattern("MCM")
				//
				.addPattern("SPS")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "chemicalextractor", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockControlRodAssembly.asItem(), 1)
				//
				.addPattern("SsS")
				//
				.addPattern(" P ")
				//
				.addPattern("SCS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('s', ElectrodynamicsTags.Items.INGOT_SILVER)
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.complete(References.ID, "controlrod", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockFreezePlug.asItem(), 1)
				//
				.addPattern("SCS")
				//
				.addPattern("SCS")
				//
				.addPattern("SBS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('C', Tags.Items.STORAGE_BLOCKS_COPPER)
				//
				.addKey('B', ElectrodynamicsTags.Items.STORAGE_BLOCK_VANADIUMSTEEL)
				//
				.complete(References.ID, "freezeplug", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockFuelReprocessor.asItem(), 1)
				//
				.addPattern("VSV")
				//
				.addPattern("STS")
				//
				.addPattern("VSV")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(References.ID, "fuelreprocessor", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockReactorCore.asItem(), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("MEM")
				//
				.addPattern("PCP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(References.ID, "fissionreactorcore", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockFusionReactorCore.asItem(), 1)
				//
				.addPattern("UEU")
				//
				.addPattern("ECE")
				//
				.addPattern("UEU")
				//
				.addKey('U', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('E', NuclearScienceBlocks.blockElectromagnet.asItem())
				//
				.addKey('C', NuclearScienceBlocks.blockMsrReactorCore.asItem())
				//
				.complete(References.ID, "fusionreactorcore", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockGasCentrifuge.asItem(), 1)
				//
				.addPattern("SES")
				//
				.addPattern("CGC")
				//
				.addPattern("BMB")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('C', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('B', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "gascentrifuge", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockHeatExchanger.asItem(), 1)
				//
				.addPattern("PPP")
				//
				.addPattern("PPP")
				//
				.addPattern("SCS")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_SILVER)
				//
				.addKey('C', Tags.Items.STORAGE_BLOCKS_COPPER)
				//
				.complete(References.ID, "heatexchanger", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockMoltenSaltSupplier.asItem(), 1)
				//
				.addPattern("SVS")
				//
				.addPattern("TTT")
				//
				.addPattern("SVS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.complete(References.ID, "moltensaltsupplier", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockMSRFuelPreProcessor.asItem(), 1)
				//
				.addPattern("VLV")
				//
				.addPattern("LCL")
				//
				.addPattern("VEV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('C', ElectrodynamicsItems.getItem(SubtypeMachine.chemicalmixer))
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "msrfuelpreprocessor", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockMsrReactorCore.asItem(), 1)
				//
				.addPattern("SVS")
				//
				.addPattern("VRV")
				//
				.addPattern("SPS")
				//
				.addKey('S', ElectrodynamicsTags.Items.PLATE_STAINLESSSTEEL)
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('R', NuclearScienceBlocks.blockReactorCore.asItem())
				//
				.addKey('P', NuclearScienceTags.Items.PELLET_PLUTONIUM)
				//
				.complete(References.ID, "msrreactorcore", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockNuclearBoiler.asItem(), 1)
				//
				.addPattern("PCP")
				//
				.addPattern("EFE")
				//
				.addPattern("PMP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.addKey('F', ElectrodynamicsItems.getItem(SubtypeMachine.electricarcfurnace))
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "nuclearboiler", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockParticleInjector.asItem(), 1)
				//
				.addPattern("MTM")
				//
				.addPattern("UDU")
				//
				.addPattern("MTM")
				//
				.addKey('M', NuclearScienceBlocks.blockElectromagnet.asItem())
				//
				.addKey('T', ElectrodynamicsItems.getItem(SubtypeMachine.upgradetransformer))
				//
				.addKey('U', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('D', Items.DISPENSER)
				//
				.complete(References.ID, "particleinjector", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockQuantumCapacitor.asItem(), 1)
				//
				.addPattern("DCD")
				//
				.addPattern("RDR")
				//
				.addPattern("DCD")
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ULTIMATE)
				//
				.addKey('R', NuclearScienceBlocks.blockFusionReactorCore.asItem())
				//
				.complete(References.ID, "quantumcapacitor", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockRadioactiveProcessor.asItem(), 1)
				//
				.addPattern("VTV")
				//
				.addPattern("VMV")
				//
				.addPattern("VCV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('T', ElectrodynamicsItems.ITEM_TITANIUM_COIL.get())
				//
				.addKey('M', ElectrodynamicsItems.getItem(SubtypeMachine.chemicalmixer))
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.complete(References.ID, "radioactiveprocessor", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockRadioisotopeGenerator.asItem(), 1)
				//
				.addPattern("VCV")
				//
				.addPattern("LEL")
				//
				.addPattern("VCV")
				//
				.addKey('V', ElectrodynamicsTags.Items.PLATE_VANADIUMSTEEL)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_ADVANCED)
				//
				.addKey('L', ElectrodynamicsTags.Items.PLATE_LEAD)
				//
				.addKey('E', NuclearScienceTags.Items.CELL_EMPTY)
				//
				.complete(References.ID, "radioisotopegenerator", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockSiren.asItem(), 1)
				//
				.addPattern("NPN")
				//
				.addKey('N', Items.NOTE_BLOCK)
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_BRONZE)
				//
				.complete(References.ID, "siren", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockTeleporter.asItem(), 1)
				//
				.addPattern("TCT")
				//
				.addPattern("HEH")
				//
				.addPattern("PDP")
				//
				.addKey('T', ElectrodynamicsTags.Items.PLATE_TITANIUMCARBIDE)
				//
				.addKey('C', ElectrodynamicsItems.getItem(SubtypeCeramic.fuse))
				//
				.addKey('H', ElectrodynamicsTags.Items.PLATE_HSLASTEEL)
				//
				.addKey('E', ElectrodynamicsTags.Items.CIRCUITS_ELITE)
				//
				.addKey('P', Items.ENDER_PEARL)
				//
				.addKey('D', NuclearScienceTags.Items.CELL_DARK_MATTER)
				//
				.complete(References.ID, "teleporter", consumer);

		ElectrodynamicsShapedCraftingRecipe.start(NuclearScienceBlocks.blockTurbine.asItem(), 1)
				//
				.addPattern(" W ")
				//
				.addPattern("PMP")
				//
				.addPattern(" P ")
				//
				.addKey('W', ElectrodynamicsItems.getItem(SubtypeWire.gold))
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "turbine", consumer);

	}

}
