package nuclearscience.common.tile.reactor.moltensalt;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileMSReactorCore extends GenericTile {

    public static final int MELTDOWN_TEMPERATURE = 1000;
    public static final double FUEL_CAPACITY = 1000;
    public static final double FUEL_USAGE_RATE = 0.01;

    public static final double WASTE_CAP = 1000;
    public static final double WASTE_PER_MB = 0.01;

    public SingleProperty<Double> temperature = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.DOUBLE, "temperature", TileFissionReactorCore.AIR_TEMPERATURE));
    public SingleProperty<Double> currentFuel = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "currentfuel", 0.0));
    public SingleProperty<Double> currentWaste = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "currentwaste", 0.0));
    public SingleProperty<Boolean> wasteIsFull = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "wasteisfull", false));

    public TileMSReactorCore(BlockPos pos, BlockState state) {
	super(NuclearScienceTiles.TILE_MSRREACTORCORE.get(), pos, state);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentContainerProvider("msrreactorcore", this)
		.createMenu((id, player) -> new ContainerMSReactorCore(id, player, getCoordsArray())));
    }

    public void tickServer(Level level, ComponentTickable tick) {
	double totstrength = temperature.getValue()
		* Math.pow(3, Math.pow(temperature.getValue() / MELTDOWN_TEMPERATURE, 9));
	int range = (int) (Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2);
	if (range > 0 && totstrength > 0 && temperature.getValue() > TileFissionReactorCore.AIR_TEMPERATURE) {
	    RadiationSystem.addRadiationSource(level,
		    new SimpleRadiationSource(totstrength, 1, range, true, 30, getBlockPos(), true, false));
	}

	double change = (temperature.getValue() - TileFissionReactorCore.AIR_TEMPERATURE) / 3000.0
		+ (temperature.getValue() - TileFissionReactorCore.AIR_TEMPERATURE) / 5000.0;
	if (change != 0) {
	    temperature.setValue(temperature.getValue()
		    - (change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change));
	}

	if (!(level.getBlockEntity(worldPosition.below()) instanceof TileFreezePlug freeze) || freeze.isRemoved()
		|| !freeze.isFrozen() || currentFuel.getValue() < FUEL_USAGE_RATE)
	    return;

	Direction facing = getFacing();
	BlockEntity controlRod = level.getBlockEntity(worldPosition.relative(facing.getOpposite()));
	int insertion = 0;
	if (controlRod instanceof IMSControlRod rod && !controlRod.isRemoved() && rod.facingDir() == facing) {
	    insertion = rod.getInsertion();
	}

	double insertDecimal = 1.0 - insertion / (double) TileControlRod.MAX_EXTENSION;
	double fuelUse = Math.min(currentFuel.getValue(), FUEL_USAGE_RATE * insertDecimal
		* Math.pow(2, Math.pow(temperature.getValue() / (MELTDOWN_TEMPERATURE - 100), 4)));
	double wasteProduced = Math.min(currentFuel.getValue(), WASTE_PER_MB * insertDecimal
		* Math.pow(2, Math.pow(temperature.getValue() / (MELTDOWN_TEMPERATURE - 100), 4)));

	if (currentWaste.getValue() > WASTE_CAP - wasteProduced) {
	    wasteIsFull.setValue(true);
	    return;
	}

	wasteIsFull.setValue(false);
	currentWaste.setValue(currentWaste.getValue() + wasteProduced);
	currentFuel.setValue(currentFuel.getValue() - fuelUse);
	temperature.setValue(
		temperature.getValue() + (MELTDOWN_TEMPERATURE * insertDecimal * (1.2 + level.random.nextDouble() / 5.0)
			- temperature.getValue()) / 600.0);

	if (level.getBlockEntity(worldPosition.above()) instanceof TileMoltenSaltPipe pipe && !pipe.isRemoved()) {
	    MoltenSaltNetwork net = pipe.getNetwork();
	    net.emit(temperature.getValue() * freeze.getSaltBonus(), new ArrayList<>(), false);
	}
    }

    public Optional<TileFreezePlug> getFreezePlug() {
	Level level = getLevel();
	if (level == null) {
	    return Optional.empty();
	}
	if (level.getBlockEntity(worldPosition.below()) instanceof TileFreezePlug plug && !plug.isRemoved()) {
	    return Optional.of(plug);
	}
	return Optional.empty();
    }

}