package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import nuclearscience.NuclearScience;

public class NuclearScienceDamageTypes {

    public static final ResourceKey<DamageType> PLASMA = create("plasma");

    public static ResourceKey<DamageType> create(String name) {
	return ResourceKey.create(Registries.DAMAGE_TYPE, NuclearScience.rl(name));
    }

    public static void registerTypes(BootstrapContext<DamageType> context) {
	context.register(PLASMA, new DamageType("plasma", DamageScaling.NEVER, 0, DamageEffects.BURNING));
    }

}
