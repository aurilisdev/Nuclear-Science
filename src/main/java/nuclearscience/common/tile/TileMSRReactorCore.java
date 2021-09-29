package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import nuclearscience.DeferredRegisters;

public class TileMSRReactorCore extends GenericTileTicking {
    public static final int MELTDOWN_TEMPERATURE = 900;
    public static final int FUEL_CAPACITY = 1000;
    public double temperature = TileReactorCore.AIR_TEMPERATURE;
    public int ticksOverheating = 0;
    public int currentFuel = 0;

    public TileMSRReactorCore() {
	super(DeferredRegisters.TILE_MSRREACTORCORE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
    }

    private void tickServer(ComponentTickable tick) {
	if (currentFuel > 0) {

	}
    }

    private void tickClient(ComponentTickable tick) {
    }
}