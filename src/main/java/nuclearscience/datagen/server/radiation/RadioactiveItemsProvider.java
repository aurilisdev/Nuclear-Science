package nuclearscience.datagen.server.radiation;

import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import electrodynamics.common.tags.ElectrodynamicsTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.References;
import nuclearscience.common.reloadlistener.RadioactiveItemLoader;
import nuclearscience.common.tags.NuclearScienceTags;

public class RadioactiveItemsProvider implements IDataProvider {
	
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

	public static final String LOC = "data/" + References.ID + "/" + RadioactiveItemLoader.FOLDER + "/" + RadioactiveItemLoader.FILE_NAME;

	private final DataGenerator dataGenerator;

	public RadioactiveItemsProvider(DataGenerator gen) {
		dataGenerator = gen;
	}

	@Override
	public void run(DirectoryCache cache) throws IOException {
		JsonObject json = new JsonObject();
		getRadioactiveItems(json);

		Path parent = dataGenerator.getOutputFolder().resolve(LOC + ".json");
		try {

			IDataProvider.save(GSON, cache, json, parent);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getRadioactiveItems(JsonObject json) {

		addTag(NuclearScienceTags.Items.PELLET_URANIUM235, 1000, json);
		addTag(NuclearScienceTags.Items.PELLET_URANIUM238, 500, json);
		addTag(NuclearScienceTags.Items.PELLET_PLUTONIUM, 4500, json);
		addTag(NuclearScienceTags.Items.PELLET_POLONIUM, 2500, json);
		addTag(NuclearScienceTags.Items.PELLET_ACTINIUM225, 5000, json);

		addTag(NuclearScienceTags.Items.NUGGET_POLONIUM, 1500, json);

		addTag(NuclearScienceTags.Items.FUELROD_URANIUM_HIGH_EN, 3000, json);
		addTag(NuclearScienceTags.Items.FUELROD_URANIUM_LOW_EN, 2000, json);
		addTag(NuclearScienceTags.Items.FUELROD_SPENT, 3500, json);
		addTag(NuclearScienceTags.Items.FUELROD_PLUTONIUM, 2500, json);

		addTag(NuclearScienceTags.Items.YELLOW_CAKE, 300, json);
		addTag(NuclearScienceTags.Items.DUST_FISSILE, 2000, json);
		addTag(NuclearScienceTags.Items.SALT_FISSILE, 200, json);
		addTag(NuclearScienceTags.Items.OXIDE_PLUTONIUM, 4000, json);
		addTag(NuclearScienceTags.Items.DUST_THORIUM, 2000, json);
		addTag(NuclearScienceTags.Items.OXIDE_ACTINIUM, 400, json);

		addTag(ElectrodynamicsTags.Items.ORE_THORIUM, 500, json);
		addTag(ElectrodynamicsTags.Items.ORE_URANIUM, 100, json);

	}

	private void addItem(Item item, double radiationStrength, JsonObject json) {
		json.addProperty(ForgeRegistries.ITEMS.getKey(item).toString(), radiationStrength);
	}

	private void addTag(INamedTag<Item> fluid, double radiationStrength, JsonObject json) {
		json.addProperty("#" + fluid.getName().toString(), radiationStrength);
	}

	@Override
	public String getName() {
		return "Nuclear Science Radioactive Items Provider";
	}

}
