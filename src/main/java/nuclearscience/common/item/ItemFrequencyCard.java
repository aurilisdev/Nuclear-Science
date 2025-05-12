package nuclearscience.common.item;

import java.util.List;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.common.item.ItemVoltaic;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.NBTUtils;

public class ItemFrequencyCard extends ItemVoltaic {

	public ItemFrequencyCard(Properties properties, Supplier<ItemGroup> creativeTab) {
		super(properties.stacksTo(1), creativeTab);
	}

	@Override
	public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {

		World level = context.getLevel();

		if (level.isClientSide) {
			return super.onItemUseFirst(stack, context);
		}

		TileEntity blockentity = context.getLevel().getBlockEntity(context.getClickedPos()); 
		
		if (blockentity instanceof TileTeleporter) {
			TileTeleporter teleporter = (TileTeleporter) blockentity;

			CompoundNBT nbt = stack.getOrCreateTag();
			if (nbt.contains(NBTUtils.DIMENSION)) {

				BlockPos pos = readBlockPos(stack);
				RegistryKey<World> world = readDimension(stack);

				teleporter.destination.setValue(pos);
				teleporter.dimension.setValue(world);

				IFormattableTextComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world) : new StringTextComponent(world.location().getPath());

				context.getPlayer().sendMessage(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(" " + pos.toShortString())), Util.NIL_UUID);

			} else {
				writeBlockPos(stack, teleporter.getBlockPos());
				writeDimension(stack, teleporter.getLevel().dimension());
			}

		}

		return super.onItemUseFirst(stack, context);
	}

	@Override
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		if (stack.hasTag()) {
			BlockPos pos = readBlockPos(stack);
			RegistryKey<World> world = readDimension(stack);

			IFormattableTextComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world) : new StringTextComponent(world.location().getPath());

			tooltip.add(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(" " + pos.toShortString())));
		} else {
			tooltip.add(NuclearTextUtils.tooltip("frequencycard.notag"));
		}
	}

	public static void writeBlockPos(ItemStack item, BlockPos pos) {
		BlockPos.CODEC.encodeStart(NBTDynamicOps.INSTANCE, pos).result().ifPresent(tag -> item.getOrCreateTag().put(NBTUtils.LOCATION, tag));
	}

	public static BlockPos readBlockPos(ItemStack item) {
		return BlockPos.CODEC.decode(NBTDynamicOps.INSTANCE, item.getOrCreateTag().get(NBTUtils.LOCATION)).result().orElse(Pair.of(BlockEntityUtils.OUT_OF_REACH, new CompoundNBT())).getFirst();
	}

	public static void writeDimension(ItemStack stack, RegistryKey<World> dim) {
		stack.getOrCreateTag().put(NBTUtils.DIMENSION, NBTUtils.writeDimensionToTag(dim));
	}

	public static RegistryKey<World> readDimension(ItemStack stack) {
		return NBTUtils.readDimensionFromTag(stack.getOrCreateTag().getCompound(NBTUtils.DIMENSION));
	}

}
