package nuclearscience.datagen.server.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.datagen.server.tags.types.NuclearScienceBlockTagsProvider;
import nuclearscience.datagen.server.tags.types.NuclearScienceDamageTagsProvider;
import nuclearscience.datagen.server.tags.types.NuclearScienceFluidTagsProvider;
import nuclearscience.datagen.server.tags.types.NuclearScienceGasTagsProvider;
import nuclearscience.datagen.server.tags.types.NuclearScienceItemTagsProvider;

public class NuclearScienceTagsProvider {
	
	public static void addTagProviders(DataGenerator generator, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
		NuclearScienceBlockTagsProvider blockProvider = new NuclearScienceBlockTagsProvider(output, lookupProvider, helper);
		generator.addProvider(true, blockProvider);
		generator.addProvider(true, new NuclearScienceItemTagsProvider(output, lookupProvider, blockProvider, helper));
		generator.addProvider(true, new NuclearScienceFluidTagsProvider(output, lookupProvider, helper));
		generator.addProvider(true, new NuclearScienceGasTagsProvider(output, lookupProvider, helper));
		generator.addProvider(true, new NuclearScienceDamageTagsProvider(output, lookupProvider, helper));
	}

}
