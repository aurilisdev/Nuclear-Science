package nuclearscience.common.reloadlistener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import electrodynamics.Electrodynamics;
import electrodynamics.prefab.reloadlistener.AbstractReloadListener;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
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
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerBlacklistVals;

public class AtomicAssemblerBlacklistRegister extends AbstractReloadListener<JsonObject> {

	public static AtomicAssemblerBlacklistRegister INSTANCE = null;

	public static final String KEY = "values";
	public static final String FOLDER = "machines";
	public static final String FILE_NAME = "atomic_assembler_blacklist";

	protected static final String JSON_EXTENSION = ".json";
	protected static final int JSON_EXTENSION_LENGTH = JSON_EXTENSION.length();

	private static final Gson GSON = new Gson();

	private final HashSet<Item> blacklistedItems = new HashSet<>();

	private final HashSet<IOptionalNamedTag<Item>> tags = new HashSet<>();

	private final Logger logger = Electrodynamics.LOGGER;

	@Override
	protected JsonObject prepare(IResourceManager manager, IProfiler profiler) {
		JsonObject blacklistedItems = new JsonObject();

		List<ResourceLocation> resources = new ArrayList<>(manager.listResources(FOLDER, AtomicAssemblerBlacklistRegister::isStringJsonFile));

		Collections.reverse(resources);

		JsonArray combinedArray = new JsonArray();

		for (ResourceLocation entry : resources) {

			final String namespace = entry.getNamespace();
			final String filePath = entry.getPath();
			final String dataPath = filePath.substring(FOLDER.length() + 1, filePath.length() - JSON_EXTENSION_LENGTH);

			final ResourceLocation jsonFile = new ResourceLocation(namespace, dataPath);

			try {

				for (IResource resource : manager.getResources(jsonFile)) {

					try (final InputStream inputStream = resource.getInputStream(); final Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {

						final JsonObject json = (JsonObject) JSONUtils.fromJson(GSON, reader, JsonElement.class);
						combinedArray.addAll(json.get(KEY).getAsJsonArray());

					} catch (RuntimeException | IOException exception) {

						logger.error("Data loader for {} could not read data {} from file {} in data pack {}", FOLDER, jsonFile, entry, resource.getLocation(), exception);

					} finally {

						IOUtils.closeQuietly(resource);

					}

				}

			} catch (IOException exception) {

				this.logger.error("Data loader for {} could not read data {} from file {}", FOLDER, jsonFile, entry, exception);

			}

		}

		blacklistedItems.add(KEY, combinedArray);

		return blacklistedItems;
	}

	@Override
	protected void apply(JsonObject json, IResourceManager manager, IProfiler profiler) {
		blacklistedItems.clear();
		tags.clear();
		ArrayList<String> list = GSON.fromJson(json.get(KEY).getAsJsonArray(), ArrayList.class);
		list.forEach(key -> {
			if (key.charAt(0) == '#') {
				tags.add(ItemTags.createOptional(new ResourceLocation(key.substring(1))));
			} else {
				blacklistedItems.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(key)));
			}
		});

	}

	public void generateTagValues() {
		tags.forEach(tag -> {
			for (ItemStack item : Ingredient.of(tag).getItems()) {
				blacklistedItems.add(item.getItem());
			}
		});
		tags.clear();
	}

	public void setClientValues(HashSet<Item> fuels) {
		this.blacklistedItems.clear();
		this.blacklistedItems.addAll(fuels);
	}

	public AtomicAssemblerBlacklistRegister subscribeAsSyncable(final SimpleChannel channel) {
		MinecraftForge.EVENT_BUS.addListener(getDatapackSyncListener(channel));
		return this;
	}

	public HashSet<Item> getBlacklist() {
		return blacklistedItems;
	}

	public boolean isBlacklisted(Item item) {
		return blacklistedItems.contains(item);
	}

	private Consumer<OnDatapackSyncEvent> getDatapackSyncListener(final SimpleChannel channel) {
		return event -> {
			generateTagValues();
			ServerPlayerEntity player = event.getPlayer();
			PacketSetClientAtomicAssemblerBlacklistVals packet = new PacketSetClientAtomicAssemblerBlacklistVals(blacklistedItems);
			PacketTarget target = player == null ? PacketDistributor.ALL.noArg() : PacketDistributor.PLAYER.with(() -> player);
			channel.send(target, packet);
		};
	}

	private static boolean isStringJsonFile(final String filename) {
		return filename.endsWith(FILE_NAME + JSON_EXTENSION);
	}
}