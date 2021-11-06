package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;

public class TileElectromagneticSwitch extends GenericTile {
    public Direction lastDirection;

    public TileElectromagneticSwitch(BlockPos worldPosition, BlockState blockState) {
	super(DeferredRegisters.TILE_ELECTROMAGNETICSWITCH.get(), worldPosition, blockState);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
	if (lastDirection != null) {
	    compound.putInt("lastDirectionOrdinal", lastDirection.ordinal());
	}
	return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
	super.load(compound);
	if (compound.contains("lastDirectionOrdinal")) {
	    lastDirection = Direction.from3DDataValue(compound.getInt("lastDirectionOrdinal"));
	}
    }
}
