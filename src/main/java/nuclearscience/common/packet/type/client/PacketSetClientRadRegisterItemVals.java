package nuclearscience.common.packet.type.client;

import java.util.HashMap;
import java.util.function.Supplier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;

public class PacketSetClientRadRegisterItemVals {
	
	private final HashMap<Item, Double> items;

	public PacketSetClientRadRegisterItemVals(HashMap<Item, Double> items) {
		this.items = items;
	}

	public static void handle(PacketSetClientRadRegisterItemVals message, Supplier<Context> context) {
		Context ctx = context.get();
		ctx.enqueueWork(() -> {
			
			message.items.forEach((item, value) -> {
				RadiationRegister.register(item, new FieldRadioactiveObject(value));
			});
			
		});
		ctx.setPacketHandled(true);
	}

	public static void encode(PacketSetClientRadRegisterItemVals pkt, PacketBuffer buf) {
		buf.writeInt(pkt.items.size());
		pkt.items.forEach((item, value) -> {
			buf.writeItem(new ItemStack(item));
			buf.writeDouble(value);
		});
	}

	public static PacketSetClientRadRegisterItemVals decode(PacketBuffer buf) {
		int count = buf.readInt();
		HashMap<Item, Double> items = new HashMap<>();
		for (int i = 0; i < count; i++) {
			items.put(buf.readItem().getItem(), buf.readDouble());
		}
		return new PacketSetClientRadRegisterItemVals(items);
	}

}