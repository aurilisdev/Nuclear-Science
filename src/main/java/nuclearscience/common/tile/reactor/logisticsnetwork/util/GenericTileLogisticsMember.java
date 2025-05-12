package nuclearscience.common.tile.reactor.logisticsnetwork.util;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.object.CachedTileOutput;

public abstract class GenericTileLogisticsMember extends GenericTile implements ILogisticsMember {

    public CachedTileOutput networkCable;

    public GenericTileLogisticsMember(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
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
