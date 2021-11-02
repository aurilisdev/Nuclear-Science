package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;
import nuclearscience.common.tile.TileRadioisotopeGenerator;

public class BlockRadioisotopeGenerator extends BlockGenericMachine {
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileRadioisotopeGenerator();
    }

}
