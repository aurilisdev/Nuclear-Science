package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileTeleporter;

public class BlockTeleporter extends BlockGenericMachine {
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileTeleporter(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
