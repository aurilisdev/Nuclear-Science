package nuclearscience.common.packet;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class PacketSetQuantumCapacitorData {

    private final double outputJoules;
    private final int frequency;
    private final BlockPos pos;

    public PacketSetQuantumCapacitorData(BlockPos pos, double outputJoules, int frequency) {
	this.pos = pos;
	this.outputJoules = outputJoules;
	this.frequency = frequency;
    }

    public static void handle(PacketSetQuantumCapacitorData message, Supplier<Context> context) {
	Context ctx = context.get();
	ctx.enqueueWork(() -> {
	    ServerLevel world = context.get().getSender().getLevel();
	    if (world != null) {
		TileQuantumCapacitor tile = (TileQuantumCapacitor) world.getBlockEntity(message.pos);
		if (tile != null) {
		    tile.outputJoules = message.outputJoules;
		    tile.frequency = message.frequency;
		}
	    }
	});
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketSetQuantumCapacitorData pkt, FriendlyByteBuf buf) {
	buf.writeBlockPos(pkt.pos);
	buf.writeDouble(pkt.outputJoules);
	buf.writeInt(pkt.frequency);
    }

    public static PacketSetQuantumCapacitorData decode(FriendlyByteBuf buf) {
	return new PacketSetQuantumCapacitorData(buf.readBlockPos(), buf.readDouble(), buf.readInt());
    }
}