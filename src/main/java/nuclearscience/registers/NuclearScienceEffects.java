package nuclearscience.registers;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.api.radiation.EffectRadiation;

public class NuclearScienceEffects {
	
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, References.ID);
	
	public static final RegistryObject<MobEffect> RADIATION = EFFECTS.register("radiation", () -> new EffectRadiation());

}
