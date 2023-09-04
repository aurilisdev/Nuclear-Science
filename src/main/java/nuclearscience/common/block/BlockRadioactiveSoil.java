package nuclearscience.common.block;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.radiation.RadiationSystem;

public class BlockRadioactiveSoil extends SnowyDirtBlock {

	public BlockRadioactiveSoil() {
		super(Properties.copy(Blocks.GRASS_BLOCK).randomTicks().strength(0.6F).sound(SoundType.GRASS));
	}

	@Override
	public void stepOn(Level lvl, BlockPos pos, BlockState state, Entity entityIn) {
		if (lvl.getLevelData().getGameTime() % 10 == 0) {
			RadiationSystem.emitRadiationFromLocation(lvl, new Location(pos), 3, 300);
		}
	}
}
