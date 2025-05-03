package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveFluidsProvider;

public class NuclearScienceRadioactiveFluidsProvider extends BaseRadioactiveFluidsProvider {

    public NuclearScienceRadioactiveFluidsProvider(DataGenerator gen) {
        super(gen, NuclearScience.ID);
    }

    @Override
    public void getRadioactiveItems(JsonObject json) {

        addTag(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE, 1, 1, json);

    }

}
