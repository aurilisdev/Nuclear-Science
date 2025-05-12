package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import nuclearscience.api.network.reactorlogistics.ILogisticsMember;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.utilities.object.CachedTileOutput;

public abstract class TileAlarmModule extends GenericTile implements ILogisticsMember {

    public TileAlarmModule(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CachedTileOutput networkCable;

    @Override
    public boolean isValidConnection(Direction dir) {
        return false;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return true;
    }
}
