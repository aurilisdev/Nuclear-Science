package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceFluids;

public class NuclearScienceFluidTagsProvider extends FluidTagsProvider {

    public NuclearScienceFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NuclearScience.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {

        tag(NuclearScienceTags.Fluids.IODINE_SOLUTION).add(NuclearScienceFluids.FLUID_IODINESOLUTION.get());
        tag(NuclearScienceTags.Fluids.METHANOL).add(NuclearScienceFluids.FLUID_METHANOL.get());
        tag(NuclearScienceTags.Fluids.DECONTAMINATION_FOAM).add(NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM.get());

    }

}
