package nuclearscience.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.TileHeatExchanger;

public class MoltenSaltNetwork extends AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double> {
    public MoltenSaltNetwork() {
	this(new HashSet<IMoltenSaltPipe>());
    }

    public MoltenSaltNetwork(Collection<? extends IMoltenSaltPipe> varCables) {
	conductorSet.addAll(varCables);
	NetworkRegistry.register(this);
    }

    public MoltenSaltNetwork(Set<AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double>> networks) {
	for (AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double> net : networks) {
	    if (net != null) {
		conductorSet.addAll(net.conductorSet);
		net.deregister();
	    }
	}
	refresh();
	NetworkRegistry.register(this);
    }

    public MoltenSaltNetwork(Set<MoltenSaltNetwork> networks, boolean special) {
	for (MoltenSaltNetwork net : networks) {
	    if (net != null) {
		conductorSet.addAll(net.conductorSet);
		net.deregister();
	    }
	}
	refresh();
	NetworkRegistry.register(this);
    }

    @Override
    public Double emit(Double transfer, ArrayList<TileEntity> ignored, boolean debug) {
	if (transfer > 0) {
	    Set<TileEntity> availableAcceptors = getFluidAcceptors(transfer);
	    Double heat = 0.0;
	    availableAcceptors.removeAll(ignored);
	    if (!availableAcceptors.isEmpty()) {
		Double perReceiver = transfer / availableAcceptors.size();
		for (TileEntity receiver : availableAcceptors) {
		    if (acceptorInputMap.containsKey(receiver)) {
			Double rec = ((TileHeatExchanger) receiver).receiveHeat(perReceiver - getSize() * 5);
			heat += rec;
			transmittedThisTick += rec;
			checkForOverload((int) transmittedThisTick);
		    }
		}
	    }
	    return heat;
	}
	return 0.0;
    }

    public Set<TileEntity> getFluidAcceptors(Double compare) {
	Set<TileEntity> toReturn = new HashSet<>();
	toReturn.addAll(acceptorSet);
	return toReturn;
    }

    private boolean checkForOverload(int attemptSend) {
	if (attemptSend >= networkMaxTransfer) {
	    HashSet<SubtypeMoltenSaltPipe> checkList = new HashSet<>();
	    for (SubtypeMoltenSaltPipe type : SubtypeMoltenSaltPipe.values()) {
		if (type.maxTransfer <= attemptSend) {
		    checkList.add(type);
		}
	    }
	    for (SubtypeMoltenSaltPipe index : checkList) {
		for (IMoltenSaltPipe conductor : conductorTypeMap.get(index)) {
		    conductor.destroyViolently();
		}
	    }
	    return true;
	}
	return false;
    }

    @Override
    public boolean isConductor(TileEntity tile) {
	return tile instanceof IMoltenSaltPipe;
    }

    @Override
    public boolean isAcceptor(TileEntity acceptor, Direction orientation) {
	return acceptor instanceof TileHeatExchanger || isConductor(acceptor);
    }

    @Override
    public AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double> createInstance() {
	return new MoltenSaltNetwork();
    }

    @Override
    public AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double> createInstanceConductor(Set<IMoltenSaltPipe> conductors) {
	return new MoltenSaltNetwork(conductors);
    }

    @Override
    public AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double> createInstance(
	    Set<AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, TileEntity, Double>> networks) {
	return new MoltenSaltNetwork(networks);

    }

    @Override
    public SubtypeMoltenSaltPipe[] getConductorTypes() {
	return SubtypeMoltenSaltPipe.values();
    }

    @Override
    public boolean canConnect(TileEntity acceptor, Direction orientation) {
	return (acceptor instanceof TileHeatExchanger && orientation.getOpposite() == Direction.DOWN) || isConductor(acceptor);
    }
}
