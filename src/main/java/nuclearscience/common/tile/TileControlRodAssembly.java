package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileControlRodAssembly extends GenericTile {

	public Direction direction = Direction.DOWN;
	public Property<Integer> insertion = property(new Property<Integer>(PropertyType.Integer, "insertion")).set(0).save();
	public Property<Boolean> isMSR = property(new Property<Boolean>(PropertyType.Boolean, "isMSR")).set(false);

	public TileControlRodAssembly(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_CONTROLRODASSEMBLY.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
	}

	public void tickServer(ComponentTickable tickable) {
		if (tickable.getTicks() % 20 == 0) {
			isMSR.set(false, true);
			for (Direction dir : Direction.values()) {
				if (dir != Direction.UP && dir != Direction.DOWN) {
					BlockEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
					if (tile instanceof TileMSRReactorCore) {
						isMSR.set(true);
						direction = dir;
					}
				}
			}
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		}
	}

	public void writePacket(CompoundTag compound) {
		compound.putInt("dir", direction.ordinal());
	}

	public void readPacket(CompoundTag compound) {
		direction = Direction.from3DDataValue(compound.getInt("dir"));
	}

}
