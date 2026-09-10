package nuclearscience.common.tile.reactor.logisticsnetwork.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.moltensalt.IMSControlRod;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileMSInterface extends GenericTileInterface implements IMSControlRod {

    public final SingleProperty<Integer> insertion = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "insertion", 0));

    public TileMSInterface(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_MSINTERFACE.get(), worldPos, blockState);
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);

	insertion.setValue(getNetworkCable(TileReactorLogisticsCable.class).map(TileReactorLogisticsCable::getNetwork)
		.filter(ReactorLogisticsNetwork::isControllerActive)
		.map(network -> network.getControlRod(controlRodLocation.getValue()))
		.map(controlRod -> controlRod.insertion.getValue()).orElse(0));
    }

    @Override
    public int getInsertion() {
	return insertion.getValue();
    }

    @Override
    public Direction facingDir() {
	return getReactorDirection();
    }

    @Override
    public Direction getReactorDirection() {
	return getFacing().getOpposite();
    }

    @Override
    public Direction getCableLocation() {
	return Direction.DOWN;
    }

    @Override
    public InterfaceType getInterfaceType() {
	return InterfaceType.MS;
    }
}
