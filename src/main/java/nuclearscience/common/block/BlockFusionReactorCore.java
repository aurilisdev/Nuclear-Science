package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileFusionReactorCore;

public class BlockFusionReactorCore extends BlockGenericMachine {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileFusionReactorCore();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
	return BlockRenderType.MODEL;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
	    Hand handIn, BlockRayTraceResult hit) {
	if (!worldIn.isRemote) {
	    ItemStack inHand = player.getItemStackFromSlot(
		    handIn == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND);
	    Item itemInHand = inHand.getItem();
	    if (itemInHand == DeferredRegisters.ITEM_CELLDEUTERIUM.get()
		    || itemInHand == DeferredRegisters.ITEM_CELLTRITIUM.get()) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileFusionReactorCore) {
		    TileFusionReactorCore core = (TileFusionReactorCore) tile;
		    boolean tritium = itemInHand == DeferredRegisters.ITEM_CELLTRITIUM.get();
		    int type = tritium ? core.tritium : core.deuterium;
		    int added = Math.min(inHand.getCount(), Constants.FUSIONREACTOR_MAXSTORAGE - type);
		    inHand.setCount(inHand.getCount() - added);
		    if (tritium) {
			core.tritium += added;
		    } else {
			core.deuterium += added;
		    }
		}
		return ActionResultType.CONSUME;
	    }
	}
	return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
