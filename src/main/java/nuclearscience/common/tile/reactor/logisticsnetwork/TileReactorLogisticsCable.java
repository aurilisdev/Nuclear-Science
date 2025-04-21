package nuclearscience.common.tile.reactor.logisticsnetwork;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.block.connect.BlockReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;

public class TileReactorLogisticsCable extends GenericRefreshingConnectTile<SubtypeReactorLogisticsCable, TileReactorLogisticsCable, ReactorLogisticsNetwork> {

    public SubtypeReactorLogisticsCable cable;

    public TileReactorLogisticsCable(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_REACTORLOGISTICSCABLE.get(), pos, state);
    }


    @Override
    public void destroyViolently() {
    }

    @Override
    public SubtypeReactorLogisticsCable getCableType() {
        if (cable == null) {
            cable = ((BlockReactorLogisticsCable) getBlockState().getBlock()).cable;
        }
        return cable;
    }

    @Override
    public double getMaxTransfer() {
        return 0;
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("ord", getCableType().ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        cable = SubtypeReactorLogisticsCable.values()[compound.getInt("ord")];
    }

    @Override
    public ReactorLogisticsNetwork createInstanceConductor(Set<TileReactorLogisticsCable> set) {
        return new ReactorLogisticsNetwork(set);
    }

    @Override
    public ReactorLogisticsNetwork createInstance(Set<ReactorLogisticsNetwork> set) {
        return new ReactorLogisticsNetwork(set);
    }
}
