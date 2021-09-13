package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import nuclearscience.DeferredRegisters;

public class TileRadioactiveProcessor extends GenericTileTicking{

	public TileRadioactiveProcessor() {
		super(DeferredRegisters.TILE_RADIOACTIVEPROCESSOR.get());

	}

}
