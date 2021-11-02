package nuclearscience;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegister {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.ID);
    public static final RegistryObject<SoundEvent> SOUND_TURBINE = SOUNDS.register("turbine",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":turbine")));
    public static final RegistryObject<SoundEvent> SOUND_NUCLEARBOILER = SOUNDS.register("nuclearboiler",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":nuclearboiler")));
    public static final RegistryObject<SoundEvent> SOUND_GASCENTRIFUGE = SOUNDS.register("gascentrifuge",
	    () -> new SoundEvent(new ResourceLocation(References.ID + ":gascentrifuge")));
}
