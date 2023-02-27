package nuclearscience.api.radiation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.world.item.Item;

public class RadiationRegister {
	
	private static final HashMap<Item, IRadioactiveObject> map = new HashMap<>();
	
	public static final IRadioactiveObject NULL = new FieldRadioactiveObject(0);

	public static IRadioactiveObject get(Item object) {
		IRadioactiveObject ret = map.get(object);
		if (ret == null) {
			return NULL;
		}
		return ret;
	}

	public static IRadioactiveObject register(Item key, IRadioactiveObject value) {
		return map.put(key, value);
	}
	
	public static List<Item> getRadioactiveItems() {
		return new ArrayList<>(map.keySet());
	}
}
