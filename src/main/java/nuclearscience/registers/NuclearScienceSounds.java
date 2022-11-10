package nuclearscience.registers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;

public class NuclearScienceSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.ID);
	public static final RegistryObject<SoundEvent> SOUND_TURBINE = SOUNDS.register("turbine", () -> new SoundEvent(new ResourceLocation(References.ID + ":turbine")));
	public static final RegistryObject<SoundEvent> SOUND_NUCLEARBOILER = SOUNDS.register("nuclearboiler", () -> new SoundEvent(new ResourceLocation(References.ID + ":nuclearboiler")));
	public static final RegistryObject<SoundEvent> SOUND_GASCENTRIFUGE = SOUNDS.register("gascentrifuge", () -> new SoundEvent(new ResourceLocation(References.ID + ":gascentrifuge")));
	public static final RegistryObject<SoundEvent> SOUND_SIREN = SOUNDS.register("siren", () -> new SoundEvent(new ResourceLocation(References.ID + ":siren")));
	public static final RegistryObject<SoundEvent> SOUND_GEIGER = SOUNDS.register("geiger", () -> new SoundEvent(new ResourceLocation(References.ID + ":geiger")));
}
