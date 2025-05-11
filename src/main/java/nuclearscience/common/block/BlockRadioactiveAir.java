package nuclearscience.common.block;

import java.util.Random;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;

public class BlockRadioactiveAir extends AirBlock {

    public BlockRadioactiveAir() {
        super(Properties.copy(Blocks.AIR).noCollission().air().randomTicks());
    }

    @Override
	public void onRemove(BlockState state, World level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        RadiationSystem.removeRadiationSource(level, pos, true);
    }

    @Override
    public void entityInside(BlockState state, World lvl, BlockPos pos, Entity entityIn) {
        if (lvl.getLevelData().getGameTime() % 10 == 0) {
            RadiationSystem.addRadiationSource(lvl, new SimpleRadiationSource(20, 1, 3, true, 100, pos, true));
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (random.nextFloat() < 0.01F) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

}