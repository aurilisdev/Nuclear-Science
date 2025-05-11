package nuclearscience.datagen.server;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceFluids;

public class NuclearScienceFluidTagsProvider extends FluidTagsProvider {

	public NuclearScienceFluidTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, NuclearScience.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		tag(NuclearScienceTags.Fluids.IODINE_SOLUTION).add(NuclearScienceFluids.FLUID_IODINESOLUTION.get());
        tag(NuclearScienceTags.Fluids.METHANOL).add(NuclearScienceFluids.FLUID_METHANOL.get());
        tag(NuclearScienceTags.Fluids.DECONTAMINATION_FOAM).add(NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM.get());
        tag(NuclearScienceTags.Fluids.STEAM).add(NuclearScienceFluids.FLUID_STEAM.get());
        tag(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE).add(NuclearScienceFluids.FLUID_URANIUMHEXAFLUORIDE.get());

	}

}
