package nuclearscience.common.block;

import javax.annotation.Nullable;

import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileReactorCore;

public class BlockReactorCore extends BlockGenericMachine implements IWaterLoggable {
	public BlockReactorCore() {
		super();
		setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH).with(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileReactorCore();
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(BlockStateProperties.WATERLOGGED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
		return super.getStateForPlacement(context).with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.create(0.5 / 16, 0, 0.5 / 16, 15.5 / 16.0, 15.0 / 16.0, 15.5 / 16.0);
	}

	@Override
	@Deprecated
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(BlockStateProperties.WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	@Deprecated
	public FluidState getFluidState(BlockState state) {
		return state.get(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Deprecated
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() == newState.getBlock()) {
			worldIn.markBlockRangeForRenderUpdate(pos, state, newState);
		} else {
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return 16;
	}

}
