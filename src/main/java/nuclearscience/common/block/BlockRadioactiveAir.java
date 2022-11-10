package nuclearscience.common.block;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.api.radiation.RadiationSystem;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class BlockRadioactiveAir extends AirBlock {

	public BlockRadioactiveAir() {
		super(Properties.of(Material.AIR).noCollission().air());
	}

	@Override
	public void entityInside(BlockState state, Level lvl, BlockPos pos, Entity entityIn) {
		if (lvl.getLevelData().getGameTime() % 10 == 0) {
			RadiationSystem.emitRadiationFromLocation(lvl, new Location(pos), 3, 500);
		}
	}

}