package nuclearscience.common.block;

import javax.annotation.Nullable;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.tile.TileReactorCore;

public class BlockReactorCore extends BlockGenericMachine implements SimpleWaterloggedBlock {
    public BlockReactorCore() {
	super();
	registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	super.createBlockStateDefinition(builder);
	builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
	FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
	return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
	    BlockPos facingPos) {
	if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
	    worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
	}
	return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
	return state.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TileReactorCore(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.MODEL;
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
