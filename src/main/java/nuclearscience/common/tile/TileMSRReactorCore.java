package nuclearscience.common.tile;

import java.util.ArrayList;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.tileentity.TileEntity;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.network.MoltenSaltNetwork;

public class TileMSRReactorCore extends GenericTileTicking {
    public static final int MELTDOWN_TEMPERATURE = 900;
    public static final double FUEL_CAPACITY = 1000;
    public static final double FUEL_USAGE_RATE = 0.1;
    public double temperature = TileReactorCore.AIR_TEMPERATURE;
    public int ticksOverheating = 0;
    public double currentFuel = 0;

    public TileMSRReactorCore() {
	super(DeferredRegisters.TILE_MSRREACTORCORE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
    }

    private void tickServer(ComponentTickable tick) {
	if (currentFuel > 0) {
	    currentFuel -= FUEL_USAGE_RATE;
	    double change = (temperature - TileReactorCore.AIR_TEMPERATURE) / 3000.0 + (temperature - TileReactorCore.AIR_TEMPERATURE) / 5000.0;
	    if (change != 0) {
		temperature -= change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change;
	    }
	    temperature += (MELTDOWN_TEMPERATURE * 0 * (0.5 + world.rand.nextDouble() / 5.0) - temperature) / (200 + 80);
	    TileEntity above = world.getTileEntity(pos.up());
	    if (above instanceof IMoltenSaltPipe) {
		MoltenSaltNetwork net = (MoltenSaltNetwork) ((IMoltenSaltPipe) above).getNetwork();
		net.emit(temperature, new ArrayList<>(), false);
	    }
	}
    }

    private void tickClient(ComponentTickable tick) {
    }
}