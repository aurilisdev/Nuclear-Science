package nuclearscience.registers;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import nuclearscience.NuclearScience;
import nuclearscience.api.capability.ICapabilityAntimatterItem;
import nuclearscience.api.quantumtunnel.ICapabilityChannelMap;
import nuclearscience.api.quantumtunnel.ICapabilityTunnelMap;

@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
public class NuclearScienceCapabilities {

	public static final Capability<ICapabilityTunnelMap> CAPABILITY_TUNNELMAP = CapabilityManager.get(new CapabilityToken<>() {
	});
	
	public static final Capability<ICapabilityChannelMap> CAPABILITY_CHANNELMAP = CapabilityManager.get(new CapabilityToken<>() {
	});
	
	public static final Capability<ICapabilityAntimatterItem> CAPABILITY_ANTIMATTERITEM = CapabilityManager.get(new CapabilityToken<>() {
	});
	
	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(ICapabilityTunnelMap.class);
		event.register(ICapabilityChannelMap.class);
		event.register(ICapabilityAntimatterItem.class);
	}

}
