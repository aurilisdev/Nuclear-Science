package nuclearscience.common.packet;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import nuclearscience.NuclearScience;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerBlacklistVals;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerWhitelistVals;
import nuclearscience.common.packet.type.client.PacketSetClientInterfaces;
import nuclearscience.common.packet.type.client.PacketSetClientTunnelFrequencies;
import nuclearscience.common.packet.type.server.PacketCreateNewFreqeuency;
import nuclearscience.common.packet.type.server.PacketDeleteFrequency;
import nuclearscience.common.packet.type.server.PacketEditFrequency;

@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void registerPackets(final RegisterPayloadHandlersEvent event) {
	final PayloadRegistrar registry = event.registrar(NuclearScience.ID).versioned(PROTOCOL_VERSION).optional();

	// CLIENT
	registry.playToClient(PacketSetClientAtomicAssemblerBlacklistVals.TYPE,
		PacketSetClientAtomicAssemblerBlacklistVals.CODEC, PacketSetClientAtomicAssemblerBlacklistVals::handle);
	registry.playToClient(PacketSetClientTunnelFrequencies.TYPE, PacketSetClientTunnelFrequencies.CODEC,
		PacketSetClientTunnelFrequencies::handle);
	registry.playToClient(PacketSetClientInterfaces.TYPE, PacketSetClientInterfaces.CODEC,
		PacketSetClientInterfaces::handle);
	registry.playToClient(PacketSetClientAtomicAssemblerWhitelistVals.TYPE,
		PacketSetClientAtomicAssemblerWhitelistVals.CODEC, PacketSetClientAtomicAssemblerWhitelistVals::handle);

	// SERVER

	registry.playToServer(PacketCreateNewFreqeuency.TYPE, PacketCreateNewFreqeuency.CODEC,
		PacketCreateNewFreqeuency::handle);
	registry.playToServer(PacketDeleteFrequency.TYPE, PacketDeleteFrequency.CODEC, PacketDeleteFrequency::handle);
	registry.playToServer(PacketEditFrequency.TYPE, PacketEditFrequency.CODEC, PacketEditFrequency::handle);

    }

    public static ResourceLocation id(String name) {
	return NuclearScience.rl(name);
    }

}
