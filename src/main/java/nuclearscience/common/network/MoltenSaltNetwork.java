package nuclearscience.common.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import electrodynamics.common.network.NetworkRegistry;
import electrodynamics.prefab.network.AbstractNetwork;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.tile.TileHeatExchanger;

public class MoltenSaltNetwork extends AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double> {
	public MoltenSaltNetwork() {
		this(new HashSet<IMoltenSaltPipe>());
	}

	public MoltenSaltNetwork(Collection<? extends IMoltenSaltPipe> varCables) {
		conductorSet.addAll(varCables);
		NetworkRegistry.register(this);
	}

	public MoltenSaltNetwork(Set<AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double>> networks) {
		for (AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double> net : networks) {
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
	public Double emit(Double transfer, ArrayList<BlockEntity> ignored, boolean debug) {
		if (transfer <= 0) {
			return 0.0;
		}
		Set<BlockEntity> availableAcceptors = Sets.newHashSet(acceptorSet);

		double heat = 0.0;

		availableAcceptors.removeAll(ignored);

		if (availableAcceptors.isEmpty()) {
			return 0.0;
		}

		double perReceiver = transfer / availableAcceptors.size();
		for (BlockEntity receiver : availableAcceptors) {
			if (acceptorInputMap.containsKey(receiver)) {
				Double rec = ((TileHeatExchanger) receiver).receiveHeat(perReceiver - getSize() * 5);
				heat += rec;
				transmittedThisTick += rec;
				checkForOverload((int) transmittedThisTick);
			}
		}

		return heat;
	}

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
			for (IMoltenSaltPipe conductor : conductorTypeMap.get(index)) {
				conductor.destroyViolently();
			}
		}
		return true;
	}

	@Override
	public boolean isConductor(BlockEntity tile, IMoltenSaltPipe requesterCable) {
		return tile instanceof IMoltenSaltPipe;
	}

	@Override
	public boolean isAcceptor(BlockEntity acceptor, Direction orientation) {
		return acceptor instanceof TileHeatExchanger || isConductorClass(acceptor);
	}

	@Override
	public boolean isConductorClass(BlockEntity tile) {
		return tile instanceof IMoltenSaltPipe;
	}

	@Override
	public AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double> createInstance() {
		return new MoltenSaltNetwork();
	}

	@Override
	public AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double> createInstanceConductor(Set<IMoltenSaltPipe> conductors) {
		return new MoltenSaltNetwork(conductors);
	}

	@Override
	public AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double> createInstance(Set<AbstractNetwork<IMoltenSaltPipe, SubtypeMoltenSaltPipe, BlockEntity, Double>> networks) {
		return new MoltenSaltNetwork(networks);

	}

	@Override
	public SubtypeMoltenSaltPipe[] getConductorTypes() {
		return SubtypeMoltenSaltPipe.values();
	}

	@Override
	public boolean canConnect(BlockEntity acceptor, Direction orientation) {
		return acceptor instanceof TileHeatExchanger && orientation == Direction.UP || isConductorClass(acceptor);
	}

}
