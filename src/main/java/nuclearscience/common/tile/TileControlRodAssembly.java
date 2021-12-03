package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;

public class TileControlRodAssembly extends GenericTile {

    public int insertion = 0;
    public boolean isMSR = false;
    public Direction direction = Direction.DOWN;

    public TileControlRodAssembly(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_CONTROLRODASSEMBLY.get(), pos, state);
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
			direction = dir;
		    }
		}
	    }
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    public void writePacket(CompoundTag compound) {
	compound.putInt("insertion", insertion);
	compound.putBoolean("isMSR", isMSR);
	compound.putInt("dir", direction.ordinal());
    }

    public void readPacket(CompoundTag compound) {
	insertion = compound.getInt("insertion");
	isMSR = compound.getBoolean("isMSR");
	direction = Direction.from3DDataValue(compound.getInt("dir"));
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
	compound.putInt("insertion", insertion);
	super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
	super.load(compound);
	insertion = compound.getInt("insertion");
    }
}
