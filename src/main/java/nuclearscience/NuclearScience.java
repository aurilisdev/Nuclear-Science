package nuclearscience;

import electrodynamics.api.configuration.ConfigurationHandler;
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
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nuclearscience.api.radiation.EffectRadiation;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.client.ClientRegister;
import nuclearscience.common.settings.Constants;

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
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
	ClientRegister.setup();
    }

    @SubscribeEvent
    public static void onLoadEvent(FMLLoadCompleteEvent event) {
	RadiationRegister.register(DeferredRegisters.ITEM_URANIUM235.get(), new FieldRadioactiveObject(1000));
	RadiationRegister.register(DeferredRegisters.ITEM_URANIUM238.get(), new FieldRadioactiveObject(500));
	RadiationRegister.register(DeferredRegisters.ITEM_YELLOWCAKE.get(), new FieldRadioactiveObject(300));
	RadiationRegister.register(DeferredRegisters.ITEM_FUELHEUO2.get(), new FieldRadioactiveObject(3000));
	RadiationRegister.register(DeferredRegisters.ITEM_FUELLEUO2.get(), new FieldRadioactiveObject(2000));
    }

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event) {
	event.getRegistry().registerAll(EffectRadiation.INSTANCE);
    }
}
