package nuclearscience.common.block;

import javax.annotation.Nullable;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import nuclearscience.common.tile.TileControlRodAssembly;

public class BlockControlRodAssembly extends BlockGenericMachine implements SimpleWaterloggedBlock {
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileControlRodAssembly();
    }

    @Override
    @Deprecated
    public RenderShape getRenderShape(BlockState state) {
	return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
	    BlockHitResult hit) {
	BlockEntity tile = worldIn.getBlockEntity(pos);
	if (tile instanceof TileControlRodAssembly) {
	    TileControlRodAssembly assembly = (TileControlRodAssembly) tile;
	    assembly.insertion = assembly.insertion + 10;
	    if (assembly.insertion > 100) {
		assembly.insertion = 0;
	    }
	    assembly.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
	return super.use(state, worldIn, pos, player, handIn, hit);
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
    @Deprecated
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
	    BlockPos facingPos) {
	if (stateIn.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE) {
	    worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
	}
	return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    @Deprecated
    public FluidState getFluidState(BlockState state) {
	return state.getValue(BlockStateProperties.WATERLOGGED) == Boolean.TRUE ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
