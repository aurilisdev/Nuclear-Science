package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends BlockGenericMachine {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileControlRodAssembly();
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

}
