package nuclearscience.common.tile.reactor.moltensalt;

import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerMSReactorCore;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.object.CachedTileOutput;

public class TileMSReactorCore extends GenericTile {

	public static final int MELTDOWN_TEMPERATURE = 1000;
	public static final double FUEL_CAPACITY = 1000;
	public static final double FUEL_USAGE_RATE = 0.01;

	public static final double WASTE_CAP = 1000;
	public static final double WASTE_PER_MB = 0.01;

	public SingleProperty<Double> temperature = property(new SingleProperty<>(PropertyTypes.DOUBLE, "temperature", TileFissionReactorCore.AIR_TEMPERATURE));
	public SingleProperty<Double> currentFuel = property(new SingleProperty<>(PropertyTypes.DOUBLE, "currentfuel", 0.0));
	public SingleProperty<Double> currentWaste = property(new SingleProperty<>(PropertyTypes.DOUBLE, "currentwaste", 0.0));
	public SingleProperty<Boolean> wasteIsFull = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "wasteisfull", false));

	private CachedTileOutput outputCache;
	private CachedTileOutput plugCache;
	private CachedTileOutput controlRodCache;


	public CachedTileOutput clientPlugCache;

	public TileMSReactorCore(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_MSRREACTORCORE.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentContainerProvider("msrreactorcore", this).createMenu((id, player) -> new ContainerMSReactorCore(id, player, null, getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {

		double change = (temperature.getValue() - TileFissionReactorCore.AIR_TEMPERATURE) / 3000.0 + (temperature.getValue() - TileFissionReactorCore.AIR_TEMPERATURE) / 5000.0;
		if (change != 0) {
			temperature.setValue(temperature.getValue() - (change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change));
		}

		if (outputCache == null) {
			outputCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.UP));
		}
		if (plugCache == null) {
			plugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if(controlRodCache == null) {
			controlRodCache = new CachedTileOutput(getLevel(), getBlockPos().relative(getFacing()));
		}

		if (tick.getTicks() % 40 == 0) {
			if(!outputCache.valid()) {
				outputCache.update(new BlockPos(worldPosition).relative(Direction.UP));
			}
			if(!plugCache.valid()) {
				plugCache.update(new BlockPos(worldPosition).relative(Direction.DOWN));
			}
		}

		if(!controlRodCache.valid() && tick.getTicks() % 10 == 0) {
			controlRodCache.update(getBlockPos().relative(getFacing().getOpposite()));
		}

		if (!plugCache.valid() || !(plugCache.getSafe() instanceof TileFreezePlug freeze && freeze.isFrozen())) {
			return;
		}

		if (currentFuel.getValue() < FUEL_USAGE_RATE) {
			return;
		}

		int insertion = 0;

		if(controlRodCache.valid() && controlRodCache.getSafe() instanceof IMSControlRod rod) {

			if(rod.facingDir() == getFacing()) {
				insertion = rod.getInsertion();
			}

		}

		double insertDecimal = 1.0 - insertion / (double) TileControlRod.MAX_EXTENSION;

		double fuelUse = Math.min(currentFuel.getValue(), FUEL_USAGE_RATE * insertDecimal * Math.pow(2, Math.pow(temperature.getValue() / (MELTDOWN_TEMPERATURE - 100), 4)));

		double wasteProduced = Math.min(currentFuel.getValue(), WASTE_PER_MB * insertDecimal * Math.pow(2, Math.pow(temperature.getValue() / (MELTDOWN_TEMPERATURE - 100), 4)));

		if (currentWaste.getValue() > WASTE_CAP - wasteProduced) {
			wasteIsFull.setValue(true);
			return;
		}

		wasteIsFull.setValue(false);

		currentWaste.setValue(currentWaste.getValue() + wasteProduced);

		currentFuel.setValue(currentFuel.getValue() - fuelUse);
		temperature.setValue((temperature.getValue() + (MELTDOWN_TEMPERATURE * insertDecimal * (1.2 + level.random.nextDouble() / 5.0) - temperature.getValue()) / 600.0));
		if (outputCache.valid() && outputCache.getSafe() instanceof TileMoltenSaltPipe pipe) {

			MoltenSaltNetwork net = pipe.getNetwork();
			net.emit(temperature.getValue() * ((TileFreezePlug) plugCache.getSafe()).getSaltBonus(), new ArrayList<>(), false);
		}

		double totstrength = temperature.getValue() * Math.pow(3, Math.pow(temperature.getValue() / MELTDOWN_TEMPERATURE, 9));
		int range = (int) (Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2);
		RadiationSystem.addRadiationSource(getLevel(), new SimpleRadiationSource(totstrength, 1, range, true, 0, getBlockPos(), false));

	}

	public void tickClient(ComponentTickable tickable) {
		if (clientPlugCache == null) {
			clientPlugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if(tickable.getTicks() % 40 == 0 && !clientPlugCache.valid()) {
			clientPlugCache.update(new BlockPos(worldPosition).relative(Direction.DOWN));
		}

	}

}