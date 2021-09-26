package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.TileFuelReprocessor;

public class BlockFuelReprocessor extends BlockGenericMachine {

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileFuelReprocessor();
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

}
