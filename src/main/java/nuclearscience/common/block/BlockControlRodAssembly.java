package nuclearscience.common.block;

import javax.annotation.Nullable;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends BlockGenericMachine implements IWaterLoggable {
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileControlRodAssembly();
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
	    BlockRayTraceResult hit) {
	TileEntity tile = worldIn.getTileEntity(pos);
	if (tile instanceof TileControlRodAssembly) {
	    TileControlRodAssembly assembly = (TileControlRodAssembly) tile;
	    assembly.insertion = assembly.insertion + 10;
	    if (assembly.insertion > 100) {
		assembly.insertion = 0;
	    }
	    assembly.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
	return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
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
    @Deprecated
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos,
	    BlockPos facingPos) {
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
}
