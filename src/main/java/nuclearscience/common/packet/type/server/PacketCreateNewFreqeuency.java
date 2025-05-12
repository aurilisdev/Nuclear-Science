package nuclearscience.common.packet.type.server;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.FrequencyType;
import voltaic.api.codec.StreamCodec;

public class PacketCreateNewFreqeuency {

    public static final StreamCodec<PacketBuffer, PacketCreateNewFreqeuency> CODEC = new StreamCodec<PacketBuffer, PacketCreateNewFreqeuency>() {
        @Override
        public PacketCreateNewFreqeuency decode(PacketBuffer buf) {

            return new PacketCreateNewFreqeuency(StreamCodec.UUID.decode(buf), StreamCodec.STRING.decode(buf), FrequencyType.values()[StreamCodec.INT.decode(buf)]);
        }

        @Override
        public void encode(PacketBuffer buf, PacketCreateNewFreqeuency packet) {

        	StreamCodec.UUID.encode(buf, packet.creator);
            StreamCodec.STRING.encode(buf, packet.name);
            StreamCodec.INT.encode(buf, packet.type.ordinal());

        }

    };

    private final UUID creator;
    private final FrequencyType type;
    private final String name;

    public PacketCreateNewFreqeuency(UUID creator, String name, FrequencyType type) {
        this.creator = creator;
        this.type = type;
        this.name = name;
    }

    public static void handle(PacketCreateNewFreqeuency message, Supplier<Context> context) {
    	Context ctx = context.get();
		ctx.enqueueWork(() -> {

			ServerBarrierMethods.createNewPacket(message.creator, message.type, message.name);

		});
		ctx.setPacketHandled(true);
    }

    public static void encode(PacketCreateNewFreqeuency pkt, PacketBuffer buf) {
		CODEC.encode(buf, pkt);
	}

	public static PacketCreateNewFreqeuency decode(PacketBuffer buf) {
		return CODEC.decode(buf);
	}
}
