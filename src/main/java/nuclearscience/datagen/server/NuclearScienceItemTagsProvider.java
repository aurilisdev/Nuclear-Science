package nuclearscience.datagen.server;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceItemTagsProvider extends ItemTagsProvider {

	public NuclearScienceItemTagsProvider(DataGenerator generator, BlockTagsProvider provider, ExistingFileHelper existingFileHelper) {
		super(generator, provider, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		tag(NuclearScienceTags.Items.CELL_EMPTY).add(NuclearScienceItems.ITEM_CELLEMPTY.get());
		tag(NuclearScienceTags.Items.CELL_HEAVYWATER).add(NuclearScienceItems.ITEM_CELLHEAVYWATER.get());
		tag(NuclearScienceTags.Items.CELL_DEUTERIUM).add(NuclearScienceItems.ITEM_CELLDEUTERIUM.get());
		tag(NuclearScienceTags.Items.CELL_TRITIUM).add(NuclearScienceItems.ITEM_CELLTRITIUM.get());
		tag(NuclearScienceTags.Items.CELL_ANTIMATTER_SMALL).add(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get());
		tag(NuclearScienceTags.Items.CELL_ANTIMATTER_LARGE).add(NuclearScienceItems.ITEM_CELLANTIMATTERLARGE.get());
		tag(NuclearScienceTags.Items.CELL_ANTIMATTER_VERY_LARGE).add(NuclearScienceItems.ITEM_CELLANTIMATTERVERYLARGE.get());
		tag(NuclearScienceTags.Items.CELL_DARK_MATTER).add(NuclearScienceItems.ITEM_CELLDARKMATTER.get());

		tag(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN).add(NuclearScienceItems.ITEM_FUELLEUO2.get());
		tag(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN).add(NuclearScienceItems.ITEM_FUELLEUO2.get());
		tag(NuclearScienceTags.Items.FUELROD_PLUTONIUM).add(NuclearScienceItems.ITEM_FUELPLUTONIUM.get());
		tag(NuclearScienceTags.Items.FUELROD_SPENT).add(NuclearScienceItems.ITEM_FUELSPENT.get());

		tag(NuclearScienceTags.Items.DUST_THORIUM).add(NuclearScienceItems.ITEM_THORIANITEDUST.get());
		tag(NuclearScienceTags.Items.DUST_FISSILE).add(NuclearScienceItems.ITEM_FISSILEDUST.get());

		tag(NuclearScienceTags.Items.SALT_FISSILE).add(NuclearScienceItems.ITEM_FISSILE_SALT.get());

		tag(NuclearScienceTags.Items.OXIDE_PLUTONIUM).add(NuclearScienceItems.ITEM_PLUTONIUMOXIDE.get());
		tag(NuclearScienceTags.Items.OXIDE_ACTINIUM).add(NuclearScienceItems.ITEM_ACTINIUMOXIDE.get());

		tag(NuclearScienceTags.Items.NUGGET_POLONIUM).add(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get());

		tag(NuclearScienceTags.Items.PELLET_URANIUM235).add(NuclearScienceItems.ITEM_URANIUM235.get());
		tag(NuclearScienceTags.Items.PELLET_URANIUM238).add(NuclearScienceItems.ITEM_URANIUM238.get());
		tag(NuclearScienceTags.Items.PELLET_PLUTONIUM).add(NuclearScienceItems.ITEM_PLUTONIUM239.get());
		tag(NuclearScienceTags.Items.PELLET_POLONIUM).add(NuclearScienceItems.ITEM_POLONIUM210.get());
		tag(NuclearScienceTags.Items.PELLET_LIFHT4PUF3).add(NuclearScienceItems.ITEM_LIFHT4PUF3.get());
		tag(NuclearScienceTags.Items.PELLET_FLINAK).add(NuclearScienceItems.ITEM_FLINAK.get());
		tag(NuclearScienceTags.Items.PELLET_ACTINIUM225).add(NuclearScienceItems.ITEM_ACTINIUM225.get());

		tag(NuclearScienceTags.Items.YELLOW_CAKE).add(NuclearScienceItems.ITEM_YELLOWCAKE.get());

	}

}
