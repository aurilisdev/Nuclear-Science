package nuclearscience;

import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.minecraft.world.effect.MobEffect;
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
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tags.NuclearScienceTags;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class NuclearScience {

	public NuclearScience() {
		ConfigurationHandler.registerConfig(Constants.class);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		DeferredRegisters.BLOCKS.register(bus);
		DeferredRegisters.ITEMS.register(bus);
		DeferredRegisters.TILES.register(bus);
		DeferredRegisters.CONTAINERS.register(bus);
		DeferredRegisters.FLUIDS.register(bus);
		DeferredRegisters.ENTITIES.register(bus);
		NuclearScienceRecipeInit.RECIPE_SERIALIZER.register(bus);
		SoundRegister.SOUNDS.register(bus);
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
	}

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
		RadiationRegister.register(DeferredRegisters.ITEM_URANIUM235.get(), new FieldRadioactiveObject(1000));
		RadiationRegister.register(DeferredRegisters.ITEM_URANIUM238.get(), new FieldRadioactiveObject(500));
		RadiationRegister.register(DeferredRegisters.ITEM_YELLOWCAKE.get(), new FieldRadioactiveObject(300));
		RadiationRegister.register(DeferredRegisters.ITEM_FUELHEUO2.get(), new FieldRadioactiveObject(3000));
		RadiationRegister.register(DeferredRegisters.ITEM_FUELLEUO2.get(), new FieldRadioactiveObject(2000));
		RadiationRegister.register(DeferredRegisters.ITEM_FUELSPENT.get(), new FieldRadioactiveObject(3500));
		RadiationRegister.register(DeferredRegisters.ITEM_FUELPLUTONIUM.get(), new FieldRadioactiveObject(2500));
		RadiationRegister.register(DeferredRegisters.ITEM_FISSILEDUST.get(), new FieldRadioactiveObject(2000));
		RadiationRegister.register(DeferredRegisters.ITEM_PLUTONIUMOXIDE.get(), new FieldRadioactiveObject(4000));
		RadiationRegister.register(DeferredRegisters.ITEM_PLUTONIUM239.get(), new FieldRadioactiveObject(4500));
		RadiationRegister.register(DeferredRegisters.ITEM_THORIANITEDUST.get(), new FieldRadioactiveObject(2000));
		RadiationRegister.register(DeferredRegisters.ITEM_POLONIUM210.get(), new FieldRadioactiveObject(2500));
		RadiationRegister.register(DeferredRegisters.ITEM_POLONIUM210_CHUNK.get(), new FieldRadioactiveObject(1500));
	}

	@SubscribeEvent
	public static void registerEffects(RegistryEvent.Register<MobEffect> event) {
		event.getRegistry().registerAll(EffectRadiation.INSTANCE);
	}
}
