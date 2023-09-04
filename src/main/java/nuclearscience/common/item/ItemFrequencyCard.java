package nuclearscience.common.item;

import java.util.List;
import java.util.function.Supplier;

import electrodynamics.common.item.ItemElectrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ItemFrequencyCard extends ItemElectrodynamics {

	public ItemFrequencyCard(Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(properties.stacksTo(1), creativeTab);
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

		Level level = context.getLevel();

		if (level.isClientSide) {
			return super.onItemUseFirst(stack, context);
		}

		if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof TileTeleporter teleporter) {

			CompoundTag nbt = stack.getOrCreateTag();
			if (nbt.contains(NBTUtils.DIMENSION)) {

				BlockPos pos = readBlockPos(stack);
				ResourceKey<Level> world = readDimension(stack);

				teleporter.destination.set(pos);
				teleporter.dimension = world;

				MutableComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world) : Component.literal(world.location().getPath());

				context.getPlayer().sendSystemMessage(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(" " + pos.toShortString())));

			} else {
				writeBlockPos(stack, teleporter.getBlockPos());
				writeDimension(stack, teleporter.getLevel().dimension());
			}

		}

		return super.onItemUseFirst(stack, context);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (stack.hasTag()) {
			BlockPos pos = readBlockPos(stack);
			ResourceKey<Level> world = readDimension(stack);

			MutableComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world) : Component.literal(world.location().getPath());

			tooltip.add(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(" " + pos.toShortString())));
		} else {
			tooltip.add(NuclearTextUtils.tooltip("frequencycard.notag"));
		}
	}

	public static void writeBlockPos(ItemStack item, BlockPos pos) {
		item.getOrCreateTag().put(NBTUtils.LOCATION, NbtUtils.writeBlockPos(pos));
	}

	public static BlockPos readBlockPos(ItemStack item) {
		return NbtUtils.readBlockPos(item.getOrCreateTag().getCompound(NBTUtils.LOCATION));
	}

	public static void writeDimension(ItemStack stack, ResourceKey<Level> dim) {
		stack.getOrCreateTag().put(NBTUtils.DIMENSION, NBTUtils.writeDimensionToTag(dim));
	}

	public static ResourceKey<Level> readDimension(ItemStack stack) {
		return NBTUtils.readDimensionFromTag(stack.getOrCreateTag().getCompound(NBTUtils.DIMENSION));
	}

}
