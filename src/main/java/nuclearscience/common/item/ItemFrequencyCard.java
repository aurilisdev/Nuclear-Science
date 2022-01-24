package nuclearscience.common.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.common.tile.TileTeleporter;

public class ItemFrequencyCard extends Item {

	public ItemFrequencyCard(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
		BlockEntity ent = context.getLevel().getBlockEntity(context.getClickedPos());
		if (ent instanceof TileTeleporter tel && !ent.getLevel().isClientSide) {
			CompoundTag nbt = stack.getOrCreateTag();
			if (nbt.contains("world")) {
				tel.xCoord = nbt.getInt("xCoord");
				tel.yCoord = nbt.getInt("yCoord");
				tel.zCoord = nbt.getInt("zCoord");
				tel.world = nbt.getString("world");
				context.getPlayer().sendMessage(new TranslatableComponent("tooltip.frequencycard.linked", tel.world + ", " + tel.xCoord + ", " + tel.yCoord + ", " + tel.zCoord), UUID.randomUUID());
			} else {
				nbt.putInt("xCoord", ent.getBlockPos().getX());
				nbt.putInt("yCoord", ent.getBlockPos().getY());
				nbt.putInt("zCoord", ent.getBlockPos().getZ());
				nbt.putString("world", ent.getLevel().dimension().location().getPath());
			}
		}
		return super.onItemUseFirst(stack, context);
	}

	public static ServerLevel getFromNBT(ServerLevel base, String str) {
		for (ServerLevel world : base.getLevel().getServer().getAllLevels()) {
			if (world.dimension().location().getPath().equalsIgnoreCase(str)) {
				return world;
			}
		}
		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (stack.hasTag()) {
			CompoundTag nbt = stack.getTag();
			int x = nbt.getInt("xCoord");
			int y = nbt.getInt("yCoord");
			int z = nbt.getInt("zCoord");
			String world = nbt.getString("world");
			tooltip.add(new TranslatableComponent("tooltip.frequencycard.linked", world + ", " + x + ", " + y + ", " + z));
		} else {
			tooltip.add(new TranslatableComponent("tooltip.frequencycard.notag"));
		}
	}
}
