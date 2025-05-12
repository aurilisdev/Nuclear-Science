package nuclearscience.common.block;

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
import nuclearscience.common.block.states.NuclearScienceBlockStates;
import nuclearscience.common.block.states.facing.FacingDirection;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.tile.IWrenchable;

public class BlockElectromagneticBooster extends Block implements IWrenchable {


	public BlockElectromagneticBooster() {
		super(Properties.copy(Blocks.GLASS).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((x, y, z) -> false));
		registerDefaultState(stateDefinition.any().setValue(VoltaicBlockStates.FACING, Direction.NORTH).setValue(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.NONE));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(VoltaicBlockStates.FACING, rot.rotate(state.getValue(VoltaicBlockStates.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(VoltaicBlockStates.FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction movingdirection = context.getHorizontalDirection();
		BlockState state = super.getStateForPlacement(context).setValue(VoltaicBlockStates.FACING, context.getHorizontalDirection().getOpposite());
		// left check first in front
		BlockState check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise().getOpposite()));
		if (check.getBlock() == this && check.getValue(VoltaicBlockStates.FACING).getOpposite() == movingdirection.getClockWise().getOpposite()) {
			state = state.setValue(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.LEFT);
		} else {
			check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise()));
			if (check.getBlock() == this && check.getValue(VoltaicBlockStates.FACING).getOpposite() == movingdirection.getClockWise()) {
				state = state.setValue(NuclearScienceBlockStates.FACINGDIRECTION, FacingDirection.RIGHT);
			}
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder <Block, BlockState> builder) {
		builder.add(VoltaicBlockStates.FACING);
		builder.add(NuclearScienceBlockStates.FACINGDIRECTION);
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
		player.level.setBlockAndUpdate(pos, rotate(player.level.getBlockState(pos), Rotation.CLOCKWISE_90));
	}

	@Override
	public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
		World world = player.level;
		BlockState current = world.getBlockState(pos);
		FacingDirection face = current.getValue(NuclearScienceBlockStates.FACINGDIRECTION);
		if (face != FacingDirection.NONE) {
			current = current.setValue(NuclearScienceBlockStates.FACINGDIRECTION, face == FacingDirection.LEFT ? FacingDirection.RIGHT : FacingDirection.LEFT);
			if (face == FacingDirection.RIGHT) {
				current = rotate(current, Rotation.CLOCKWISE_180);
			}
			current = rotate(current, Rotation.CLOCKWISE_90);
		} else {
			current = rotate(current, Rotation.CLOCKWISE_180);
		}
		world.setBlockAndUpdate(pos, current);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
}
