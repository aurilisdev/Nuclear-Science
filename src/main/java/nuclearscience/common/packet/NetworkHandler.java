package nuclearscience.common.packet;

import java.util.Optional;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import nuclearscience.References;
import nuclearscience.common.packet.type.client.PacketSetClientAtomicAssemblerBlacklistVals;
import nuclearscience.common.packet.type.client.PacketSetClientRadRegisterItemVals;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	private static int disc = 0;
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(References.ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static void init() {
		CHANNEL.registerMessage(disc++, PacketSetClientAtomicAssemblerBlacklistVals.class, PacketSetClientAtomicAssemblerBlacklistVals::encode, PacketSetClientAtomicAssemblerBlacklistVals::decode, PacketSetClientAtomicAssemblerBlacklistVals::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		CHANNEL.registerMessage(disc++, PacketSetClientRadRegisterItemVals.class, PacketSetClientRadRegisterItemVals::encode, PacketSetClientRadRegisterItemVals::decode, PacketSetClientRadRegisterItemVals::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}
}