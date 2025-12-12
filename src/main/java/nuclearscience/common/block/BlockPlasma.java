package nuclearscience.common.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.tile.reactor.fusion.TilePlasma;
import nuclearscience.registers.NuclearScienceDamageTypes;
import voltaic.prefab.block.GenericEntityBlock;

public class BlockPlasma extends GenericEntityBlock {

    public BlockPlasma() {
	super(Blocks.NETHER_PORTAL.properties().noCollission().randomTicks().strength(-1.0F).sound(SoundType.GLASS));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
	return new TilePlasma(pos, state);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
	return 11;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
	entityIn.hurt(entityIn.damageSources().source(NuclearScienceDamageTypes.PLASMA), 99999);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
	return Shapes.empty();
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
	return adjacentBlockState.is(this) || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	if (state.getBlock() != newState.getBlock()) {
	    super.onRemove(state, worldIn, pos, newState, isMoving);
	}
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
	return null;
    }
}
