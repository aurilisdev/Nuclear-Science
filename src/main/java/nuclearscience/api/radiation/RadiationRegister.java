package nuclearscience.api.radiation;

import java.util.HashMap;

public class RadiationRegister {
    private static final HashMap<Object, IRadioactiveObject> map = new HashMap<>();
    public static final IRadioactiveObject NULL = new FieldRadioactiveObject(0);

    public static IRadioactiveObject get(Object object) {
	IRadioactiveObject ret = map.get(object);
	if (ret == null) {
	    return NULL;
	}
	return ret;
    }

    public static IRadioactiveObject register(Object key, IRadioactiveObject value) {
	return map.put(key, value);
    }
}
