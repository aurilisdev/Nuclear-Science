package nuclearscience.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
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
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	private final boolean isGlass;

	public BlockElectromagnet(boolean isGlass) {
		super(Properties.create(isGlass ? Material.GLASS : Material.IRON).hardnessAndResistance(3.5f, 20).harvestLevel(2).harvestTool(ToolType.PICKAXE).notSolid().setOpaque(BlockElectromagnet::isntSolid));
		this.isGlass = isGlass;
	}

	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		return false;
	}

	@Override
	@Deprecated
	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		return isGlass ? VoxelShapes.empty() : super.getRayTraceShape(state, reader, pos, context);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	@Deprecated
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
		return !isGlass ? super.isSideInvisible(state, adjacentBlockState, side) : adjacentBlockState.isIn(this) ? true : super.isSideInvisible(state, adjacentBlockState, side);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	@Deprecated
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return isGlass ? 1.0F : super.getAmbientOcclusionLightValue(state, worldIn, pos);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return isGlass ? true : super.propagatesSkylightDown(state, reader, pos);
	}
}
