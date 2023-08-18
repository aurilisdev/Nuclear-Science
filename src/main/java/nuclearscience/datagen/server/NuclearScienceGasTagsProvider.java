package nuclearscience.datagen.server;

import electrodynamics.datagen.server.ElectrodynamicsGasTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceGases;

public class NuclearScienceGasTagsProvider extends ElectrodynamicsGasTagsProvider {

	public NuclearScienceGasTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, References.ID, existingFileHelper);
	}

	@Override
	public void addTags() {

		tag(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE).add(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get());

	}

}
