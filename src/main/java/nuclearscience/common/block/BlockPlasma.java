package nuclearscience.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.api.plasma.DamageSourcePlasma;
import nuclearscience.common.tile.TilePlasma;

public class BlockPlasma extends Block {

    public BlockPlasma() {
	super(AbstractBlock.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F)
		.sound(SoundType.GLASS));
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
	return 11;
    }

    @Override
    @Deprecated
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
	entityIn.attackEntityFrom(DamageSourcePlasma.INSTANCE, 99999);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
	return new TilePlasma();
    }

    @Override
    @Deprecated
    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
	return VoxelShapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Deprecated
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
	return adjacentBlockState.isIn(this) || super.isSideInvisible(state, adjacentBlockState, side);
    }

    @Override
    @Deprecated
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	if (state.getBlock() != newState.getBlock()) {
	    super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }
}
