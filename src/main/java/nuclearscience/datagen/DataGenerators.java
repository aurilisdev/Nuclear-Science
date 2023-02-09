package nuclearscience.datagen;

import nuclearscience.References;
import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider.Locale;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nuclearscience.datagen.client.NuclearScienceBlockStateProvider;
import nuclearscience.datagen.client.NuclearScienceItemModelsProvider;
import nuclearscience.datagen.client.NuclearScienceLangKeyProvider;
import nuclearscience.datagen.client.NuclearScienceSoundProvider;
import nuclearscience.datagen.server.NuclearScienceBlockTagsProvider;
import nuclearscience.datagen.server.NuclearScienceFluidTagsProvider;
import nuclearscience.datagen.server.NuclearScienceItemTagsProvider;
import nuclearscience.datagen.server.NuclearScienceLootTablesProvider;
import nuclearscience.datagen.server.recipe.NuclearScienceRecipeProvider;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = References.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {
			
			NuclearScienceBlockTagsProvider blockProvider = new NuclearScienceBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(true, blockProvider);
			generator.addProvider(true, new NuclearScienceItemTagsProvider(generator, blockProvider, event.getExistingFileHelper()));
			generator.addProvider(true, new NuclearScienceFluidTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new NuclearScienceLootTablesProvider(generator));
			generator.addProvider(true, new NuclearScienceRecipeProvider(generator));
			
		}
		if (event.includeClient()) {
			generator.addProvider(true, new NuclearScienceBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new NuclearScienceItemModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(true, new NuclearScienceLangKeyProvider(generator, Locale.EN_US));
			generator.addProvider(true, new NuclearScienceSoundProvider(generator, event.getExistingFileHelper()));
		}
	}

}
