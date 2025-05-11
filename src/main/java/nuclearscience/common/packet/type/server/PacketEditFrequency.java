package nuclearscience.common.packet.type.server;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import voltaic.api.codec.StreamCodec;

public class PacketEditFrequency {

    public static final StreamCodec<PacketBuffer, PacketEditFrequency> CODEC = new StreamCodec<PacketBuffer, PacketEditFrequency>() {
        @Override
        public PacketEditFrequency decode(PacketBuffer buf) {

            return new PacketEditFrequency(StreamCodec.UUID.decode(buf), TunnelFrequency.STREAM_CODEC.decode(buf));
        }

        @Override
        public void encode(PacketBuffer buf, PacketEditFrequency packet) {

            StreamCodec.UUID.encode(buf, packet.requester);
            TunnelFrequency.STREAM_CODEC.encode(buf, packet.frequency);

        }

    };

    private final UUID requester;
    private final TunnelFrequency frequency;

    public PacketEditFrequency(UUID requester, TunnelFrequency frequency) {
        this.requester = requester;
        this.frequency = frequency;
    }

    public static void handle(PacketEditFrequency message, Supplier<Context> context) {
    	Context ctx = context.get();
		ctx.enqueueWork(() -> {

			ServerBarrierMethods.editFrequency(message.requester, message.frequency);

		});
		ctx.setPacketHandled(true);
    }

    public static void encode(PacketEditFrequency pkt, PacketBuffer buf) {
		CODEC.encode(buf, pkt);
	}

	public static PacketEditFrequency decode(PacketBuffer buf) {
		return CODEC.decode(buf);
	}
}
