package nuclearscience.common.block;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.api.radiation.RadiationSystem;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class BlockRadioactiveAir extends AirBlock {

	public BlockRadioactiveAir() {
		super(Properties.copy(Blocks.AIR).noCollission().air());
	}

	@Override
	public void entityInside(BlockState state, Level lvl, BlockPos pos, Entity entityIn) {
		if (lvl.getLevelData().getGameTime() % 10 == 0) {
			RadiationSystem.emitRadiationFromLocation(lvl, new Location(pos), 3, 500);
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.randomTick(state, level, pos, random);
		if(random.nextFloat() < 0.01F) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		}
	}

}