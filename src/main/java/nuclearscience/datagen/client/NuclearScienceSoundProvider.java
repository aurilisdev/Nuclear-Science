package nuclearscience.datagen.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinition.Sound;
import net.minecraftforge.common.data.SoundDefinition.SoundType;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.fml.RegistryObject;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceSounds;

public class NuclearScienceSoundProvider extends SoundDefinitionsProvider {

	public NuclearScienceSoundProvider(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, References.ID, helper);
	}

	@Override
	public void registerSounds() {
		add(NuclearScienceSounds.SOUND_GASCENTRIFUGE);
		add(NuclearScienceSounds.SOUND_GEIGER);
		add(NuclearScienceSounds.SOUND_NUCLEARBOILER);
		add(NuclearScienceSounds.SOUND_SIREN);
		add(NuclearScienceSounds.SOUND_TURBINE);
	}

	private void add(RegistryObject<SoundEvent> sound) {
		add(sound.get(), SoundDefinition.definition().subtitle("subtitles." + References.ID + "." + sound.getId().getPath()).with(Sound.sound(sound.getId(), SoundType.SOUND)));
	}

}
