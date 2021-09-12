package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import nuclearscience.DeferredRegisters;

public class TileFuelReprocessor extends GenericTileTicking{

	public TileFuelReprocessor() {
		super(DeferredRegisters.TILE_FUELREPROCESSOR.get());

	}

}
