package nuclearscience.common.tile.generic;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Sets;

import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.common.tile.TileHeatExchanger;

public abstract class GenericTileMoltenSaltPipe extends GenericTile implements IMoltenSaltPipe {

    public MoltenSaltNetwork moltenSaltNetwork;

    @Override
    public AbstractNetwork<?, ?, ?, ?> getAbstractNetwork() {
	return moltenSaltNetwork;
    }

    protected GenericTileMoltenSaltPipe(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
	super(tileEntityTypeIn, pos, state);
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
    }

    private HashSet<IMoltenSaltPipe> getConnectedConductors() {
	HashSet<IMoltenSaltPipe> set = new HashSet<>();
	for (Direction dir : Direction.values()) {
	    BlockEntity facing = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
	    if (facing instanceof IMoltenSaltPipe) {
		set.add((IMoltenSaltPipe) facing);
	    }
	}
	return set;
    }

    @Override
    public MoltenSaltNetwork getNetwork() {
	return getNetwork(true);
    }

    @Override
    public MoltenSaltNetwork getNetwork(boolean createIfNull) {
	if (moltenSaltNetwork == null && createIfNull) {
	    HashSet<IMoltenSaltPipe> adjacentCables = getConnectedConductors();
	    HashSet<MoltenSaltNetwork> connectedNets = new HashSet<>();
	    for (IMoltenSaltPipe wire : adjacentCables) {
		if (wire.getNetwork(false) != null && wire.getNetwork()instanceof MoltenSaltNetwork net) {
		    connectedNets.add(net);
		}
	    }
	    if (connectedNets.isEmpty()) {
		moltenSaltNetwork = new MoltenSaltNetwork(Sets.newHashSet(this));
	    } else {
		if (connectedNets.size() == 1) {
		    moltenSaltNetwork = (MoltenSaltNetwork) connectedNets.toArray()[0];
		} else {
		    moltenSaltNetwork = new MoltenSaltNetwork(connectedNets, false);
		}
		moltenSaltNetwork.conductorSet.add(this);
	    }
	}
	return moltenSaltNetwork;
    }

    @Override
    public void setNetwork(AbstractNetwork<?, ?, ?, ?> network) {
	if (moltenSaltNetwork != network && network instanceof MoltenSaltNetwork net) {
	    removeFromNetwork();
	    moltenSaltNetwork = net;
	}
    }

    @Override
    public void refreshNetwork() {
	if (!level.isClientSide) {
	    updateAdjacent();
	    ArrayList<MoltenSaltNetwork> foundNetworks = new ArrayList<>();
	    for (Direction dir : Direction.values()) {
		BlockEntity facing = level.getBlockEntity(new BlockPos(worldPosition).relative(dir));
		if (facing instanceof IMoltenSaltPipe pipe && pipe.getNetwork()instanceof MoltenSaltNetwork net) {
		    foundNetworks.add(net);
		}
	    }
	    if (!foundNetworks.isEmpty()) {
		foundNetworks.get(0).conductorSet.add(this);
		moltenSaltNetwork = foundNetworks.get(0);
		if (foundNetworks.size() > 1) {
		    foundNetworks.remove(0);
		    for (MoltenSaltNetwork network : foundNetworks) {
			getNetwork().merge(network);
		    }
		}
	    }
	    getNetwork().refresh();
	}
    }

    @Override
    public void removeFromNetwork() {
	if (moltenSaltNetwork != null) {
	    moltenSaltNetwork.removeFromNetwork(this);
	}
    }

    private boolean[] connections = new boolean[6];
    private BlockEntity[] tileConnections = new BlockEntity[6];

    public boolean updateAdjacent() {
	boolean flag = false;
	for (Direction dir : Direction.values()) {
	    BlockEntity tile = level.getBlockEntity(worldPosition.relative(dir));
	    boolean is = tile instanceof IMoltenSaltPipe || tile instanceof TileHeatExchanger;
	    if (connections[dir.ordinal()] != is) {
		connections[dir.ordinal()] = is;
		tileConnections[dir.ordinal()] = tile;
		flag = true;
	    }
	}
	return flag;
    }

    @Override
    public BlockEntity[] getAdjacentConnections() {
	return tileConnections;
    }

    @Override
    public void refreshNetworkIfChange() {
	if (updateAdjacent()) {
	    refreshNetwork();
	}
    }

    @Override
    public void destroyViolently() {
    }

    @Override
    public void setRemoved() {
	if (!level.isClientSide && moltenSaltNetwork != null) {
	    getNetwork().split(this);
	}
	super.setRemoved();
    }

    @Override
    public void onChunkUnloaded() {
	if (!level.isClientSide && moltenSaltNetwork != null) {
	    getNetwork().split(this);
	}
    }

    @Override
    public void onLoad() {
	super.onLoad();
	Scheduler.schedule(1, this::refreshNetwork);
    }

    protected abstract void writeCustomPacket(CompoundTag nbt);

    protected abstract void readCustomPacket(CompoundTag nbt);

}
