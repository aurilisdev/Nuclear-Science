package nuclearscience.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.tileentity.TileEntity;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.reactor.moltensalt.TileHeatExchanger;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltPipe;
import voltaic.common.network.NetworkRegistry;
import voltaic.prefab.network.AbstractNetwork;

public class MoltenSaltNetwork extends AbstractNetwork<TileMoltenSaltPipe, SubtypeMoltenSaltPipe, Double, MoltenSaltNetwork> {

	public MoltenSaltNetwork(Collection<TileMoltenSaltPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public MoltenSaltNetwork(Set<MoltenSaltNetwork> networks) {
		for (MoltenSaltNetwork net : networks) {
			conductorSet.addAll(net.conductorSet);
			net.deregister();
		}
		NetworkRegistry.register(this);
	}

	@Override
	public Double emit(Double transfer, ArrayList<TileEntity> ignored, boolean debug) {
		if (transfer <= 0) {
			return 0.0;
		}
		Set<TileEntity> availableAcceptors = Sets.newHashSet(acceptorSet);

		double heat = 0.0;

		availableAcceptors.removeAll(ignored);

		if (availableAcceptors.isEmpty()) {
			return 0.0;
		}

		double perReceiver = transfer / availableAcceptors.size();
		for (TileEntity receiver : availableAcceptors) {
			if (acceptorInputMap.containsKey(receiver) && receiver instanceof TileHeatExchanger) {
				Double rec = ((TileHeatExchanger) receiver).receiveHeat(perReceiver - getSize() * 5);
				heat += rec;
				transmittedThisTick += rec;
				//checkForOverload((int) transmittedThisTick);
			}
		}

		return heat;
	}

	/*
	private boolean checkForOverload(int attemptSend) {
		if (attemptSend <= networkMaxTransfer) {
			return false;
		}

		HashSet<SubtypeMoltenSaltPipe> checkList = new HashSet<>();
		for (SubtypeMoltenSaltPipe type : SubtypeMoltenSaltPipe.values()) {
			if (type.maxTransfer <= attemptSend) {
				checkList.add(type);
			}
		}
		for (SubtypeMoltenSaltPipe index : checkList) {
			for (TileMoltenSaltPipe conductor : conductorTypeMap.get(index)) {
				conductor.destroyViolently();
			}
		}
		return true;
	}

	 */

	@Override
	public boolean isConductor(TileEntity tile, TileMoltenSaltPipe requesterCable) {
		return tile instanceof TileMoltenSaltPipe;
	}

	@Override
	public MoltenSaltNetwork createInstanceConductor(Set<TileMoltenSaltPipe> conductors) {
		return new MoltenSaltNetwork(conductors);
	}

}
