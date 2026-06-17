package nuclearscience.common.packet.type.server;

import java.util.UUID;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import voltaic.api.codec.StreamCodec;

public class PacketEditFrequency {

    public static final StreamCodec<ByteBuf, PacketEditFrequency> CODEC = new StreamCodec<>() {
	@Override
	public PacketEditFrequency decode(ByteBuf buf) {

	    return new PacketEditFrequency(StreamCodec.UUID.decode(buf), TunnelFrequency.STREAM_CODEC.decode(buf));
	}

	@Override
	public void encode(ByteBuf buf, PacketEditFrequency packet) {

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

    public static void encode(PacketEditFrequency pkt, FriendlyByteBuf buf) {
	CODEC.encode(buf, pkt);
    }

    public static PacketEditFrequency decode(FriendlyByteBuf buf) {
	return CODEC.decode(buf);
    }
}
