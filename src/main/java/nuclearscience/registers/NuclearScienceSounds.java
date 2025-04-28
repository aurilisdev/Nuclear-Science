package nuclearscience.registers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.NuclearScience;

public class NuclearScienceSounds {

	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NuclearScience.ID);

	public static final RegistryObject<SoundEvent> SOUND_TURBINE = sound("turbine");
	public static final RegistryObject<SoundEvent> SOUND_NUCLEARBOILER = sound("nuclearboiler");
	public static final RegistryObject<SoundEvent> SOUND_GASCENTRIFUGE = sound("gascentrifuge");
	public static final RegistryObject<SoundEvent> SOUND_SIREN = sound("siren");

	public static final RegistryObject<SoundEvent> SOUND_GEIGERCOUNTER_1 = sound("geigercountersound1");
	public static final RegistryObject<SoundEvent> SOUND_GEIGERCOUNTER_2 = sound("geigercountersound2");
	public static final RegistryObject<SoundEvent> SOUND_GEIGERCOUNTER_3 = sound("geigercountersound3");
	public static final RegistryObject<SoundEvent> SOUND_GEIGERCOUNTER_4 = sound("geigercountersound4");
	public static final RegistryObject<SoundEvent> SOUND_GEIGERCOUNTER_5 = sound("geigercountersound5");
	public static final RegistryObject<SoundEvent> SOUND_GEIGERCOUNTER_6 = sound("geigercountersound6");
	public static final RegistryObject<SoundEvent> SOUND_LOGISTICSCONTROLLER = sound("logisticscontroller");
	public static final RegistryObject<SoundEvent> SOUND_PARTICLE = sound("particle");

	private static RegistryObject<SoundEvent> sound(String name) {
		return SOUNDS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(NuclearScience.ID + ":" + name), 16.0F));
	}
}
