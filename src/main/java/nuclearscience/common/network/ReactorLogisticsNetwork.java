package nuclearscience.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileController;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileReactorLogisticsCable;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import voltaic.common.network.NetworkRegistry;
import voltaic.prefab.network.AbstractNetwork;

public class ReactorLogisticsNetwork extends
	AbstractNetwork<TileReactorLogisticsCable, SubtypeReactorLogisticsCable, Void, ReactorLogisticsNetwork> {

    private TileController controller;
    private final HashMap<BlockPos, TileControlRodModule> controlRods = new HashMap<>();
    private final HashMap<BlockPos, TileSupplyModule> supplyModules = new HashMap<>();
    private final HashMap<BlockPos, GenericTileInterface> interfaces = new HashMap<>();
    private final HashMap<BlockPos, TileMonitorModule> monitors = new HashMap<>();
    private final HashMap<BlockPos, TileThermometerModule> thermometers = new HashMap<>();

    public ReactorLogisticsNetwork(Collection<TileReactorLogisticsCable> varCables) {
	conductorSet.addAll(varCables);
	NetworkRegistry.register(this);
    }

    public ReactorLogisticsNetwork(Set<ReactorLogisticsNetwork> networks) {
	for (ReactorLogisticsNetwork net : networks) {
	    if (net != null) {
		conductorSet.addAll(net.conductorSet);
		net.deregister();
	    }
	}
	NetworkRegistry.register(this);
    }

    @Override
    public void refreshNewNetwork() {
	controller = null;
	interfaces.clear();
	controlRods.clear();
	supplyModules.clear();
	monitors.clear();
	thermometers.clear();
	super.refreshNewNetwork();
    }

    @Override
    public void resetReceiverStatistics() {
	super.resetReceiverStatistics();
	controller = null;
	interfaces.clear();
	controlRods.clear();
	supplyModules.clear();
	monitors.clear();
	thermometers.clear();
    }

    @Override
    public void tick() {
	super.tick();

	boolean receiversChanged = false;
	Iterator<BlockEntity> iterator = acceptorSet.iterator();

	while (iterator.hasNext()) {
	    BlockEntity tile = iterator.next();
	    Set<Direction> inputs = acceptorInputMap.get(tile);

	    if (tile == null || tile.isRemoved() || inputs == null || inputs.isEmpty()) {
		iterator.remove();
		acceptorInputMap.remove(tile);
		receiversChanged = true;
	    }
	}

	receiversChanged |= acceptorInputMap.keySet().retainAll(acceptorSet);

	if (receiversChanged) {
	    resetReceiverStatistics();
	    acceptorInputMap.forEach(
		    (receiver, inputs) -> inputs.forEach(input -> updateRecieverStatistics(receiver, input)));
	}
    }

    @Override
    public void updateRecieverStatistics(BlockEntity reciever, Direction dir) {
	if (reciever instanceof TileController controller) {
	    this.controller = controller;
	} else if (reciever instanceof GenericTileInterface reactorInterface) {
	    interfaces.put(reactorInterface.getBlockPos(), reactorInterface);
	} else if (reciever instanceof TileControlRodModule controlRod) {
	    controlRods.put(controlRod.getBlockPos(), controlRod);
	} else if (reciever instanceof TileSupplyModule supplyModule) {
	    supplyModules.put(supplyModule.getBlockPos(), supplyModule);
	} else if (reciever instanceof TileMonitorModule monitor) {
	    monitors.put(monitor.getBlockPos(), monitor);
	} else if (reciever instanceof TileThermometerModule thermometer) {
	    thermometers.put(thermometer.getBlockPos(), thermometer);
	}
    }

    @Override
    public Void emit(Void transfer, ArrayList<BlockEntity> ignored, boolean debug) {
	throw new UnsupportedOperationException("The Reactor Logistics Network does not emit, what are you doing?");
    }

    @Override
    public boolean isConductor(BlockEntity blockEntity, TileReactorLogisticsCable iReactorLogisticsCable) {
	return blockEntity instanceof TileReactorLogisticsCable;
    }

    @Override
    public ReactorLogisticsNetwork createInstanceConductor(Set<TileReactorLogisticsCable> set) {
	return new ReactorLogisticsNetwork(set);
    }

    @Nullable
    public TileController getController() {
	return controller;
    }

    @Nullable
    public TileControlRodModule getControlRod(BlockPos pos) {
	return controlRods.getOrDefault(pos, null);
    }

    @Nullable
    public TileSupplyModule getSupplyModule(BlockPos pos) {
	return supplyModules.getOrDefault(pos, null);
    }

    @Nullable
    public GenericTileInterface getInterface(BlockPos pos) {
	return interfaces.getOrDefault(pos, null);
    }

    public List<GenericTileInterface> getInterfacesForType(GenericTileInterface.InterfaceType... types) {
	List<GenericTileInterface> interfaces = new ArrayList<>();

	this.interfaces.forEach((pos, tile) -> {
	    for (GenericTileInterface.InterfaceType type : types) {
		if (tile.getInterfaceType() == type) {
		    interfaces.add(tile);
		}
	    }
	});

	return interfaces;
    }

    public boolean isControllerActive() {
	return controller != null && controller.isActive();
    }

}
