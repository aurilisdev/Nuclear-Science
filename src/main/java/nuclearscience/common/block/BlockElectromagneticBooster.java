package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.block.facing.FacingDirectionProperty;

public class BlockElectromagneticBooster extends Block implements IElectromagnet, IWrenchable {
	public static final FacingDirectionProperty FACINGDIRECTION = FacingDirectionProperty.create("side", FacingDirection.values());

	public BlockElectromagneticBooster() {
		super(Properties.copy(Blocks.GLASS).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((x, y, z) -> false).harvestLevel(1).harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(GenericEntityBlock.FACING, Direction.NORTH).setValue(FACINGDIRECTION, FacingDirection.NONE));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(GenericEntityBlock.FACING, rot.rotate(state.getValue(GenericEntityBlock.FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(GenericEntityBlock.FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction movingdirection = context.getHorizontalDirection();
		BlockState state = super.getStateForPlacement(context).setValue(GenericEntityBlock.FACING, context.getHorizontalDirection().getOpposite());
		// left check first in front
		BlockState check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise().getOpposite()));
		if (check.getBlock() == this && check.getValue(GenericEntityBlock.FACING).getOpposite() == movingdirection.getClockWise().getOpposite()) {
			state = state.setValue(FACINGDIRECTION, FacingDirection.LEFT);
		} else {
			check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise()));
			if (check.getBlock() == this && check.getValue(GenericEntityBlock.FACING).getOpposite() == movingdirection.getClockWise()) {
				state = state.setValue(FACINGDIRECTION, FacingDirection.RIGHT);
			}
		}
		return state;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACINGDIRECTION);
		builder.add(GenericEntityBlock.FACING);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
		player.level.setBlockAndUpdate(pos, rotate(player.level.getBlockState(pos), Rotation.CLOCKWISE_90));
	}

	@Override
	public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
		World world = player.level;
		BlockState current = world.getBlockState(pos);
		FacingDirection face = current.getValue(FACINGDIRECTION);
		if (face != FacingDirection.NONE) {
			current = current.setValue(FACINGDIRECTION, face == FacingDirection.LEFT ? FacingDirection.RIGHT : FacingDirection.LEFT);
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
}