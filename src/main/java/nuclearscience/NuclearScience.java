package nuclearscience;

import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import nuclearscience.api.radiation.EffectRadiation;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
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
		NuclearScienceRecipeInit.RECIPE_TYPES.register(bus);
		NuclearScienceRecipeInit.RECIPE_SERIALIZER.register(bus);
		RadiationRegister.init();
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		ClientRegister.setup();
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {
		NetworkHandler.init();
		NuclearScienceTags.init();
		RadioactiveItemLoader.INSTANCE = new RadioactiveItemLoader().subscribeAsSyncable(NetworkHandler.CHANNEL);
	}

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
		
	}

	@SubscribeEvent
	public static void registerEffects(RegisterEvent event) {
		event.<MobEffect>register(ForgeRegistries.Keys.MOB_EFFECTS, helper -> helper.register("radiation", EffectRadiation.INSTANCE));
	}
}
