package nuclearscience.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import nuclearscience.NuclearScience;
import nuclearscience.datagen.client.NuclearScienceBlockStateProvider;
import nuclearscience.datagen.client.NuclearScienceItemModelsProvider;
import nuclearscience.datagen.client.NuclearScienceLangKeyProvider;
import nuclearscience.datagen.client.NuclearScienceSoundProvider;
import nuclearscience.datagen.server.NuclearScienceBlockTagsProvider;
import nuclearscience.datagen.server.NuclearScienceFluidTagsProvider;
import nuclearscience.datagen.server.NuclearScienceItemTagsProvider;
import nuclearscience.datagen.server.NuclearScienceLootTablesProvider;
import nuclearscience.datagen.server.radiation.NuclearScienceRadiationShieldingProvider;
import nuclearscience.datagen.server.radiation.NuclearScienceRadioactiveFluidsProvider;
import nuclearscience.datagen.server.radiation.RadioactiveItemsProvider;
import nuclearscience.datagen.server.recipe.NuclearScienceRecipeProvider;
import voltaic.datagen.utils.client.BaseLangKeyProvider.Locale;

@Mod.EventBusSubscriber(modid = NuclearScience.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();
		if (event.includeServer()) {

			NuclearScienceBlockTagsProvider blockProvider = new NuclearScienceBlockTagsProvider(generator, event.getExistingFileHelper());
			generator.addProvider(blockProvider);
			generator.addProvider(new NuclearScienceItemTagsProvider(generator, blockProvider, event.getExistingFileHelper()));
			generator.addProvider(new NuclearScienceFluidTagsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new NuclearScienceLootTablesProvider(generator));
			generator.addProvider(new NuclearScienceRecipeProvider(generator));
			generator.addProvider(new RadioactiveItemsProvider(generator));
			generator.addProvider(new NuclearScienceRadioactiveFluidsProvider(generator));
			generator.addProvider(new NuclearScienceRadiationShieldingProvider(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new NuclearScienceBlockStateProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new NuclearScienceItemModelsProvider(generator, event.getExistingFileHelper()));
			generator.addProvider(new NuclearScienceLangKeyProvider(generator, Locale.EN_US));
			generator.addProvider(new NuclearScienceSoundProvider(generator, event.getExistingFileHelper()));
		}
	}

}
