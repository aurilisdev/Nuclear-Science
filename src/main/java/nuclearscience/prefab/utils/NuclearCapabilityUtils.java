package nuclearscience.prefab.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.nbt.CompoundNBT;
import nuclearscience.api.capability.ICapabilityAntimatterItem;
import nuclearscience.api.quantumtunnel.ICapabilityChannelMap;
import nuclearscience.api.quantumtunnel.ICapabilityTunnelMap;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;

public class NuclearCapabilityUtils {
	
	public static final ICapabilityTunnelMap EMPTY_TUNNELMAP = new ICapabilityTunnelMap() {
		
		@Override
		public HashMap<TunnelFrequency, TunnelFrequencyBuffer> getMap() {
			return new HashMap<>();
		}

		@Override
		public CompoundNBT toTag() {
			return new CompoundNBT();
		}

		@Override
		public void fromTag(CompoundNBT tag) {
			
		}
	};
	
	public static final ICapabilityChannelMap EMPTY_CHANNELMAP = new ICapabilityChannelMap() {
		
		@Override
		public HashMap<UUID, HashSet<TunnelFrequency>> getMap() {
			return new HashMap<>();
		}

		@Override
		public CompoundNBT toTag() {
			return new CompoundNBT();
		}

		@Override
		public void fromTag(CompoundNBT tag) {
			
		}
	};
	
	public static final ICapabilityAntimatterItem EMPTY_ANTIMATTERITEM = new ICapabilityAntimatterItem() {
		
		@Override
		public void incrementTime() {
			
		}
		
		@Override
		public int getTime() {
			return 0;
		}

		@Override
		public CompoundNBT toTag() {
			return new CompoundNBT();
		}

		@Override
		public void fromTag(CompoundNBT tag) {
			
		}
	};

}
