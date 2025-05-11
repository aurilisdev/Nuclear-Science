package nuclearscience.datagen.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.datagen.utils.client.BaseItemModelsProvider;

public class NuclearScienceItemModelsProvider extends BaseItemModelsProvider {

	public NuclearScienceItemModelsProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, existingFileHelper, NuclearScience.ID);
	}

	@Override
	protected void registerModels() {

		layeredItem(NuclearScienceItems.ITEM_URANIUM235, Parent.GENERATED, itemLoc("uranium235"));
		layeredItem(NuclearScienceItems.ITEM_URANIUM238, Parent.GENERATED, itemLoc("uranium238"));
		layeredItem(NuclearScienceItems.ITEM_PLUTONIUM239, Parent.GENERATED, itemLoc("plutonium239"));
		layeredItem(NuclearScienceItems.ITEM_POLONIUM210, Parent.GENERATED, itemLoc("polonium210"));
		layeredItem(NuclearScienceItems.ITEM_POLONIUM210_CHUNK, Parent.GENERATED, itemLoc("polonium210chunk"));
		layeredItem(NuclearScienceItems.ITEM_LIFHT4PUF3, Parent.GENERATED, itemLoc("lifthf4uf4"));
		layeredItem(NuclearScienceItems.ITEM_FLINAK, Parent.GENERATED, itemLoc("flinak"));
		layeredItem(NuclearScienceItems.ITEM_ACTINIUM225, Parent.GENERATED, itemLoc("actinium225"));
		layeredItem(NuclearScienceItems.ITEM_YELLOWCAKE, Parent.GENERATED, itemLoc("yellowcake"));
		layeredItem(NuclearScienceItems.ITEM_FISSILEDUST, Parent.GENERATED, itemLoc("fissiledust"));
		layeredItem(NuclearScienceItems.ITEM_PLUTONIUMOXIDE, Parent.GENERATED, itemLoc("plutoniumoxide"));
		layeredItem(NuclearScienceItems.ITEM_ACTINIUMOXIDE, Parent.GENERATED, itemLoc("actiniumoxide"));
		layeredItem(NuclearScienceItems.ITEM_FISSILE_SALT, Parent.GENERATED, itemLoc("fissilesalt"));
		layeredItem(NuclearScienceItems.ITEM_THORIANITEDUST, Parent.GENERATED, itemLoc("thorianitedust"));

		layeredItem(NuclearScienceItems.ITEM_CELLEMPTY, Parent.GENERATED, itemLoc("cellempty"));
		layeredItem(NuclearScienceItems.ITEM_CELLDEUTERIUM, Parent.GENERATED, itemLoc("celldeuterium"));
		layeredItem(NuclearScienceItems.ITEM_CELLTRITIUM, Parent.GENERATED, itemLoc("celltritium"));
		layeredItem(NuclearScienceItems.ITEM_CELLHEAVYWATER, Parent.GENERATED, itemLoc("cellheavywater"));
		layeredItem(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC, Parent.GENERATED, itemLoc("cellelectromagnetic"));
		layeredItem(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL, Parent.GENERATED, itemLoc("cellantimattersmall"));
		layeredItem(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE, Parent.GENERATED, itemLoc("cellantimatterlarge"));
		layeredItem(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE, Parent.GENERATED, itemLoc("cellantimatterverylarge"));
		layeredItem(NuclearScienceItems.ITEM_CELLDARKMATTER, Parent.GENERATED, itemLoc("celldarkmatter"));
		layeredItem(NuclearScienceItems.ITEM_FUELHEUO2, Parent.GENERATED, itemLoc("fuelheuo2"));
		layeredItem(NuclearScienceItems.ITEM_FUELLEUO2, Parent.GENERATED, itemLoc("fuelleuo2"));
		layeredItem(NuclearScienceItems.ITEM_FUELSPENT, Parent.GENERATED, itemLoc("fuelspent"));
		layeredItem(NuclearScienceItems.ITEM_FUELPLUTONIUM, Parent.GENERATED, itemLoc("fuelplutonium"));

		layeredItem(NuclearScienceItems.ITEM_GEIGERCOUNTER, Parent.GENERATED, itemLoc("geigercounter"));
		layeredItem(NuclearScienceItems.ITEM_ANTIDOTE, Parent.GENERATED, itemLoc("antidote"));
		layeredItem(NuclearScienceItems.ITEM_IODINETABLET, Parent.GENERATED, itemLoc("iodinetablet"));
		layeredItem(NuclearScienceItems.ITEM_CANISTERLEAD, Parent.GENERATED, itemLoc("canisterlead"));
		layeredBuilder(name(NuclearScienceItems.ITEM_FREQUENCYCARD), Parent.GENERATED, itemLoc("frequencycard")).transforms().transform(Perspective.GUI).scale(0.75F).end();

		layeredItem(NuclearScienceItems.ITEM_HAZMATHELMET, Parent.GENERATED, itemLoc("hazmathelmet"));
		layeredItem(NuclearScienceItems.ITEM_HAZMATPLATE, Parent.GENERATED, itemLoc("hazmatplate"));
		layeredItem(NuclearScienceItems.ITEM_HAZMATLEGS, Parent.GENERATED, itemLoc("hazmatlegs"));
		layeredItem(NuclearScienceItems.ITEM_HAZMATBOOTS, Parent.GENERATED, itemLoc("hazmatboots"));

		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET, Parent.GENERATED, itemLoc("reinforcedhazmathelmet"));
		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE, Parent.GENERATED, itemLoc("reinforcedhazmatplate"));
		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS, Parent.GENERATED, itemLoc("reinforcedhazmatlegs"));
		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS, Parent.GENERATED, itemLoc("reinforcedhazmatboots"));

		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler), existingBlock(blockLoc("atomicassemblersingle")));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioncontrolrod), existingBlock(blockLoc("fissioncontrolroditem")));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod), existingBlock(blockLoc("mscontrolroditem")));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge), existingBlock(blockLoc("gascentrifuge")));
		layeredItem(NuclearScienceItems.ITEMS_MOLTENSALTPIPTE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic), Parent.GENERATED, itemLoc("pipe/" + SubtypeMoltenSaltPipe.vanadiumsteelceramic.tag()));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector), existingBlock(blockLoc("particleinjector"))).transforms().transform(Perspective.GUI).rotation(45.0F, 45.0F, 0).scale(0.5F).translation(0.0F, -1.0F, 0.0F);
		simpleBlockItem(NuclearScienceBlocks.BLOCK_TURBINE.get(), existingBlock(blockLoc("turbine")));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.falloutscrubber), existingBlock(blockLoc("falloutscrubber")));

		layeredItem(NuclearScienceItems.ITEMS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), Parent.GENERATED, itemLoc("leadlineddoor"));

		simpleBlockItem(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), existingBlock(blockLoc("radiationshieldingtrapdoor_bottom")));
		layeredItem(NuclearScienceItems.ITEMS_REACTORLOGISTICSCABLE.getValue(SubtypeReactorLogisticsCable.base), Parent.GENERATED, itemLoc("pipe/" + SubtypeReactorLogisticsCable.base.tag()));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.controlrodmodule), existingBlock(blockLoc("controlrodmoduleitem")));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface), existingBlock(blockLoc("fissioninterfaceitem")));
		simpleBlockItem(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface), existingBlock(blockLoc("msinterfaceitem")));

	}

}
