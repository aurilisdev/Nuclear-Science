package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileGasCentrifuge;

public class BlockGasCentrifuge extends BlockGenericMachine {
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileGasCentrifuge();
	}

	@Deprecated
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof IInventory) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tile);
		}
		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
}
