package nuclearscience.datagen.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceSounds;
import voltaic.datagen.utils.client.BaseSoundProvider;

public class NuclearScienceSoundProvider extends BaseSoundProvider {

	public NuclearScienceSoundProvider(DataGenerator gen, ExistingFileHelper helper) {
		super(gen, helper, NuclearScience.ID);
	}

	@Override
	public void registerSounds() {
		add(NuclearScienceSounds.SOUND_GASCENTRIFUGE);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_1);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_2);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_3);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_4);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_5);
		add(NuclearScienceSounds.SOUND_GEIGERCOUNTER_6);
		add(NuclearScienceSounds.SOUND_NUCLEARBOILER);
		add(NuclearScienceSounds.SOUND_SIREN);
		add(NuclearScienceSounds.SOUND_TURBINE);
		add(NuclearScienceSounds.SOUND_LOGISTICSCONTROLLER);
		add(NuclearScienceSounds.SOUND_PARTICLE);
	}

}
