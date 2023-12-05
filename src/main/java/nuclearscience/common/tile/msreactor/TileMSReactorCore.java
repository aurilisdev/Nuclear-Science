package nuclearscience.common.tile.msreactor;


import java.util.ArrayList;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.common.tile.TileControlRodAssembly;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileMSReactorCore extends GenericTile {

	public static final int MELTDOWN_TEMPERATURE = 1000;
	public static final double FUEL_CAPACITY = 1000;
	public static final double FUEL_USAGE_RATE = 0.01;

	public static final double WASTE_CAP = 1000;
	public static final double WASTE_PER_MB = 0.01;

	public Property<Double> temperature = property(new Property<>(PropertyType.Double, "temperature", TileFissionReactorCore.AIR_TEMPERATURE));
	public Property<Double> currentFuel = property(new Property<>(PropertyType.Double, "currentfuel", 0.0));
	public Property<Double> currentWaste = property(new Property<>(PropertyType.Double, "currentwaste", 0.0));

	public Property<Boolean> wasteIsFull = property(new Property<>(PropertyType.Boolean, "wasteisfull", false));

	public int ticksOverheating = 0;
	private CachedTileOutput outputCache;
	public CachedTileOutput plugCache;

	public TileMSReactorCore(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_MSRREACTORCORE.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentContainerProvider("container.msrreactorcore", this).createMenu((id, player) -> new ContainerMSRReactorCore(id, player, null, getCoordsArray())));
	}

	public void tickServer(ComponentTickable tick) {

		double change = (temperature.get() - TileFissionReactorCore.AIR_TEMPERATURE) / 3000.0 + (temperature.get() - TileFissionReactorCore.AIR_TEMPERATURE) / 5000.0;
		if (change != 0) {
			temperature.set(temperature.get() - (change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change));
		}
		if (!plugCache.valid() || !(plugCache.getSafe() instanceof TileFreezePlug freeze && freeze.isFrozen())) {
			return;
		}

		if (currentFuel.get() < FUEL_USAGE_RATE) {
			return;
		}

		int insertion = 0;
		for (Direction dir : Direction.values()) {
			if (dir != Direction.UP && dir != Direction.DOWN) {
				BlockEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
				if (tile instanceof TileControlRodAssembly cr) {
					TileControlRodAssembly control = cr;
					if (Direction.values()[control.direction.get()] == dir.getOpposite()) {
						insertion += control.insertion.get();
					}
				}
			}
		}

		double insertDecimal = (100 - insertion) / 100.0;

		double fuelUse = Math.min(currentFuel.get(), FUEL_USAGE_RATE * insertDecimal * Math.pow(2, Math.pow(temperature.get() / (MELTDOWN_TEMPERATURE - 100), 4)));

		double wasteProduced = Math.min(currentFuel.get(), WASTE_PER_MB * insertDecimal * Math.pow(2, Math.pow(temperature.get() / (MELTDOWN_TEMPERATURE - 100), 4)));

		if (currentWaste.get() > WASTE_CAP - wasteProduced) {
			wasteIsFull.set(true);
			return;
		}

		wasteIsFull.set(false);

		currentWaste.set(currentWaste.get() + wasteProduced);

		currentFuel.set(currentFuel.get() - fuelUse);
		temperature.set((temperature.get() + (MELTDOWN_TEMPERATURE * insertDecimal * (1.2 + level.random.nextDouble() / 5.0) - temperature.get()) / 600.0));
		if (outputCache.valid() && outputCache.getSafe() instanceof IMoltenSaltPipe) {

			MoltenSaltNetwork net = (MoltenSaltNetwork) outputCache.<IMoltenSaltPipe>getSafe().getNetwork();
			net.emit(temperature.get() * ((TileFreezePlug) plugCache.getSafe()).getSaltBonus(), new ArrayList<>(), false);
		}

	}

	protected void tickCommon(ComponentTickable tick) {
		if (outputCache == null) {
			outputCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.UP));
		}
		if (plugCache == null) {
			plugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if (tick.getTicks() % 40 == 0) {
			outputCache.update(new BlockPos(worldPosition).relative(Direction.UP));
			plugCache.update(new BlockPos(worldPosition).relative(Direction.DOWN));
		}

		if (!plugCache.valid() || !(plugCache.getSafe() instanceof TileFreezePlug freeze && freeze.isFrozen())) {
			return;
		}

		if (currentFuel.get() < FUEL_USAGE_RATE) {
			return;
		}
		if (level.getLevelData().getGameTime() % 10 == 0) {
			double totstrength = temperature.get() * Math.pow(3, Math.pow(temperature.get() / MELTDOWN_TEMPERATURE, 9));
			double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
			RadiationSystem.emitRadiationFromLocation(level, new Location(worldPosition), range, totstrength);
		}
	}

}