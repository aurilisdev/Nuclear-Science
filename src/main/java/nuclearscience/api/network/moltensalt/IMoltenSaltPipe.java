package nuclearscience.api.network.moltensalt;

import electrodynamics.api.network.IAbstractConductor;
import electrodynamics.api.network.INetwork;
import electrodynamics.prefab.network.AbstractNetwork;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;

public interface IMoltenSaltPipe extends IAbstractConductor {
    INetwork getNetwork();

    INetwork getNetwork(boolean createIfNull);

    void refreshNetwork();

    void refreshNetworkIfChange();

    @Override
    void removeFromNetwork();

    void destroyViolently();

    SubtypeMoltenSaltPipe getPipeType();

    @Override
    void setNetwork(AbstractNetwork<?, ?, ?, ?> aValueNetwork);

    @Override
    default Object getConductorType() {
	return getPipeType();
    }

    @Override
    default double getMaxTransfer() {
	return getPipeType().maxTransfer;
    }

}