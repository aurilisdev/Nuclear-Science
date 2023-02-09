package nuclearscience.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsItemModelsProvider;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceItemModelsProvider extends ElectrodynamicsItemModelsProvider {

	public NuclearScienceItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, existingFileHelper, References.ID);
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
		layeredItem(NuclearScienceItems.ITEM_YELLOWCAKE, Parent.GENERATED, itemLoc("yellowcake"));
		layeredItem(NuclearScienceItems.ITEM_FISSILEDUST, Parent.GENERATED, itemLoc("fissiledust"));
		layeredItem(NuclearScienceItems.ITEM_PLUTONIUMOXIDE, Parent.GENERATED, itemLoc("plutoniumoxide"));
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
		layeredItem(NuclearScienceItems.ITEM_CANISTERLEAD, Parent.GENERATED, itemLoc("canisterlead"));
		layeredBuilder(name(NuclearScienceItems.ITEM_FREQUENCYCARD), Parent.GENERATED, itemLoc("frequencycard")).transforms().transform(TransformType.GUI).scale(0.75F).end();

		layeredItem(NuclearScienceItems.ITEM_HAZMATHELMET, Parent.GENERATED, itemLoc("hazmathelmet"));
		layeredItem(NuclearScienceItems.ITEM_HAZMATPLATE, Parent.GENERATED, itemLoc("hazmatplate"));
		layeredItem(NuclearScienceItems.ITEM_HAZMATLEGS, Parent.GENERATED, itemLoc("hazmatlegs"));
		layeredItem(NuclearScienceItems.ITEM_HAZMATBOOTS, Parent.GENERATED, itemLoc("hazmatboots"));

		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATHELMET, Parent.GENERATED, itemLoc("reinforcedhazmathelmet"));
		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATPLATE, Parent.GENERATED, itemLoc("reinforcedhazmatplate"));
		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATLEGS, Parent.GENERATED, itemLoc("reinforcedhazmatlegs"));
		layeredItem(NuclearScienceItems.ITEM_REINFORCEDHAZMATBOOTS, Parent.GENERATED, itemLoc("reinforcedhazmatboots"));

		simpleBlockItem(NuclearScienceBlocks.blockAtomicAssembler, existingBlock(blockLoc("atomicassemblersingle")));
		simpleBlockItem(NuclearScienceBlocks.blockControlRodAssembly, existingBlock(blockLoc("controlrodassembly"))).transforms().transform(TransformType.GUI).rotation(30.0F, 225.0F, 0.0F).translation(0.0F, -0.9F, 0.0F).scale(0.5F).end();
		simpleBlockItem(NuclearScienceBlocks.blockGasCentrifuge, existingBlock(blockLoc("gascentrifuge")));
		layeredItem(NuclearScienceItems.getItem(SubtypeMoltenSaltPipe.vanadiumsteelceramic), Parent.GENERATED, itemLoc("pipe/" + SubtypeMoltenSaltPipe.vanadiumsteelceramic.tag()));
		simpleBlockItem(NuclearScienceBlocks.blockParticleInjector, existingBlock(blockLoc("particleinjector"))).transforms().transform(TransformType.GUI).rotation(45.0F, 45.0F, 0).scale(0.5F).translation(0.0F, -1.0F, 0.0F);
		simpleBlockItem(NuclearScienceBlocks.blockTurbine, existingBlock(blockLoc("turbine")));

	}

}
