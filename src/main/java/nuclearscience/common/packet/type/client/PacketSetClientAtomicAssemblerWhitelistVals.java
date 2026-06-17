package nuclearscience.common.packet.type.client;

import java.util.HashSet;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;

public class PacketSetClientAtomicAssemblerWhitelistVals {

    private final HashSet<Item> items;

    public PacketSetClientAtomicAssemblerWhitelistVals(HashSet<Item> items) {
	this.items = items;
    }

    public static void handle(PacketSetClientAtomicAssemblerWhitelistVals message, Supplier<Context> context) {
	Context ctx = context.get();
	ctx.enqueueWork(() -> {

	    message.items.forEach(item -> {
		AtomicAssemblerWhitelistRegister.INSTANCE.setClientValues(message.items);
	    });

	});
	ctx.setPacketHandled(true);
    }

    public static void encode(PacketSetClientAtomicAssemblerWhitelistVals pkt, FriendlyByteBuf buf) {
	buf.writeInt(pkt.items.size());
	pkt.items.forEach(item -> {
	    buf.writeItem(new ItemStack(item));
	});
    }

    public static PacketSetClientAtomicAssemblerWhitelistVals decode(FriendlyByteBuf buf) {
	int count = buf.readInt();
	HashSet<Item> items = new HashSet<>();
	for (int i = 0; i < count; i++) {
	    items.add(buf.readItem().getItem());
	}
	return new PacketSetClientAtomicAssemblerWhitelistVals(items);
    }

}
