package nuclearscience.common.block;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.tile.TileReactorCore;

public class BlockReactorCore extends BlockGenericMachine {
    public BlockReactorCore() {
	registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileReactorCore(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	return Shapes.box(0.5 / 16, 0, 0.5 / 16, 15.5 / 16.0, 15.0 / 16.0, 15.5 / 16.0);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	if (state.getBlock() == newState.getBlock()) {
	    worldIn.setBlocksDirty(pos, state, newState);
	} else {
	    super.onRemove(state, worldIn, pos, newState, isMoving);
	}
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
	BlockEntity core = world.getBlockEntity(pos);
	if (core instanceof TileReactorCore rc) {
	    return (int) Math.max(0, Math.min(rc.temperature / TileReactorCore.MELTDOWN_TEMPERATURE_ACTUAL * 15, 15));
	}
	return 0;
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
	BlockEntity core = world.getBlockEntity(pos);
	if (core instanceof TileReactorCore rc) {
	    rc.meltdown();
	}
	super.onBlockExploded(state, world, pos, explosion);
    }

}
