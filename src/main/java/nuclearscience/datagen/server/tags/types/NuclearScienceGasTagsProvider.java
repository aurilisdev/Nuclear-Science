package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import electrodynamics.datagen.server.tags.types.ElectrodynamicsGasTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceGases;

public class NuclearScienceGasTagsProvider extends ElectrodynamicsGasTagsProvider {

	public NuclearScienceGasTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, References.ID, existingFileHelper);
	}

	@Override
	public void addTags(Provider provider) {

		tag(NuclearScienceTags.Gases.URANIUM_HEXAFLUORIDE).add(NuclearScienceGases.URANIUM_HEXAFLUORIDE.get());

	}

}
