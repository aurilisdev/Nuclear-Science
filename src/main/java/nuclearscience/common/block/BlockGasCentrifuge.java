package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.TileGasCentrifuge;

public class BlockGasCentrifuge extends BlockGenericMachine {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileGasCentrifuge();
    }

}
