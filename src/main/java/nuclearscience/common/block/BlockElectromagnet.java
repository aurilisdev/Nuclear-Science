package nuclearscience.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.fusion.IElectromagnet;

public class BlockElectromagnet extends Block implements IElectromagnet {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	private final boolean isGlass;

	public BlockElectromagnet(boolean isGlass) {
		super(Properties.copy(isGlass ? Blocks.GLASS : Blocks.IRON_BLOCK).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((x, y, z) -> false).harvestLevel(1).harvestTool(ToolType.PICKAXE));
		this.isGlass = isGlass;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return isGlass ? VoxelShapes.empty() : super.getVisualShape(state, reader, pos, context);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return !isGlass ? super.skipRendering(state, adjacentBlockState, side) : adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return isGlass ? 1.0F : super.getShadeBrightness(state, worldIn, pos);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return isGlass || super.propagatesSkylightDown(state, reader, pos);
	}
}