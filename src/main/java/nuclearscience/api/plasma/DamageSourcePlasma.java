package nuclearscience.api.plasma;

import net.minecraft.world.damagesource.DamageSource;

public class DamageSourcePlasma {
    public static DamageSource INSTANCE = new DamageSource("plasma").bypassArmor().bypassMagic();
}
