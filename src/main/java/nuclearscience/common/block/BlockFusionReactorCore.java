package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.TileFusionReactorCore;

public class BlockFusionReactorCore extends BlockGenericMachine {
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileFusionReactorCore();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
}
