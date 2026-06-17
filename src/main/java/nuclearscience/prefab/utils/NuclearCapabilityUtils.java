package nuclearscience.prefab.utils;

import java.util.HashMap;

import nuclearscience.api.capability.ICapabilityAntimatterItem;
import nuclearscience.api.quantumtunnel.ICapabilityChannelMap;
import nuclearscience.api.quantumtunnel.ICapabilityTunnelMap;

public class NuclearCapabilityUtils {

    public static final ICapabilityTunnelMap EMPTY_TUNNELMAP = () -> new HashMap<>();

    public static final ICapabilityChannelMap EMPTY_CHANNELMAP = () -> new HashMap<>();

    public static final ICapabilityAntimatterItem EMPTY_ANTIMATTERITEM = new ICapabilityAntimatterItem() {

	@Override
	public void incrementTime() {

	}

	@Override
	public int getTime() {
	    return 0;
	}
    };

}
