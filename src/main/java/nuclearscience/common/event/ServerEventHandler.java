package nuclearscience.common.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import nuclearscience.NuclearScience;
import nuclearscience.api.capability.CapabilityAntimatterItem;
import nuclearscience.api.quantumtunnel.CapabilityChannelMap;
import nuclearscience.api.quantumtunnel.CapabilityTunnelMap;
import nuclearscience.common.command.CommandWipeAllFrequencies;
import nuclearscience.common.command.CommandWipePublicFrequencies;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.prefab.utils.NuclearCapabilityUtils;
import nuclearscience.registers.NuclearScienceCapabilities;

@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.FORGE)
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
	
	@SubscribeEvent
	public static void registerLevelCaps(AttachCapabilitiesEvent<Level> event) {
		Level world = event.getObject();
		if(world != null && world.dimension().equals(Level.OVERWORLD) && world.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP) == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
			event.addCapability(NuclearScience.rl("tunnelmap"), new CapabilityTunnelMap());
		}
		if(world != null && world.dimension().equals(Level.OVERWORLD) && world.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP) == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
			event.addCapability(NuclearScience.rl("channelmap"), new CapabilityChannelMap());
		}
	}
	
	@SubscribeEvent
	public static void registerEntityCaps(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		
		if(entity instanceof ItemEntity item && item.getCapability(NuclearScienceCapabilities.CAPABILITY_ANTIMATTERITEM).orElse(NuclearCapabilityUtils.EMPTY_ANTIMATTERITEM) == NuclearCapabilityUtils.EMPTY_ANTIMATTERITEM) {
			event.addCapability(NuclearScience.rl("antimatteritem"), new CapabilityAntimatterItem());
		}
	}

}
