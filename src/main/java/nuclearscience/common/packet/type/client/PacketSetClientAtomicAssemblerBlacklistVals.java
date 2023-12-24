package nuclearscience.common.packet.type.client;

import java.util.HashSet;
import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;

public class PacketSetClientAtomicAssemblerBlacklistVals {

	private final HashSet<Item> items;

	public PacketSetClientAtomicAssemblerBlacklistVals(HashSet<Item> items) {
		this.items = items;
	}

	public static void handle(PacketSetClientAtomicAssemblerBlacklistVals message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {

			message.items.forEach(item -> {
				AtomicAssemblerBlacklistRegister.INSTANCE.setClientValues(message.items);
			});

		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSetClientAtomicAssemblerBlacklistVals pkt, PacketBuffer buf) {
		buf.writeInt(pkt.items.size());
		pkt.items.forEach(item -> {
			buf.writeItem(new ItemStack(item));
		});
	}

	public static PacketSetClientAtomicAssemblerBlacklistVals decode(PacketBuffer buf) {
		int count = buf.readInt();
		HashSet<Item> items = new HashSet<>();
		for (int i = 0; i < count; i++) {
			items.add(buf.readItem().getItem());
		}
		return new PacketSetClientAtomicAssemblerBlacklistVals(items);
	}

}