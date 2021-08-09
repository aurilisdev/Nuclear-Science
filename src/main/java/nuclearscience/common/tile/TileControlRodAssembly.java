package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import nuclearscience.DeferredRegisters;

public class TileControlRodAssembly extends GenericTileTicking {

    public int insertion = 0;

    public TileControlRodAssembly() {
	super(DeferredRegisters.TILE_CONTROLRODASSEMBLY.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::writePacket).customPacketReader(this::readPacket));
    }

    public void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % 20 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    public void writePacket(CompoundNBT compound) {
	write(compound);
    }

    public void readPacket(CompoundNBT compound) {
	insertion = compound.getInt("insertion");
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
