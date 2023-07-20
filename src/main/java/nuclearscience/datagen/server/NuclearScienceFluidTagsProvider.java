package nuclearscience.datagen.server;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceFluids;

public class NuclearScienceFluidTagsProvider extends FluidTagsProvider {

	public NuclearScienceFluidTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

		tag(NuclearScienceTags.Fluids.AMMONIA).add(NuclearScienceFluids.fluidAmmonia);
		// tag(NuclearScienceTags.Fluids.URANIUM_HEXAFLUORIDE).add(NuclearScienceFluids.fluidUraniumHexafluoride);

	}

}
