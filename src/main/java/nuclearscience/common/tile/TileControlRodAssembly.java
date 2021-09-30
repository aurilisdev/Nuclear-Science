package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;

public class TileControlRodAssembly extends GenericTileTicking {

    public int insertion = 0;
    public boolean isMSR = false;
    public Direction dir = Direction.DOWN;

    public TileControlRodAssembly() {
	super(DeferredRegisters.TILE_CONTROLRODASSEMBLY.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::writePacket).customPacketReader(this::readPacket));
    }

    public void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % 20 == 0) {
	    isMSR = false;
	    for (Direction dir : Direction.values()) {
		if (dir != Direction.UP && dir != Direction.DOWN) {
		    TileEntity tile = world.getTileEntity(getPos().offset(dir));
		    if (tile instanceof TileMSRReactorCore) {
			isMSR = true;
			this.dir = dir;
		    }
		}
	    }
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    public void writePacket(CompoundNBT compound) {
	compound.putInt("insertion", insertion);
	compound.putBoolean("isMSR", isMSR);
	compound.putInt("dir", dir.ordinal());
    }

    public void readPacket(CompoundNBT compound) {
	insertion = compound.getInt("insertion");
	isMSR = compound.getBoolean("isMSR");
	dir = Direction.byIndex(compound.getInt("dir"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putInt("insertion", insertion);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	insertion = compound.getInt("insertion");
    }
}
