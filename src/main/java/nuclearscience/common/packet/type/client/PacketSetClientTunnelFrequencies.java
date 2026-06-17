package nuclearscience.common.packet.type.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.common.packet.NetworkHandler;

public class PacketSetClientTunnelFrequencies implements CustomPacketPayload {

    public static final ResourceLocation PACKET_SETCLIENTRADIOACTIVEGASES_PACKETID = NetworkHandler
	    .id("packetsetclienttunnelfrequencies");
    public static final Type<PacketSetClientTunnelFrequencies> TYPE = new Type<>(
	    PACKET_SETCLIENTRADIOACTIVEGASES_PACKETID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetClientTunnelFrequencies> CODEC = new StreamCodec<>() {
	@Override
	public PacketSetClientTunnelFrequencies decode(RegistryFriendlyByteBuf buf) {
	    HashMap<UUID, HashSet<TunnelFrequency>> data = new HashMap<>();

	    int size = buf.readInt();
	    for (int i = 0; i < size; i++) {

		UUID id = UUIDUtil.STREAM_CODEC.decode(buf);
		HashSet<TunnelFrequency> set = new HashSet<>();

		int setSize = buf.readInt();

		for (int j = 0; j < setSize; j++) {
		    set.add(TunnelFrequency.STREAM_CODEC.decode(buf));
		}

		data.put(id, set);
	    }

	    return new PacketSetClientTunnelFrequencies(data, TunnelFrequencyBuffer.STREAM_CODEC.decode(buf),
		    BlockPos.STREAM_CODEC.decode(buf));
	}

	@Override
	public void encode(RegistryFriendlyByteBuf buf, PacketSetClientTunnelFrequencies packet) {
	    buf.writeInt(packet.frequencies.size());
	    for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : packet.frequencies.entrySet()) {
		UUIDUtil.STREAM_CODEC.encode(buf, entry.getKey());
		buf.writeInt(entry.getValue().size());
		for (TunnelFrequency freq : entry.getValue()) {
		    TunnelFrequency.STREAM_CODEC.encode(buf, freq);
		}
	    }
	    TunnelFrequencyBuffer.STREAM_CODEC.encode(buf, packet.currBuffer);
	    BlockPos.STREAM_CODEC.encode(buf, packet.tilePos);
	}
    };

    private final HashMap<UUID, HashSet<TunnelFrequency>> frequencies;
    private final TunnelFrequencyBuffer currBuffer;
    private final BlockPos tilePos;

    public PacketSetClientTunnelFrequencies(HashMap<UUID, HashSet<TunnelFrequency>> frequencies,
	    TunnelFrequencyBuffer currBuffer, BlockPos tilePos) {
	this.frequencies = frequencies;
	this.currBuffer = currBuffer;
	this.tilePos = tilePos;
    }

    public static void handle(PacketSetClientTunnelFrequencies message, IPayloadContext context) {
	ClientBarrierMethods.handleSetClientTunnelFrequencies(message.frequencies, message.currBuffer, message.tilePos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
	return TYPE;
    }
}
