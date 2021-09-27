package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import nuclearscience.DeferredRegisters;

public class TileMSRReactorCore extends GenericTileTicking {

    public TileMSRReactorCore() {
	super(DeferredRegisters.TILE_MSRREACTORCORE.get());
	addComponent(new ComponentDirection());
    }

}
