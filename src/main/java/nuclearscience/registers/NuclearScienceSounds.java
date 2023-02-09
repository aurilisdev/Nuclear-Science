package nuclearscience.registers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;

public class NuclearScienceSounds {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.ID);

	public static final RegistryObject<SoundEvent> SOUND_TURBINE = sound("turbine");
	public static final RegistryObject<SoundEvent> SOUND_NUCLEARBOILER = sound("nuclearboiler");
	public static final RegistryObject<SoundEvent> SOUND_GASCENTRIFUGE = sound("gascentrifuge");
	public static final RegistryObject<SoundEvent> SOUND_SIREN = sound("siren");
	public static final RegistryObject<SoundEvent> SOUND_GEIGER = sound("geiger");

	private static RegistryObject<SoundEvent> sound(String name) {
		return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(References.ID + ":" + name)));
	}
}
