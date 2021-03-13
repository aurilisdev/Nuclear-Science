package nuclearscience.common.block;

import electrodynamics.api.item.IWrench;
import electrodynamics.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import nuclearscience.common.tile.TileTurbine;

public class BlockTurbine extends Block implements IWrenchable {
    public static final BooleanProperty RENDER = BooleanProperty.create("render");

    public BlockTurbine() {
	super(Properties.create(Material.IRON).hardnessAndResistance(3.5F).sound(SoundType.METAL)
		.harvestTool(ToolType.PICKAXE).notSolid());
	setDefaultState(stateContainer.getBaseState().with(RENDER, true));
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TileTurbine();
    }

    @Override
    public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
	TileTurbine turbine = (TileTurbine) player.world.getTileEntity(pos);
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
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	if (state.getBlock() != newState.getBlock()) {
	    TileTurbine turbine = (TileTurbine) worldIn.getTileEntity(pos);
	    if (turbine != null) {
		turbine.deconstructStructure();
	    }
	    super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
    }

    @Override
    @Deprecated
    public BlockRenderType getRenderType(BlockState state) {
	BlockRenderType type = super.getRenderType(state);
	if (state.get(RENDER) != Boolean.TRUE) {
	    type = BlockRenderType.INVISIBLE;
	}
	return type;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
	return 0;
    }

    @Override
    @Deprecated
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
	return 1;
    }

    @Override
    @Deprecated
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
	    Hand handIn, BlockRayTraceResult hit) {
	if (worldIn.isRemote) {
	    return ActionResultType.SUCCESS;
	} else if (!(player.getHeldItem(handIn).getItem() instanceof IWrench)) {
	    return ActionResultType.CONSUME;
	}
	return ActionResultType.FAIL;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
	return getDefaultState().with(RENDER, true);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	builder.add(RENDER);
    }

    @Override
    public void onPickup(ItemStack stack, BlockPos pos, PlayerEntity player) {
	World world = player.world;
	world.setBlockState(pos, Blocks.AIR.getDefaultState());
	world.addEntity(
		new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(getSelf())));
    }
}
