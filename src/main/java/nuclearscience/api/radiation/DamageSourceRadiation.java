package nuclearscience.api.radiation;

import net.minecraft.util.DamageSource;

public class DamageSourceRadiation {
	public static DamageSource INSTANCE = (new DamageSource("radiation")).setDamageBypassesArmor().setDamageIsAbsolute();
}
