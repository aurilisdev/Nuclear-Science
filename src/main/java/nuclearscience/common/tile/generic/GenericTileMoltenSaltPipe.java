package nuclearscience.common.tile.generic;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Sets;

import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.common.network.MoltenSaltNetwork;
import nuclearscience.common.tile.TileHeatExchanger;

public abstract class GenericTileMoltenSaltPipe extends GenericTile implements IMoltenSaltPipe {

    public MoltenSaltNetwork moltenSaltNetwork;

    @Override
    public AbstractNetwork<?, ?, ?, ?> getAbstractNetwork() {
	return moltenSaltNetwork;
    }

    protected GenericTileMoltenSaltPipe(TileEntityType<?> tileEntityTypeIn) {
	super(tileEntityTypeIn);
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
    }

    private HashSet<IMoltenSaltPipe> getConnectedConductors() {
	HashSet<IMoltenSaltPipe> set = new HashSet<>();
	for (Direction dir : Direction.values()) {
	    TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
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
		if (wire.getNetwork(false) != null && wire.getNetwork() instanceof MoltenSaltNetwork) {
		    connectedNets.add((MoltenSaltNetwork) wire.getNetwork());
		}
	    }
	    if (connectedNets.isEmpty()) {
		moltenSaltNetwork = new MoltenSaltNetwork(Sets.newHashSet(this));
	    } else if (connectedNets.size() == 1) {
		moltenSaltNetwork = (MoltenSaltNetwork) connectedNets.toArray()[0];
		moltenSaltNetwork.conductorSet.add(this);
	    } else {
		moltenSaltNetwork = new MoltenSaltNetwork(connectedNets, false);
		moltenSaltNetwork.conductorSet.add(this);
	    }
	}
	return moltenSaltNetwork;
    }

    @Override
    public void setNetwork(AbstractNetwork<?, ?, ?, ?> network) {
	if (moltenSaltNetwork != network && network instanceof MoltenSaltNetwork) {
	    removeFromNetwork();
	    moltenSaltNetwork = (MoltenSaltNetwork) network;
	}
    }

    @Override
    public void refreshNetwork() {
	if (!world.isRemote) {
	    updateAdjacent();
	    ArrayList<MoltenSaltNetwork> foundNetworks = new ArrayList<>();
	    for (Direction dir : Direction.values()) {
		TileEntity facing = world.getTileEntity(new BlockPos(pos).offset(dir));
		if (facing instanceof IMoltenSaltPipe && ((IMoltenSaltPipe) facing).getNetwork() instanceof MoltenSaltNetwork) {
		    foundNetworks.add((MoltenSaltNetwork) ((IMoltenSaltPipe) facing).getNetwork());
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
    private TileEntity[] tileConnections = new TileEntity[6];

    public boolean updateAdjacent() {
	boolean flag = false;
	for (Direction dir : Direction.values()) {
	    TileEntity tile = world.getTileEntity(pos.offset(dir));
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
    public TileEntity[] getAdjacentConnections() {
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
    public void remove() {
	if (!world.isRemote && moltenSaltNetwork != null) {
	    getNetwork().split(this);
	}
	super.remove();
    }

    @Override
    public void onChunkUnloaded() {
	if (!world.isRemote && moltenSaltNetwork != null) {
	    getNetwork().split(this);
	}
    }

    @Override
    public void onLoad() {
	super.onLoad();
	Scheduler.schedule(1, this::refreshNetwork);
    }

    protected abstract void writeCustomPacket(CompoundNBT nbt);

    protected abstract void readCustomPacket(CompoundNBT nbt);

}