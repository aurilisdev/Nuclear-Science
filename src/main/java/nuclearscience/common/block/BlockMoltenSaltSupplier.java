package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.TileMoltenSaltSupplier;

public class BlockMoltenSaltSupplier extends BlockGenericMachine {
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileMoltenSaltSupplier(pos, state);
    }

    @Override
    @Deprecated
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
