package nuclearscience.common.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.api.plasma.DamageSourcePlasma;
import nuclearscience.common.tile.TilePlasma;

public class BlockPlasma extends Block {

    public BlockPlasma() {
	super(BlockBehaviour.Properties.of(Material.PORTAL).noCollission().randomTicks().strength(-1.0F)
		.sound(SoundType.GLASS));
    }

    @Override
    public int getLightValue(BlockState state, BlockGetter world, BlockPos pos) {
	return 11;
    }

    @Override
    @Deprecated
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
	entityIn.hurt(DamageSourcePlasma.INSTANCE, 99999);
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
	return new TilePlasma();
    }

    @Override
    @Deprecated
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
	return Shapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Deprecated
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
	return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    @Deprecated
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	if (state.getBlock() != newState.getBlock()) {
	    super.onRemove(state, worldIn, pos, newState, isMoving);
	}
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }
}
