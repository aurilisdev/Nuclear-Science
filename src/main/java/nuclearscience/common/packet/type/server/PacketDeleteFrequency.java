package nuclearscience.common.packet.type.server;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import voltaic.api.codec.StreamCodec;

public class PacketDeleteFrequency {

    public static final StreamCodec<PacketBuffer, PacketDeleteFrequency> CODEC = new StreamCodec<PacketBuffer, PacketDeleteFrequency>() {
        @Override
        public PacketDeleteFrequency decode(PacketBuffer buf) {

            return new PacketDeleteFrequency(StreamCodec.UUID.decode(buf), TunnelFrequency.STREAM_CODEC.decode(buf));
        }

        @Override
        public void encode(PacketBuffer buf, PacketDeleteFrequency packet) {

            StreamCodec.UUID.encode(buf, packet.requester);
            TunnelFrequency.STREAM_CODEC.encode(buf, packet.frequency);

        }

    };

    private final UUID requester;
    private final TunnelFrequency frequency;

    public PacketDeleteFrequency(UUID requester, TunnelFrequency frequency) {
        this.requester = requester;
        this.frequency = frequency;
    }

    public static void handle(PacketDeleteFrequency message, Supplier<Context> context) {
    	Context ctx = context.get();
		ctx.enqueueWork(() -> {

			ServerBarrierMethods.deleteFrequency(message.requester, message.frequency);

		});
		ctx.setPacketHandled(true);
    }

    public static void encode(PacketDeleteFrequency pkt, PacketBuffer buf) {
		CODEC.encode(buf, pkt);
	}

	public static PacketDeleteFrequency decode(PacketBuffer buf) {
		return CODEC.decode(buf);
	}
}
