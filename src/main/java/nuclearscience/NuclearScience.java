package nuclearscience;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nuclearscience.client.ClientRegister;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD)
public class NuclearScience {

	public NuclearScience() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		DeferredRegisters.BLOCKS.register(bus);
		DeferredRegisters.ITEMS.register(bus);
		DeferredRegisters.TILES.register(bus);
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		ClientRegister.setup();
	}

	@SubscribeEvent
	public static void onLoadEvent(FMLLoadCompleteEvent event) {
	}
}
