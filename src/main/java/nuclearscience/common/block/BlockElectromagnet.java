package nuclearscience.common.block;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.fusion.IElectromagnet;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class BlockElectromagnet extends Block implements IElectromagnet {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private final boolean isGlass;

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState state, Builder builder) {
	return Arrays.asList(new ItemStack(this));
    }

    public BlockElectromagnet(boolean isGlass) {
	super(Properties.of(isGlass ? Material.GLASS : Material.METAL).strength(3.5f, 20).harvestLevel(2)
		.harvestTool(ToolType.PICKAXE).noOcclusion().isRedstoneConductor(BlockElectromagnet::isntSolid));
	this.isGlass = isGlass;
    }

    private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
	return false;
    }

    @Override
    @Deprecated
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
	return isGlass ? Shapes.empty() : super.getVisualShape(state, reader, pos, context);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Deprecated
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
	return !isGlass ? super.skipRendering(state, adjacentBlockState, side)
		: adjacentBlockState.is(this) ? true : super.skipRendering(state, adjacentBlockState, side);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @Deprecated
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
	return isGlass ? 1.0F : super.getShadeBrightness(state, worldIn, pos);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
	return isGlass ? true : super.propagatesSkylightDown(state, reader, pos);
    }
}
