package nuclearscience.common.packet.type.server;

import java.util.UUID;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.FrequencyType;
import voltaic.api.codec.StreamCodec;

public class PacketCreateNewFreqeuency {

    public static final StreamCodec<ByteBuf, PacketCreateNewFreqeuency> CODEC = new StreamCodec<>() {
	@Override
	public PacketCreateNewFreqeuency decode(ByteBuf buf) {

	    return new PacketCreateNewFreqeuency(StreamCodec.UUID.decode(buf), StreamCodec.STRING.decode(buf),
		    FrequencyType.values()[StreamCodec.INT.decode(buf)]);
	}

	@Override
	public void encode(ByteBuf buf, PacketCreateNewFreqeuency packet) {

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

    public static void encode(PacketCreateNewFreqeuency pkt, FriendlyByteBuf buf) {
	CODEC.encode(buf, pkt);
    }

    public static PacketCreateNewFreqeuency decode(FriendlyByteBuf buf) {
	return CODEC.decode(buf);
    }
}
