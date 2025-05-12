package nuclearscience.api.quantumtunnel;

import java.util.HashMap;

import net.minecraft.nbt.CompoundNBT;

public interface ICapabilityTunnelMap {
	
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> getMap();
	
	CompoundNBT toTag();
	
	void fromTag(CompoundNBT tag);

}
