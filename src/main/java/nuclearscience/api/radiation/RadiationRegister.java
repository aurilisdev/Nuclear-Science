package nuclearscience.api.radiation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javax.annotation.Nonnull;

import electrodynamics.Electrodynamics;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;

public class RadiationRegister {

	/*
	 * This allows us to better categorize what is radioactive in case we need to reference all types of an object, and also makes way
	 * for things like datapack support for radioactive items and what not
	 */
	private static final HashMap<RadioactiveSource, HashMap<Object, IRadioactiveObject>> MASTER_REGISTER = new HashMap<>();

	public static void init() {

		for (RadioactiveSource source : RadioactiveSource.values()) {

			MASTER_REGISTER.put(source, MASTER_REGISTER.getOrDefault(source, new HashMap<>()));

		}

	}

	public static IRadioactiveObject get(Object object) {

		for (RadioactiveSource source : RadioactiveSource.values()) {

			if (source.test(object)) {

				return MASTER_REGISTER.get(source).getOrDefault(object, IRadioactiveObject.NULL);

			}

		}

		return IRadioactiveObject.NULL;

	}

	public static IRadioactiveObject register(Object key, IRadioactiveObject value) {

		for (RadioactiveSource source : RadioactiveSource.values()) {

			if (source.test(key)) {

				return MASTER_REGISTER.getOrDefault(source, new HashMap<>()).put(key, value);

			}

		}

		Electrodynamics.LOGGER.atFatal().log("Somehow you have managed to pass in an radiation source that is not an instance of Object");

		return IRadioactiveObject.NULL;
	}

	public static HashMap<Object, IRadioactiveObject> getMapForType(@Nonnull RadioactiveSource source) {
		return MASTER_REGISTER.getOrDefault(source, new HashMap<>());
	}

	public static List<Item> getRadioactiveItems() {
		ArrayList<Item> items = new ArrayList<>();
		getMapForType(RadioactiveSource.ITEM).keySet().forEach(item -> {
			items.add((Item) item);
		});
		return items;
	}

	/*
	 * Simple category enum allowing for easy expansion of categories
	 */
	public static enum RadioactiveSource {

		ITEM(obj -> obj instanceof Item), BLOCK(obj -> obj instanceof Block), ENTITY(obj -> obj instanceof Entity), TILE(obj -> obj instanceof TileEntity), MISC(obj -> true);

		private final Predicate<Object> isClass;

		private RadioactiveSource(Predicate<Object> isClass) {
			this.isClass = isClass;
		}

		public boolean test(Object obj) {
			return isClass.test(obj);
		}

	}

}