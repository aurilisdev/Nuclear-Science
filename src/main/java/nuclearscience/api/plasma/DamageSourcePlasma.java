package nuclearscience.api.plasma;

import net.minecraft.util.DamageSource;

public class DamageSourcePlasma {
	public static DamageSource INSTANCE = new DamageSource("plasma").setDamageBypassesArmor().setDamageIsAbsolute();
}
