package nuclearscience.common.tile.reactor.logisticsnetwork.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.Interface;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public abstract class GenericTileInterfaceBound extends GenericTileLogisticsMember {

    public static final GenericTileInterface.InterfaceType[] CONTROL_RODS = {
	    GenericTileInterface.InterfaceType.FISSION, GenericTileInterface.InterfaceType.MS };
    public static final GenericTileInterface.InterfaceType[] TEMPERATURE = { GenericTileInterface.InterfaceType.FISSION,
	    GenericTileInterface.InterfaceType.MS };
    public static final GenericTileInterface.InterfaceType[] SUPPLIES = { GenericTileInterface.InterfaceType.FISSION,
	    GenericTileInterface.InterfaceType.FUSION };
    public static final GenericTileInterface.InterfaceType[] ALL = { GenericTileInterface.InterfaceType.FISSION,
	    GenericTileInterface.InterfaceType.MS, GenericTileInterface.InterfaceType.FUSION };

    public final SingleProperty<Boolean> linked = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "islinked", false))
	    .onChange((prop, old) -> {
		Level level = this.level;
		if (level == null || level.isClientSide)
		    return;

		if (BlockEntityUtils.isLit(this) ^ prop.getValue()) {
		    BlockEntityUtils.updateLit(this, prop.getValue());
		}

	    });

    public final SingleProperty<BlockPos> interfaceLocation = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.BLOCK_POS, "interfacelocation", BlockEntityUtils.OUT_OF_REACH)).onChange((prop, old) -> {
		Level level = this.level;
		if (level == null || level.isClientSide)
		    return;

		onInterfacePropChange(prop, old);

	    }).setUpdateServer();
    public final SingleProperty<Integer> interfaceType = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.INTEGER, "interfacetype", GenericTileInterface.InterfaceType.NONE.ordinal()))
	    .setUpdateServer();

    public final List<Interface> clientInterfaces = new ArrayList<>();

    public GenericTileInterfaceBound(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);

	getNetworkCable(TileReactorLogisticsCable.class).ifPresentOrElse(cable -> {

	    ReactorLogisticsNetwork network = cable.getNetwork();
	    GenericTileInterface inter = network.getInterface(interfaceLocation.getValue());

	    if (!network.isControllerActive() || inter == null) {
		linked.setValue(false);
		return;
	    }

	    if (inter.getInterfaceType().ordinal() != interfaceType.getValue() || !checkLinkedPosition(inter)) {
		interfaceLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
		interfaceType.setValue(GenericTileInterface.InterfaceType.NONE.ordinal());
		linked.setValue(false);
		return;
	    }

	    linked.setValue(true);
	}, () -> linked.setValue(false));
    }

    public abstract boolean checkLinkedPosition(GenericTileInterface inter);

    public abstract GenericTileInterface.InterfaceType[] getValidInterfaces();

    public List<Interface> getInterfacesForClient() {
	return getNetworkCable(TileReactorLogisticsCable.class).map(cable -> {
	    List<GenericTileInterface> interfaces = cable.getNetwork().getInterfacesForType(getValidInterfaces());

	    List<Interface> list = new ArrayList<>();

	    for (GenericTileInterface tile : interfaces) {
		list.add(new Interface(tile.getBlockPos(), tile.getInterfaceType()));
	    }

	    return list;
	}).orElseGet(Collections::emptyList);
    }

    public void onInterfacePropChange(SingleProperty<BlockPos> prop, BlockPos old) {

    }

}
