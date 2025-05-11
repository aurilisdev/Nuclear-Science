package nuclearscience.common.packet;

import java.util.Optional;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import nuclearscience.NuclearScience;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerBlacklistVals;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerWhitelistVals;
import nuclearscience.common.packet.type.client.PacketSetClientInterfaces;
import nuclearscience.common.packet.type.client.PacketSetClientTunnelFrequencies;
import nuclearscience.common.packet.type.server.PacketCreateNewFreqeuency;
import nuclearscience.common.packet.type.server.PacketDeleteFrequency;
import nuclearscience.common.packet.type.server.PacketEditFrequency;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	private static int disc = 0;
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(NuclearScience.ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static void init() {
		
		// CLIENT
		
		CHANNEL.registerMessage(disc++, PacketSetClientInterfaces.class, PacketSetClientInterfaces::encode, PacketSetClientInterfaces::decode, PacketSetClientInterfaces::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientTunnelFrequencies.class, PacketSetClientTunnelFrequencies::encode, PacketSetClientTunnelFrequencies::decode, PacketSetClientTunnelFrequencies::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientAtomicAssemblerBlacklistVals.class, PacketSetClientAtomicAssemblerBlacklistVals::encode, PacketSetClientAtomicAssemblerBlacklistVals::decode, PacketSetClientAtomicAssemblerBlacklistVals::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientAtomicAssemblerWhitelistVals.class, PacketSetClientAtomicAssemblerWhitelistVals::encode, PacketSetClientAtomicAssemblerWhitelistVals::decode, PacketSetClientAtomicAssemblerWhitelistVals::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		
		// SERVER
		
		CHANNEL.registerMessage(disc++, PacketCreateNewFreqeuency.class, PacketCreateNewFreqeuency::encode, PacketCreateNewFreqeuency::decode, PacketCreateNewFreqeuency::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketDeleteFrequency.class, PacketDeleteFrequency::encode, PacketDeleteFrequency::decode, PacketDeleteFrequency::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		CHANNEL.registerMessage(disc++, PacketEditFrequency.class, PacketEditFrequency::encode, PacketEditFrequency::decode, PacketEditFrequency::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		
	}
}
