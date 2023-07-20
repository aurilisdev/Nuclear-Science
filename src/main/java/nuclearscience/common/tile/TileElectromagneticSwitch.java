package nuclearscience.common.tile;

import org.jetbrains.annotations.NotNull;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileElectromagneticSwitch extends GenericTile {
	public Direction lastDirection;

	public TileElectromagneticSwitch(BlockPos worldPosition, BlockState blockState) {
		super(NuclearScienceBlockTypes.TILE_ELECTROMAGNETICSWITCH.get(), worldPosition, blockState);
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) {
		if (lastDirection != null) {
			compound.putInt("lastDirectionOrdinal", lastDirection.ordinal());
		}
		super.saveAdditional(compound);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		super.load(compound);
		if (compound.contains("lastDirectionOrdinal")) {
			lastDirection = Direction.from3DDataValue(compound.getInt("lastDirectionOrdinal"));
		}
	}
}
