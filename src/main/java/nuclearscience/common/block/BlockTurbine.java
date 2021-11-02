package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import electrodynamics.api.IWrenchItem;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolType;
import nuclearscience.common.tile.TileTurbine;

public class BlockTurbine extends Block implements IWrenchable {
    public static final BooleanProperty RENDER = BooleanProperty.create("render");

    public BlockTurbine() {
	super(Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).noOcclusion());
	registerDefaultState(stateDefinition.any().setValue(RENDER, true));
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TileTurbine();
    }

    @Override
    public void onRotate(ItemStack stack, BlockPos pos, Player player) {
	TileTurbine turbine = (TileTurbine) player.level.getBlockEntity(pos);
	if (turbine != null) {
	    if (turbine.isCore) {
		turbine.deconstructStructure();
	    } else {
		turbine.constructStructure();
	    }
	}
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
    }

    @Override
    @Deprecated
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	if (state.getBlock() != newState.getBlock()) {
	    TileTurbine turbine = (TileTurbine) worldIn.getBlockEntity(pos);
	    if (turbine != null) {
		turbine.deconstructStructure();
	    }
	    super.onRemove(state, worldIn, pos, newState, isMoving);
	}
    }

    @Override
    @Deprecated
    public RenderShape getRenderShape(BlockState state) {
	RenderShape type = super.getRenderShape(state);
	if (state.getValue(RENDER) != Boolean.TRUE) {
	    type = RenderShape.INVISIBLE;
	}
	return type;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public int getLightValue(BlockState state, BlockGetter world, BlockPos pos) {
	return 0;
    }

    @Override
    @Deprecated
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
	return 1;
    }

    @Override
    @Deprecated
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
	if (worldIn.isClientSide) {
	    return InteractionResult.SUCCESS;
	} else if (!(player.getItemInHand(handIn).getItem() instanceof IWrenchItem)) {
	    return InteractionResult.CONSUME;
	}
	return InteractionResult.FAIL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
	return defaultBlockState().setValue(RENDER, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	builder.add(RENDER);
    }

    @Override
    public void onPickup(ItemStack stack, BlockPos pos, Player player) {
	Level world = player.level;
	world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(asBlock())));
    }
}
