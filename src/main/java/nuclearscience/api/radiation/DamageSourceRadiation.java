package nuclearscience.api.radiation;

import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceRadiation {
	public static DamageSource INSTANCE = new DamageSource("radiation").bypassArmor().bypassMagic();
}
