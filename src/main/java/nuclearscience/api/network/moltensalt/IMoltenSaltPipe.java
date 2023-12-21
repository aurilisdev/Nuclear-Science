package nuclearscience.api.network.moltensalt;

import electrodynamics.api.network.cable.IRefreshableCable;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;

public interface IMoltenSaltPipe extends IRefreshableCable {

	SubtypeMoltenSaltPipe getPipeType();

	@Override
	default Object getConductorType() {
		return getPipeType();
	}

	@Override
	default double getMaxTransfer() {
		return getPipeType().maxTransfer;
	}

}