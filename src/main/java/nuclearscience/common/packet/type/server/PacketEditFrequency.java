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

public class PacketEditFrequency implements CustomPacketPayload {

    public static final ResourceLocation PACKET_EDITFREQUENCY_PACKETID = NetworkHandler.id("packeteditfrequency");
    public static final Type<PacketEditFrequency> TYPE = new Type<>(PACKET_EDITFREQUENCY_PACKETID);

    public static final StreamCodec<ByteBuf, PacketEditFrequency> CODEC = new StreamCodec<>() {
	@Override
	public PacketEditFrequency decode(ByteBuf buf) {

	    return new PacketEditFrequency(UUIDUtil.STREAM_CODEC.decode(buf), TunnelFrequency.STREAM_CODEC.decode(buf));
	}

	@Override
	public void encode(ByteBuf buf, PacketEditFrequency packet) {

	    UUIDUtil.STREAM_CODEC.encode(buf, packet.requester);
	    TunnelFrequency.STREAM_CODEC.encode(buf, packet.frequency);

	}

    };

    private final UUID requester;
    private final TunnelFrequency frequency;

    public PacketEditFrequency(UUID requester, TunnelFrequency frequency) {
	this.requester = requester;
	this.frequency = frequency;
    }

    public static void handle(PacketEditFrequency message, IPayloadContext context) {
	ServerBarrierMethods.editFrequency(message.requester, message.frequency);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
	return TYPE;
    }
}
