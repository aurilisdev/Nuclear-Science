package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileMSRReactorCore;

public class BlockMSRReactorCore extends BlockGenericMachine {
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileMSRReactorCore(pos, state);
    }

}
