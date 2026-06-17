package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonObject;

import net.minecraft.data.PackOutput;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import voltaic.datagen.utils.server.radiation.BaseRadioactiveGasesProvider;

public class NuclearScienceRadioactiveGasesProvider extends BaseRadioactiveGasesProvider {

    public NuclearScienceRadioactiveGasesProvider(PackOutput output) {
	super(output, NuclearScience.ID);
    }

    @Override
    public void getRadioactiveGases(JsonObject json) {

	addTag(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE, 1, 1, json);

    }

}
