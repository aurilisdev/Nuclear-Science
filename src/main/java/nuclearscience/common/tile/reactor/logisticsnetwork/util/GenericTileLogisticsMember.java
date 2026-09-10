package nuclearscience.common.tile.reactor.logisticsnetwork.util;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;

public abstract class GenericTileLogisticsMember extends GenericTile implements ILogisticsMember {

    public GenericTileLogisticsMember(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
	super(tileEntityTypeIn, worldPos, blockState);
    }

    public void tickServer(Level level, ComponentTickable tickable) {
    }

    public Optional<BlockEntity> getNetworkCable() {
	Level level = getLevel();
	if (level == null) {
	    return Optional.empty();
	}
	BlockPos cableLocation = getBlockPos().relative(getCableLocation());

	return Optional.ofNullable(level.getBlockEntity(cableLocation)).filter(cable -> !cable.isRemoved());
    }

    public <T extends BlockEntity> Optional<T> getNetworkCable(Class<T> cableClass) {
	return getNetworkCable().filter(cableClass::isInstance).map(cableClass::cast);
    }

    public abstract Direction getCableLocation();

    @Override
    public boolean isValidConnection(Direction dir) {
	return dir == getCableLocation();
    }
}