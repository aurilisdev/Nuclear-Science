package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;

public class TileElectromagneticSwitch extends GenericTile {
    public Direction lastDirection;

    public TileElectromagneticSwitch() {
	super(DeferredRegisters.TILE_ELECTROMAGNETICSWITCH.get());
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
	if (lastDirection != null) {
	    compound.putInt("lastDirectionOrdinal", lastDirection.ordinal());
	}
	return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
	super.load(state, compound);
	if (compound.contains("lastDirectionOrdinal")) {
	    lastDirection = Direction.from3DDataValue(compound.getInt("lastDirectionOrdinal"));
	}
    }
}
