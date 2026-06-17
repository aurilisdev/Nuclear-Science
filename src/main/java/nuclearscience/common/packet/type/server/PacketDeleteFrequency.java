package nuclearscience.common.packet.type.server;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.packet.NetworkHandler;

public class PacketDeleteFrequency implements CustomPacketPayload {

    public static final ResourceLocation PACKET_DELETEFREQUENCY_PACKETID = NetworkHandler.id("packetdeletefrequency");
    public static final Type<PacketDeleteFrequency> TYPE = new Type<>(PACKET_DELETEFREQUENCY_PACKETID);

    public static final StreamCodec<ByteBuf, PacketDeleteFrequency> CODEC = new StreamCodec<>() {
	@Override
	public PacketDeleteFrequency decode(ByteBuf buf) {

	    return new PacketDeleteFrequency(UUIDUtil.STREAM_CODEC.decode(buf),
		    TunnelFrequency.STREAM_CODEC.decode(buf));
	}

	@Override
	public void encode(ByteBuf buf, PacketDeleteFrequency packet) {

	    UUIDUtil.STREAM_CODEC.encode(buf, packet.requester);
	    TunnelFrequency.STREAM_CODEC.encode(buf, packet.frequency);

	}

    };

    private final UUID requester;
    private final TunnelFrequency frequency;

    public PacketDeleteFrequency(UUID requester, TunnelFrequency frequency) {
	this.requester = requester;
	this.frequency = frequency;
    }

    public static void handle(PacketDeleteFrequency message, IPayloadContext context) {
	ServerBarrierMethods.deleteFrequency(message.requester, message.frequency);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
	return TYPE;
    }
}
