package nuclearscience.common.block;

import electrodynamics.api.tile.IWrenchable;
import electrodynamics.common.block.BlockGenericMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.block.facing.FacingDirectionProperty;

public class BlockElectromagneticBooster extends Block implements IElectromagnet, IWrenchable {
	public static final FacingDirectionProperty FACINGDIRECTION = FacingDirectionProperty.create("side", FacingDirection.values());

	public BlockElectromagneticBooster() {
		super(Properties.create(Material.GLASS).hardnessAndResistance(3.5f, 20).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid().setOpaque(BlockElectromagneticBooster::isntSolid));
		setDefaultState(stateContainer.getBaseState().with(BlockGenericMachine.FACING, Direction.NORTH).with(FACINGDIRECTION, FacingDirection.NONE));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(BlockGenericMachine.FACING, rot.rotate(state.get(BlockGenericMachine.FACING)));
	}

	@Deprecated
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(BlockGenericMachine.FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction movingdirection = context.getPlacementHorizontalFacing();
		BlockState state = getDefaultState().with(BlockGenericMachine.FACING, context.getPlacementHorizontalFacing().getOpposite());
		// left check first in front
		BlockState check = context.getWorld().getBlockState(context.getPos().offset(movingdirection.rotateY().getOpposite()));
		if (check.getBlock() == this && check.get(BlockGenericMachine.FACING).getOpposite() == movingdirection.rotateY().getOpposite()) {
			state = state.with(FACINGDIRECTION, FacingDirection.LEFT);
		} else {
			check = context.getWorld().getBlockState(context.getPos().offset(movingdirection.rotateY()));
			if (check.getBlock() == this && check.get(BlockGenericMachine.FACING).getOpposite() == movingdirection.rotateY()) {
				state = state.with(FACINGDIRECTION, FacingDirection.RIGHT);
			}
		}
		return state;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockGenericMachine.FACING);
		builder.add(FACINGDIRECTION);
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
		player.world.setBlockState(pos, rotate(player.world.getBlockState(pos), Rotation.CLOCKWISE_90));
	}

	@Override
	public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
		World world = player.world;
		BlockState current = world.getBlockState(pos);
		FacingDirection face = current.get(FACINGDIRECTION);
		if (face != FacingDirection.NONE) {
			current = current.with(FACINGDIRECTION, face == FacingDirection.LEFT ? FacingDirection.RIGHT : FacingDirection.LEFT);
			if (face == FacingDirection.RIGHT) {
				current = rotate(current, Rotation.CLOCKWISE_180);
			}
			current = rotate(current, Rotation.CLOCKWISE_90);
		} else {
			current = rotate(current, Rotation.CLOCKWISE_180);
		}
		world.setBlockState(pos, current);
	}

	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

	@Override
	@Deprecated
	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	@Deprecated
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
}
