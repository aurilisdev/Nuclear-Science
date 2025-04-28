package nuclearscience.api.quantumtunnel;

import java.util.HashMap;

public interface ICapabilityTunnelMap {
	
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> getMap();

}
