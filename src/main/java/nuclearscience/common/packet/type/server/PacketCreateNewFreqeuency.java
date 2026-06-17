package nuclearscience.common.packet.type.server;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nuclearscience.api.quantumtunnel.FrequencyType;
import nuclearscience.common.packet.NetworkHandler;

public class PacketCreateNewFreqeuency implements CustomPacketPayload {

    public static final ResourceLocation PACKET_CREATENEWFREQUENCY_PACKETID = NetworkHandler
	    .id("packetcreatenewfrequency");
    public static final Type<PacketCreateNewFreqeuency> TYPE = new Type<>(PACKET_CREATENEWFREQUENCY_PACKETID);

    public static final StreamCodec<ByteBuf, PacketCreateNewFreqeuency> CODEC = new StreamCodec<>() {
	@Override
	public PacketCreateNewFreqeuency decode(ByteBuf buf) {

	    return new PacketCreateNewFreqeuency(UUIDUtil.STREAM_CODEC.decode(buf),
		    ByteBufCodecs.STRING_UTF8.decode(buf), FrequencyType.values()[ByteBufCodecs.INT.decode(buf)]);
	}

	@Override
	public void encode(ByteBuf buf, PacketCreateNewFreqeuency packet) {

	    UUIDUtil.STREAM_CODEC.encode(buf, packet.creator);
	    ByteBufCodecs.STRING_UTF8.encode(buf, packet.name);
	    ByteBufCodecs.INT.encode(buf, packet.type.ordinal());

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

    public static void handle(PacketCreateNewFreqeuency message, IPayloadContext context) {
	ServerBarrierMethods.createNewPacket(message.creator, message.type, message.name);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
	return TYPE;
    }
}
