package nuclearscience.common.tile.reactor.logisticsnetwork.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.object.CachedTileOutput;

public abstract class GenericTileLogisticsMember extends GenericTile implements ILogisticsMember {

    public CachedTileOutput networkCable;

    public GenericTileLogisticsMember(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
    }

    public void tickServer(ComponentTickable tickable) {

	if (networkCable == null) {
	    networkCable = new CachedTileOutput(getLevel(), getBlockPos().relative(getCableLocation()));
	}

	if (tickable.getTicks() % 20 == 0) {
	    if (!networkCable.valid()) {
		networkCable.update(getBlockPos().relative(getCableLocation()));
	    }

	}

    }

    public abstract Direction getCableLocation();

    @Override
    public boolean isValidConnection(Direction dir) {
	return dir == getCableLocation();
    }
}
