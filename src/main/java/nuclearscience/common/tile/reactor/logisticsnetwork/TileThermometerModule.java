package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
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

    public final SingleProperty<Integer> mode = property(new SingleProperty<>(PropertyTypes.INTEGER, "comparitormode", Mode.CONSTANT.ordinal()));
    public final SingleProperty<Boolean> inverted = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "inverted", false));
    public final SingleProperty<Double> targetTemperature = property(new SingleProperty<>(PropertyTypes.DOUBLE, "targettemperature", 0.0));
    public final SingleProperty<Double> trackedTemperature = property(new SingleProperty<>(PropertyTypes.DOUBLE, "trackedtemperature", 0.0));
    public final SingleProperty<Integer> redstoneSignal = property(new SingleProperty<>(PropertyTypes.INTEGER, "redstonesignal", 0));

    public static final int MAX_REDSTONE = 15;

    public TileThermometerModule(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_THERMOMETERMODULE.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentContainerProvider("thermometermodule", this).createMenu((id, player) -> new ContainerThermometerModule(id, player, new SimpleContainer(0), getCoordsArray())));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        GenericTileInterface.InterfaceType type = GenericTileInterface.InterfaceType.values()[interfaceType.getValue()];

        if (type == GenericTileInterface.InterfaceType.NONE || interfaceLocation.getValue().equals(BlockEntityUtils.OUT_OF_REACH) || !networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
            redstoneSignal.setValue(0);
            trackedTemperature.setValue(0.0);
            return;
        }

        TileReactorLogisticsCable cable = networkCable.getSafe();

        if (cable.isRemoved()) {
            redstoneSignal.setValue(0);
            trackedTemperature.setValue(0.0);
            return;
        }

        ReactorLogisticsNetwork network = cable.getNetwork();

        if (!network.isControllerActive()) {
            redstoneSignal.setValue(0);
            trackedTemperature.setValue(0.0);
            return;
        }

        GenericTileInterface genericInterface = network.getInterface(interfaceLocation.getValue());

        if (genericInterface == null || genericInterface.getInterfaceType() != type) {
            redstoneSignal.setValue(0);
            trackedTemperature.setValue(0.0);
            return;
        }

        if (genericInterface.reactor == null || !genericInterface.reactor.valid()) {
            redstoneSignal.setValue(0);
            trackedTemperature.setValue(0.0);
            return;
        }

        double temp = -1;

        if (genericInterface.reactor.getSafe() instanceof TileFissionReactorCore core) {

            temp = TileFissionReactorCore.getActualTemp(core.temperature.getValue());

        } else if (genericInterface.reactor.getSafe() instanceof TileMSReactorCore core) {

            temp = core.temperature.getValue();

        }

        if (temp < 0) {
            redstoneSignal.setValue(0);
            trackedTemperature.setValue(0.0);
            return;
        }

        double perc = 0;

        trackedTemperature.setValue(temp);

        switch (Mode.values()[mode.getValue()]) {
            case CONSTANT:
                if (inverted.getValue()) {
                    if (temp <= targetTemperature.getValue()) {
                        perc = 1;
                    } else {
                        perc = 0;
                    }
                } else {
                    if (temp >= targetTemperature.getValue()) {
                        perc = 1;
                    } else {
                        perc = 0;
                    }
                }

                break;
            case BUILD_UP:

                if (inverted.getValue()) {
                    if (temp == 0 || targetTemperature.getValue() == 0) {
                        perc = 1;
                    } else {

                        perc = 1.0 - Math.min(1, temp / targetTemperature.getValue());

                    }
                } else {
                    if (temp == 0 || targetTemperature.getValue() == 0) {
                        perc = 0;
                    } else {

                        perc = Math.min(1, temp / targetTemperature.getValue());

                    }

                }

                break;
        }

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
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        if (!level.isClientSide() && oldState.hasProperty(VoltaicBlockStates.FACING) && newState.hasProperty(VoltaicBlockStates.FACING) && oldState.getValue(VoltaicBlockStates.FACING) != newState.getValue(VoltaicBlockStates.FACING)) {
            relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("relativeback", relativeBack.ordinal());
    }

    @Override
	public void load(CompoundTag compound) {
        super.load(compound);
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return TEMPERATURE;
    }

    @Override
    public int getComparatorSignal() {
        return redstoneSignal.getValue();
    }

    public static enum Mode {

        BUILD_UP, CONSTANT;

    }


}
