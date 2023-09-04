package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import nuclearscience.References;

public class NuclearScienceDamageTypes {

	public static final ResourceKey<DamageType> PLASMA = create("plasma");
	public static final ResourceKey<DamageType> RADIATION = create("radiation");

	public static ResourceKey<DamageType> create(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(References.ID, name));
	}

	public static void registerTypes(BootstapContext<DamageType> context) {
		context.register(PLASMA, new DamageType("plasma", DamageScaling.NEVER, 0, DamageEffects.BURNING));
		context.register(RADIATION, new DamageType("radiation", DamageScaling.NEVER, 0.1F, DamageEffects.HURT));
	}

}
