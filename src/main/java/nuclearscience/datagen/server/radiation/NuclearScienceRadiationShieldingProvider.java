package nuclearscience.datagen.server.radiation;

import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.registers.NuclearScienceBlocks;
import voltaic.datagen.utils.server.radiation.BaseRadiationShieldingProvider;

public class NuclearScienceRadiationShieldingProvider extends BaseRadiationShieldingProvider {

    public NuclearScienceRadiationShieldingProvider(DataGenerator gen) {
        super(gen, NuclearScience.ID);
    }

    @Override
    public void getRadiationShielding(JsonObject json) {
	addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.base), 0.00, 1,
		json);
	addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), 0.03, 1,
		json);
	addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), 0.02, 1,
		json);
	addBlock(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass), 0.10, 1,
		json);

    }


}
