package nuclearscience.common.block;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.radiation.RadiationSystem;

public class BlockRadioactiveSoil extends SnowyDirtBlock {

	public BlockRadioactiveSoil() {
		super(Properties.of(Material.GRASS, MaterialColor.COLOR_GREEN).randomTicks().strength(0.6F).sound(SoundType.GRASS).harvestLevel(2).harvestTool(ToolType.SHOVEL));
	}

	@Override
	public void stepOn(World lvl, BlockPos pos, Entity entityIn) {
		if (lvl.getLevelData().getGameTime() % 10 == 0) {
			RadiationSystem.emitRadiationFromLocation(lvl, new Location(pos), 3, 300);
		}
	}
}