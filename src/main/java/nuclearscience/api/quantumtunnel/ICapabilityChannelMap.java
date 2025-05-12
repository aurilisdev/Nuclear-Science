package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.nbt.CompoundNBT;

public interface ICapabilityChannelMap {
	
	HashMap<UUID, HashSet<TunnelFrequency>> getMap();
	
	CompoundNBT toTag();
	
	void fromTag(CompoundNBT tag);

}
