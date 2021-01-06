package nuclearscience.common.tile;

import electrodynamics.common.tile.generic.GenericTileInventory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;

public class TileReactorCore extends GenericTileInventory {

	public static final int[] SLOTS_UP = new int[] { 0, 1, 2, 3, 4, 5 };

	public TileReactorCore() {
		super(DeferredRegisters.TILE_REACTORCORE.get());
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_UP : SLOTS_EMPTY;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return null;
	}

}
