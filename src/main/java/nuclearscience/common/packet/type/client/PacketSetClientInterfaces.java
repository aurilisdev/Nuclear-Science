package nuclearscience.common.packet.type.client;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nuclearscience.api.network.reactorlogistics.Interface;
import nuclearscience.common.packet.NetworkHandler;

public class PacketSetClientInterfaces implements CustomPacketPayload {

    public static final ResourceLocation PACKET_SETCLIENTINTERFACES_PACKETID = NetworkHandler
	    .id("packetsetclientinterfaces");
    public static final Type<PacketSetClientInterfaces> TYPE = new Type<>(PACKET_SETCLIENTINTERFACES_PACKETID);

    public static final StreamCodec<ByteBuf, PacketSetClientInterfaces> CODEC = new StreamCodec<>() {
	@Override
	public PacketSetClientInterfaces decode(ByteBuf buf) {
	    List<Interface> interfaces = new ArrayList<>();

	    int size = buf.readInt();
	    for (int i = 0; i < size; i++) {

		interfaces.add(Interface.STREAM_CODEC.decode(buf));

	    }

	    return new PacketSetClientInterfaces(BlockPos.STREAM_CODEC.decode(buf), interfaces);
	}

	@Override
	public void encode(ByteBuf buf, PacketSetClientInterfaces packet) {
	    buf.writeInt(packet.interfaces.size());
	    for (Interface inter : packet.interfaces) {
		Interface.STREAM_CODEC.encode(buf, inter);
	    }
	    BlockPos.STREAM_CODEC.encode(buf, packet.pos);
	}
    };

    private final BlockPos pos;
    private final List<Interface> interfaces;

    public PacketSetClientInterfaces(BlockPos pos, List<Interface> interfaces) {
	this.pos = pos;
	this.interfaces = interfaces;
    }

    public static void handle(PacketSetClientInterfaces message, IPayloadContext context) {
	ClientBarrierMethods.handleSetClientInterfaces(message.pos, message.interfaces);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
	return TYPE;
    }
}
