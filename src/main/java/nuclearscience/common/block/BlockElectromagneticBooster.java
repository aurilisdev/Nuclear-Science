package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.common.block.BlockGenericMachine;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.block.facing.FacingDirectionProperty;

public class BlockElectromagneticBooster extends Block implements IElectromagnet, IWrenchable {
    public static final FacingDirectionProperty FACINGDIRECTION = FacingDirectionProperty.create("side", FacingDirection.values());

    public BlockElectromagneticBooster() {
	super(Properties.of(Material.GLASS).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion()
		.isRedstoneConductor((p_152653_, p_152654_, p_152655_) -> {
		    return false;
		}));
	registerDefaultState(
		stateDefinition.any().setValue(BlockGenericMachine.FACING, Direction.NORTH).setValue(FACINGDIRECTION, FacingDirection.NONE));
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
    }

    @Override
    @Deprecated
    public BlockState rotate(BlockState state, Rotation rot) {
	return state.setValue(BlockGenericMachine.FACING, rot.rotate(state.getValue(BlockGenericMachine.FACING)));
    }

    @Deprecated
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
	return state.rotate(mirrorIn.getRotation(state.getValue(BlockGenericMachine.FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
	Direction movingdirection = context.getHorizontalDirection();
	BlockState state = defaultBlockState().setValue(BlockGenericMachine.FACING, context.getHorizontalDirection().getOpposite());
	// left check first in front
	BlockState check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise().getOpposite()));
	if (check.getBlock() == this && check.getValue(BlockGenericMachine.FACING).getOpposite() == movingdirection.getClockWise().getOpposite()) {
	    state = state.setValue(FACINGDIRECTION, FacingDirection.LEFT);
	} else {
	    check = context.getLevel().getBlockState(context.getClickedPos().relative(movingdirection.getClockWise()));
	    if (check.getBlock() == this && check.getValue(BlockGenericMachine.FACING).getOpposite() == movingdirection.getClockWise()) {
		state = state.setValue(FACINGDIRECTION, FacingDirection.RIGHT);
	    }
	}
	return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	builder.add(BlockGenericMachine.FACING);
	builder.add(FACINGDIRECTION);
    }

    @Override
    public void onRotate(ItemStack stack, BlockPos pos, Player player) {
	player.level.setBlockAndUpdate(pos, rotate(player.level.getBlockState(pos), Rotation.CLOCKWISE_90));
    }

    @Override
    public void onPickup(ItemStack stack, BlockPos pos, Player player) {
	Level world = player.level;
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
    @Deprecated
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
	return Shapes.empty();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Deprecated
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
	return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
	return true;
    }
}
