package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;

public class TileElectromagneticSwitch extends GenericTile {
    public Direction lastDirection;

    public TileElectromagneticSwitch() {
	super(DeferredRegisters.TILE_ELECTROMAGNETICSWITCH.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	if (lastDirection != null) {
	    compound.putInt("lastDirectionOrdinal", lastDirection.ordinal());
	}
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	if (compound.contains("lastDirectionOrdinal")) {
	    lastDirection = Direction.byIndex(compound.getInt("lastDirectionOrdinal"));
	}
    }
}
