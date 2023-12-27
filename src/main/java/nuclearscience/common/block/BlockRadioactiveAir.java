package nuclearscience.common.block;

import java.util.Random;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import nuclearscience.api.radiation.RadiationSystem;

public class BlockRadioactiveAir extends AirBlock {

	public BlockRadioactiveAir() {
		super(Properties.copy(Blocks.AIR).noCollission().air());
	}

	@Override
	public void entityInside(BlockState state, World lvl, BlockPos pos, Entity entityIn) {
		if (lvl.getLevelData().getGameTime() % 10 == 0) {
			RadiationSystem.emitRadiationFromLocation(lvl, new Location(pos), 3, 500);
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
