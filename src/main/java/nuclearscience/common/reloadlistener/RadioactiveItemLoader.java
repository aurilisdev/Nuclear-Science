package nuclearscience.common.reloadlistener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationRegister.RadioactiveSource;
import nuclearscience.common.packet.type.client.PacketSetClientRadRegisterItemVals;

public class RadioactiveItemLoader extends SimplePreparableReloadListener<JsonObject> {

	public static RadioactiveItemLoader INSTANCE = null;

	public static final String FOLDER = "radiation";
	public static final String FILE_NAME = "radioactive_items";

	protected static final String JSON_EXTENSION = ".json";
	protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

	private static final Gson GSON = new Gson();

	private final HashMap<TagKey<Item>, Double> tags = new HashMap<>();

	private final Logger logger = Electrodynamics.LOGGER;

	@Override
	protected JsonObject prepare(ResourceManager manager, ProfilerFiller profiler) {
		JsonObject combined = new JsonObject();

		List<ResourceLocation> resources = new ArrayList<>(manager.listResources(FOLDER, RadioactiveItemLoader::isStringJsonFile));

		Collections.reverse(resources);

		for (ResourceLocation entry : resources) {
			final String namespace = entry.getNamespace();
			final String filePath = entry.getPath();
			final String dataPath = filePath.substring(FOLDER.length() + 1, filePath.length() - JSON_EXTENSION_LENGTH);

			final ResourceLocation jsonFile = new ResourceLocation(namespace, dataPath);

			try {

				for (Resource resource : manager.getResources(entry)) {

					try (final InputStream inputStream = resource.getInputStream(); final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {
						
						final JsonObject json = (JsonObject) GsonHelper.fromJson(GSON, reader, JsonElement.class);
						
						logger.debug(json);

						json.entrySet().forEach(set -> {

							if (combined.has(set.getKey())) {
								combined.remove(set.getKey());
							}

							combined.add(set.getKey(), set.getValue());
						});

					} catch (RuntimeException | IOException exception) {
						
						logger.error("Data loader for {} could not read data {} from file {} in data pack {}", FOLDER, jsonFile, entry, resource.getSourceName(), exception);
						
					} finally {

						IOUtils.closeQuietly(resource);

					}

				}

			} catch (IOException exception) {

				this.logger.error("Data loader for {} could not read data {} from file {}", FOLDER, jsonFile, entry, exception);

			}

		}

		return combined;
	}

	@Override
	protected void apply(JsonObject json, ResourceManager manager, ProfilerFiller profiler) {
		tags.clear();

		json.entrySet().forEach(set -> {

			String key = set.getKey();
			Double value = set.getValue().getAsDouble();

			if (key.contains("#")) {

				key = key.substring(1);

				tags.put(ItemTags.create(new ResourceLocation(key)), value);

			} else {

				RadiationRegister.register(ForgeRegistries.ITEMS.getValue(new ResourceLocation(key)), new FieldRadioactiveObject(value));

			}

		});

	}

	public void generateTagValues() {

		tags.forEach((tag, value) -> {
			ForgeRegistries.ITEMS.tags().getTag(tag).forEach(item -> {

				if (RadiationRegister.get(item).isNull()) {
					RadiationRegister.register(item, new FieldRadioactiveObject(value));
				}

			});
		});

		tags.clear();
	}

	public RadioactiveItemLoader subscribeAsSyncable(final SimpleChannel channel) {
		MinecraftForge.EVENT_BUS.addListener(getDatapackSyncListener(channel));
		return this;
	}

	private Consumer<OnDatapackSyncEvent> getDatapackSyncListener(final SimpleChannel channel) {
		return event -> {
			generateTagValues();
			ServerPlayer player = event.getPlayer();
			HashMap<Item, Double> items = new HashMap<>();
			RadiationRegister.getMapForType(RadioactiveSource.ITEM).forEach((item, radSource) -> {

				items.put((Item) item, radSource.getRadiationStrength());

			});
			PacketSetClientRadRegisterItemVals packet = new PacketSetClientRadRegisterItemVals(items);
			PacketTarget target = player == null ? PacketDistributor.ALL.noArg() : PacketDistributor.PLAYER.with(() -> player);
			channel.send(target, packet);
		};
	}

	private static boolean isStringJsonFile(final String filename) {
		return filename.endsWith(FILE_NAME + JSON_EXTENSION);
	}

}