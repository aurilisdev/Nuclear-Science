package nuclearscience.common.packet.type.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import voltaic.api.codec.StreamCodec;

public class PacketSetClientTunnelFrequencies {

    public static final StreamCodec<FriendlyByteBuf, PacketSetClientTunnelFrequencies> CODEC = new StreamCodec<>() {
        @Override
        public PacketSetClientTunnelFrequencies decode(FriendlyByteBuf buf) {
            HashMap<UUID, HashSet<TunnelFrequency>> data = new HashMap<>();

            int size = buf.readInt();
            for (int i = 0; i < size; i++) {

                UUID id = StreamCodec.UUID.decode(buf);
                HashSet<TunnelFrequency> set = new HashSet<>();

                int setSize = buf.readInt();

                for (int j = 0; j < setSize; j++) {
                    set.add(TunnelFrequency.STREAM_CODEC.decode(buf));
                }

                data.put(id, set);
            }


            return new PacketSetClientTunnelFrequencies(data, TunnelFrequencyBuffer.STREAM_CODEC.decode(buf), StreamCodec.BLOCK_POS.decode(buf));
        }

        @Override
        public void encode(FriendlyByteBuf buf, PacketSetClientTunnelFrequencies packet) {
            buf.writeInt(packet.frequencies.size());
            for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : packet.frequencies.entrySet()) {
            	StreamCodec.UUID.encode(buf, entry.getKey());
                buf.writeInt(entry.getValue().size());
                for (TunnelFrequency freq : entry.getValue()) {
                    TunnelFrequency.STREAM_CODEC.encode(buf, freq);
                }
            }
            TunnelFrequencyBuffer.STREAM_CODEC.encode(buf, packet.currBuffer);
            StreamCodec.BLOCK_POS.encode(buf, packet.tilePos);
        }
    };

    private final HashMap<UUID, HashSet<TunnelFrequency>> frequencies;
    private final TunnelFrequencyBuffer currBuffer;
    private final BlockPos tilePos;

    public PacketSetClientTunnelFrequencies(HashMap<UUID, HashSet<TunnelFrequency>> frequencies, TunnelFrequencyBuffer currBuffer, BlockPos tilePos) {
        this.frequencies = frequencies;
        this.currBuffer = currBuffer;
        this.tilePos = tilePos;
    }

    public static void handle(PacketSetClientTunnelFrequencies message, Supplier<Context> context) {
    	Context ctx = context.get();
		ctx.enqueueWork(() -> {

			ClientBarrierMethods.handleSetClientTunnelFrequencies(message.frequencies, message.currBuffer, message.tilePos);

		});
		ctx.setPacketHandled(true);
        
    }

    public static void encode(PacketSetClientTunnelFrequencies pkt, FriendlyByteBuf buf) {
		CODEC.encode(buf, pkt);
	}

	public static PacketSetClientTunnelFrequencies decode(FriendlyByteBuf buf) {
		return CODEC.decode(buf);
	}
}
