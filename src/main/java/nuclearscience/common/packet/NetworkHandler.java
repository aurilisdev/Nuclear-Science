package nuclearscience.common.packet;

import java.util.Optional;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import nuclearscience.References;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int disc = 0;
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(References.ID, "main"), () -> PROTOCOL_VERSION,
	    PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
	CHANNEL.registerMessage(disc++, PacketSetQuantumCapacitorData.class, PacketSetQuantumCapacitorData::encode,
		PacketSetQuantumCapacitorData::decode, PacketSetQuantumCapacitorData::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
