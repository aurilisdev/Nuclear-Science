package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveItemsProvider;

public class RadioactiveItemsProvider extends BaseRadioactiveItemsProvider {

	public RadioactiveItemsProvider(DataGenerator gen) {
		super(gen, NuclearScience.ID);
	}

	public void getRadioactiveItems(JsonObject json) {

		addTag(NuclearScienceTags.Items.PELLET_URANIUM235, 1000, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_URANIUM238, 500, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_PLUTONIUM, 4500, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_POLONIUM, 2500, 1, json);
		addTag(NuclearScienceTags.Items.PELLET_ACTINIUM225, 5000, 1, json);

		addTag(NuclearScienceTags.Items.NUGGET_POLONIUM, 625, 1, json);

		addTag(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN, 3000, 1, json);
		addTag(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN, 2000, 1, json);
		addTag(NuclearScienceTags.Items.FUELROD_SPENT, 3500, 1, json);
		addTag(NuclearScienceTags.Items.FUELROD_PLUTONIUM, 2500, 1, json);

		addTag(NuclearScienceTags.Items.YELLOW_CAKE, 300, 1, json);
		addTag(NuclearScienceTags.Items.DUST_FISSILE, 2000, 1, json);
		addTag(NuclearScienceTags.Items.SALT_FISSILE, 200, 1, json);
		addTag(NuclearScienceTags.Items.OXIDE_PLUTONIUM, 4000, 1, json);
		addTag(NuclearScienceTags.Items.DUST_THORIUM, 2000, 1, json);
		addTag(NuclearScienceTags.Items.OXIDE_ACTINIUM, 400, 1, json);

		addTag(VoltaicTags.Items.ORE_THORIUM, 500, 1, json);
		addTag(VoltaicTags.Items.ORE_URANIUM, 100, 1, json);

		addTag(VoltaicTags.Items.RAW_ORE_THORIUM, 150, 1, json);
		addTag(VoltaicTags.Items.RAW_ORE_URANIUM, 50, 1, json);

		addTag(VoltaicTags.Items.BLOCK_RAW_ORE_THORIUM, 500, 1, json);
		addTag(VoltaicTags.Items.BLOCK_RAW_ORE_URANIUM, 450, 1, json);

	}

}
