package nuclearscience.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.tile.IWrenchable;

public class BlockElectromagneticDiode extends Block implements IWrenchable {

    private static final VoxelShape SHAPE = VoxelShapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

    public BlockElectromagneticDiode() {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((p1, p2, p3) -> false));
        registerDefaultState(stateDefinition.any().setValue(VoltaicBlockStates.FACING, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(VoltaicBlockStates.FACING, rot.rotate(state.getValue(VoltaicBlockStates.FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(VoltaicBlockStates.FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).setValue(VoltaicBlockStates.FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder <Block, BlockState> builder) {
        builder.add(VoltaicBlockStates.FACING);
    }

    @Override
    public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
        player.level.setBlockAndUpdate(pos, rotate(player.level.getBlockState(pos), Rotation.CLOCKWISE_90));
    }

    @Override
    public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
        World world = player.level;
        world.destroyBlock(pos, true, player);
    }

    @Override
	public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
