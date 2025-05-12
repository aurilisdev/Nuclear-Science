package nuclearscience.common.packet.type.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import nuclearscience.api.network.reactorlogistics.Interface;
import voltaic.api.codec.StreamCodec;

public class PacketSetClientInterfaces {

    public static final StreamCodec<PacketBuffer, PacketSetClientInterfaces> CODEC = new StreamCodec<PacketBuffer, PacketSetClientInterfaces>() {
        @Override
        public PacketSetClientInterfaces decode(PacketBuffer buf) {
            List<Interface> interfaces = new ArrayList<>();

            int size = buf.readInt();
            for (int i = 0; i < size; i++) {

                interfaces.add(Interface.STREAM_CODEC.decode(buf));

            }


            return new PacketSetClientInterfaces(StreamCodec.BLOCK_POS.decode(buf), interfaces);
        }

        @Override
        public void encode(PacketBuffer buf, PacketSetClientInterfaces packet) {
            buf.writeInt(packet.interfaces.size());
            for (Interface inter : packet.interfaces) {
                Interface.STREAM_CODEC.encode(buf, inter);
            }
            StreamCodec.BLOCK_POS.encode(buf, packet.pos);
        }
    };

    private final BlockPos pos;
    private final List<Interface> interfaces;

    public PacketSetClientInterfaces(BlockPos pos, List<Interface> interfaces) {
        this.pos = pos;
        this.interfaces = interfaces;
    }

    public static void handle(PacketSetClientInterfaces message, Supplier<Context> context) {
    	Context ctx = context.get();
		ctx.enqueueWork(() -> {

			ClientBarrierMethods.handleSetClientInterfaces(message.pos, message.interfaces);

		});
		ctx.setPacketHandled(true);
        
    }

    public static void encode(PacketSetClientInterfaces pkt, PacketBuffer buf) {
		CODEC.encode(buf, pkt);
	}

	public static PacketSetClientInterfaces decode(PacketBuffer buf) {
		return CODEC.decode(buf);
	}
}
