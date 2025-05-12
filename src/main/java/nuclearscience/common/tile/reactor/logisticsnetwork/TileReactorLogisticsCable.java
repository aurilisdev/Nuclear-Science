package nuclearscience.common.tile.reactor.logisticsnetwork;

import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import nuclearscience.common.block.connect.BlockReactorLogisticsCable;
import nuclearscience.common.block.subtype.SubtypeReactorLogisticsCable;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;

public class TileReactorLogisticsCable extends GenericRefreshingConnectTile<SubtypeReactorLogisticsCable, TileReactorLogisticsCable, ReactorLogisticsNetwork> {

    public SubtypeReactorLogisticsCable cable;

    public TileReactorLogisticsCable() {
        super(NuclearScienceTiles.TILE_REACTORLOGISTICSCABLE.get());
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
	public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("ord", getCableType().ordinal());
        return super.save(compound);
    }

    @Override
	public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
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
