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
import electrodynamics.prefab.reloadlistener.AbstractReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags.IOptionalNamedTag;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.PacketTarget;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationRegister.RadioactiveSource;
import nuclearscience.common.packet.type.client.PacketSetClientRadRegisterItemVals;

public class RadioactiveItemLoader extends AbstractReloadListener<JsonObject> {

	public static RadioactiveItemLoader INSTANCE = null;

	public static final String FOLDER = "radiation";
	public static final String FILE_NAME = "radioactive_items";

	protected static final String JSON_EXTENSION = ".json";
	protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

	private static final Gson GSON = new Gson();

	private final HashMap<IOptionalNamedTag<Item>, Double> tags = new HashMap<>();

	private final Logger logger = Electrodynamics.LOGGER;

	@Override
	protected JsonObject prepare(IResourceManager manager, IProfiler profiler) {
		JsonObject combined = new JsonObject();

		List<ResourceLocation> resources = new ArrayList<>(manager.listResources(FOLDER, RadioactiveItemLoader::isStringJsonFile));

		Collections.reverse(resources);

		for (ResourceLocation entry : resources) {
			final String namespace = entry.getNamespace();
			final String filePath = entry.getPath();
			final String dataPath = filePath.substring(FOLDER.length() + 1, filePath.length() - JSON_EXTENSION_LENGTH);

			final ResourceLocation jsonFile = new ResourceLocation(namespace, dataPath);

			try {

				for (IResource resource : manager.getResources(entry)) {

					try (final InputStream inputStream = resource.getInputStream(); final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {
						
						final JsonObject json = (JsonObject) JSONUtils.fromJson(GSON, reader, JsonElement.class);

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
	protected void apply(JsonObject json, IResourceManager manager, IProfiler profiler) {
		tags.clear();

		json.entrySet().forEach(set -> {

			String key = set.getKey();
			Double value = set.getValue().getAsDouble();

			if (key.contains("#")) {

				key = key.substring(1);

				tags.put(ItemTags.createOptional(new ResourceLocation(key)), value);

			} else {

				RadiationRegister.register(ForgeRegistries.ITEMS.getValue(new ResourceLocation(key)), new FieldRadioactiveObject(value));

			}

		});

	}

	public void generateTagValues() {

		tags.forEach((tag, value) -> {

			ItemTags.getAllTags().getTag(tag.getName()).getValues().forEach(item -> {
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
			ServerPlayerEntity player = event.getPlayer();
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