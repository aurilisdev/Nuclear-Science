package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
		    BlockEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
		    if (tile instanceof TileMSRReactorCore) {
			isMSR = true;
			this.dir = dir;
		    }
		}
	    }
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    public void writePacket(CompoundTag compound) {
	compound.putInt("insertion", insertion);
	compound.putBoolean("isMSR", isMSR);
	compound.putInt("dir", dir.ordinal());
    }

    public void readPacket(CompoundTag compound) {
	insertion = compound.getInt("insertion");
	isMSR = compound.getBoolean("isMSR");
	dir = Direction.from3DDataValue(compound.getInt("dir"));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
	compound.putInt("insertion", insertion);
	return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
	super.load(state, compound);
	insertion = compound.getInt("insertion");
    }
}
