package nuclearscience;

import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.minecraft.potion.Effect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nuclearscience.api.radiation.EffectRadiation;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.block.voxelshapes.NuclearScienceVoxelShapeRegistry;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemLoader;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.UnifiedNuclearScienceRegister;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class NuclearScience {

	public NuclearScience() {
		ConfigurationHandler.registerConfig(Constants.class);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		UnifiedNuclearScienceRegister.register(bus);
		NuclearScienceRecipeInit.RECIPE_SERIALIZER.register(bus);
		RadiationRegister.init();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegister.setup();
		});
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		NetworkHandler.init();
		NuclearScienceTags.init();
		RadioactiveItemLoader.INSTANCE = new RadioactiveItemLoader().subscribeAsSyncable(NetworkHandler.CHANNEL);
		AtomicAssemblerBlacklistRegister.INSTANCE = new AtomicAssemblerBlacklistRegister().subscribeAsSyncable(NetworkHandler.CHANNEL);
		NuclearScienceVoxelShapeRegistry.init();
	}

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
		
	}

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<Effect> event) {
		event.getRegistry().registerAll(EffectRadiation.INSTANCE);
	}
}
