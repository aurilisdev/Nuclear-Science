package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public interface ICapabilityChannelMap {
	
	HashMap<UUID, HashSet<TunnelFrequency>> getMap();

}
