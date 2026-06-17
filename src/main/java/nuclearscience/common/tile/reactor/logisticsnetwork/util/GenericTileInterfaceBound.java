package nuclearscience.common.tile.reactor.logisticsnetwork.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.core.BlockPos;
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
	    new SingleProperty<>(PropertyTypes.BOOLEAN, "islinked", false)).onChange((prop, old) -> {

		if (level == null || level.isClientSide) {
		    return;
		}

		if (BlockEntityUtils.isLit(this) ^ prop.getValue()) {
		    BlockEntityUtils.updateLit(this, prop.getValue());
		}

	    });

    public final SingleProperty<BlockPos> interfaceLocation = property(
	    new SingleProperty<>(PropertyTypes.BLOCK_POS, "interfacelocation", BlockEntityUtils.OUT_OF_REACH))
	    .onChange((prop, old) -> {

		if (level == null || level.isClientSide) {
		    return;
		}

		onInterfacePropChange(prop, old);

	    });
    public final SingleProperty<Integer> interfaceType = property(new SingleProperty<>(PropertyTypes.INTEGER,
	    "interfacetype", GenericTileInterface.InterfaceType.NONE.ordinal()));

    public final List<Interface> clientInterfaces = new ArrayList<>();

    public GenericTileInterfaceBound(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
	super.tickServer(tickable);

	if (!networkCable.valid() || !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
	    linked.setValue(false);
	    return;
	}

	TileReactorLogisticsCable cable = networkCable.getSafe();

	if (cable.isRemoved()) {
	    linked.setValue(false);
	    return;
	}

	ReactorLogisticsNetwork network = cable.getNetwork();

	GenericTileInterface inter = network.getInterface(interfaceLocation.getValue());

	if (!network.isControllerActive() || inter == null) {
	    linked.setValue(false);
	    return;
	}

	if (inter.getInterfaceType() != GenericTileInterface.InterfaceType.values()[interfaceType.getValue()]
		|| !checkLinkedPosition(inter)) {
	    interfaceLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
	    interfaceType.setValue(GenericTileInterface.InterfaceType.NONE.ordinal());
	    linked.setValue(false);
	}

	linked.setValue(true);

    }

    public abstract boolean checkLinkedPosition(GenericTileInterface inter);

    public abstract GenericTileInterface.InterfaceType[] getValidInterfaces();

    public List<Interface> getInterfacesForClient() {
	if (networkCable == null || !networkCable.valid()
		|| !(networkCable.getSafe() instanceof TileReactorLogisticsCable)) {
	    return Collections.emptyList();
	}

	TileReactorLogisticsCable cable = networkCable.getSafe();

	if (cable.isRemoved()) {
	    return Collections.emptyList();
	}

	ReactorLogisticsNetwork network = cable.getNetwork();

	List<GenericTileInterface> interfaces = network.getInterfacesForType(getValidInterfaces());

	List<Interface> list = new ArrayList<>();

	interfaces.forEach(tile -> {
	    list.add(new Interface(tile.getBlockPos(), tile.getInterfaceType()));
	});

	return list;
    }

    public void onInterfacePropChange(SingleProperty<BlockPos> prop, BlockPos old) {

    }

}
