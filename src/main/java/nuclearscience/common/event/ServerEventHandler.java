package nuclearscience.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import nuclearscience.NuclearScience;
import nuclearscience.common.command.CommandWipeAllFrequencies;
import nuclearscience.common.command.CommandWipePublicFrequencies;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;

@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEventHandler {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(AtomicAssemblerBlacklistRegister.INSTANCE);
		event.addListener(AtomicAssemblerWhitelistRegister.INSTANCE);
	}

	@SubscribeEvent
	public static void serverStartedHandler(ServerStartedEvent event) {
		AtomicAssemblerBlacklistRegister.INSTANCE.generateTagValues();
		AtomicAssemblerWhitelistRegister.INSTANCE.generateTagValues();
	}

	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		CommandWipeAllFrequencies.register(event.getDispatcher());
		CommandWipePublicFrequencies.register(event.getDispatcher());
	}


}
