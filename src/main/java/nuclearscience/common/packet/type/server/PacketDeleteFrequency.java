package nuclearscience.common.packet.type.server;

import java.util.UUID;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import voltaic.api.codec.StreamCodec;

public class PacketDeleteFrequency {

    public static final StreamCodec<ByteBuf, PacketDeleteFrequency> CODEC = new StreamCodec<>() {
	@Override
	public PacketDeleteFrequency decode(ByteBuf buf) {

	    return new PacketDeleteFrequency(StreamCodec.UUID.decode(buf), TunnelFrequency.STREAM_CODEC.decode(buf));
	}

	@Override
	public void encode(ByteBuf buf, PacketDeleteFrequency packet) {

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

    public static void encode(PacketDeleteFrequency pkt, FriendlyByteBuf buf) {
	CODEC.encode(buf, pkt);
    }

    public static PacketDeleteFrequency decode(FriendlyByteBuf buf) {
	return CODEC.decode(buf);
    }
}
