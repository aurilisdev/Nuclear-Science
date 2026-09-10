package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerThermometerModule;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileThermometerModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public final SingleProperty<Integer> mode = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.INTEGER, "comparitormode", Mode.CONSTANT.ordinal())).setUpdateServer();
    public final SingleProperty<Boolean> inverted = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "inverted", false)).setUpdateServer();
    public final SingleProperty<Double> targetTemperature = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "targettemperature", 0.0))
	    .setUpdateServer();
    public final SingleProperty<Double> trackedTemperature = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.DOUBLE, "trackedtemperature", 0.0));
    public final SingleProperty<Integer> redstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "redstonesignal", 0));

    public static final int MAX_REDSTONE = 15;

    public TileThermometerModule(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_THERMOMETERMODULE.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentContainerProvider("thermometermodule", this).createMenu(
		(id, player) -> new ContainerThermometerModule(id, player, new SimpleContainer(0), getCoordsArray())));
	relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);

	GenericTileInterface.InterfaceType type = GenericTileInterface.InterfaceType.values()[interfaceType.getValue()];

	if (type == GenericTileInterface.InterfaceType.NONE
		|| interfaceLocation.getValue().equals(BlockEntityUtils.OUT_OF_REACH)) {
	    redstoneSignal.setValue(0);
	    trackedTemperature.setValue(0.0);
	    return;
	}

	double temp = getNetworkCable(TileReactorLogisticsCable.class).map(TileReactorLogisticsCable::getNetwork)
		.filter(ReactorLogisticsNetwork::isControllerActive)
		.map(network -> network.getInterface(interfaceLocation.getValue()))
		.filter(inter -> inter.getInterfaceType() == type).flatMap(GenericTileInterface::getReactor)
		.map(reactor -> {
		    if (reactor instanceof TileFissionReactorCore core) {
			return TileFissionReactorCore.getActualTemp(core.temperature.getValue());
		    }
		    if (reactor instanceof TileMSReactorCore core) {
			return core.temperature.getValue();
		    }
		    return -1.0;
		}).orElse(-1.0);

	if (temp < 0) {
	    redstoneSignal.setValue(0);
	    trackedTemperature.setValue(0.0);
	    return;
	}

	trackedTemperature.setValue(temp);

	double target = targetTemperature.getValue();

	double perc = switch (Mode.values()[mode.getValue()]) {
	case CONSTANT -> (inverted.getValue() ? temp <= target : temp >= target) ? 1.0 : 0.0;

	case BUILD_UP -> {
	    double progress = temp == 0 || target == 0 ? 0.0 : Math.min(1.0, temp / target);

	    yield inverted.getValue() ? 1.0 - progress : progress;
	}

	default -> 0.0;
	};

	redstoneSignal.setValue((int) (MAX_REDSTONE * perc));
    }

    @Override
    public boolean checkLinkedPosition(GenericTileInterface inter) {
	return true;
    }

    @Override
    public Direction getCableLocation() {
	return relativeBack;
    }

    @Override
    public void onBlockStateUpdate(Level level, BlockState oldState, BlockState newState) {
	super.onBlockStateUpdate(level, oldState, newState);
	if (!level.isClientSide() && oldState.hasProperty(VoltaicBlockStates.FACING)
		&& newState.hasProperty(VoltaicBlockStates.FACING)
		&& oldState.getValue(VoltaicBlockStates.FACING) != newState.getValue(VoltaicBlockStates.FACING)) {
	    relativeBack = BlockEntityUtils.getRelativeSide(getFacing(),
		    BlockEntityUtils.MachineDirection.BACK.mappedDir);
	}
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.saveAdditional(compound, registries);
	compound.putInt("relativeback", relativeBack.ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
	super.loadAdditional(compound, registries);
	relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
	return TEMPERATURE;
    }

    @Override
    public int getComparatorSignal(Level level) {
	return redstoneSignal.getValue();
    }

    public static enum Mode {

	BUILD_UP,
	CONSTANT;

    }

}
