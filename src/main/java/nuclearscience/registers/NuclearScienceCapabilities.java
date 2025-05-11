package nuclearscience.registers;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import nuclearscience.api.capability.CapabilityAntimatterItem;
import nuclearscience.api.capability.ICapabilityAntimatterItem;
import nuclearscience.api.quantumtunnel.CapabilityChannelMap;
import nuclearscience.api.quantumtunnel.CapabilityTunnelMap;
import nuclearscience.api.quantumtunnel.ICapabilityChannelMap;
import nuclearscience.api.quantumtunnel.ICapabilityTunnelMap;

public class NuclearScienceCapabilities {

	@CapabilityInject(ICapabilityTunnelMap.class)
	public static Capability<ICapabilityTunnelMap> CAPABILITY_TUNNELMAP;
	@CapabilityInject(ICapabilityChannelMap.class)
	public static Capability<ICapabilityChannelMap> CAPABILITY_CHANNELMAP;
	@CapabilityInject(ICapabilityAntimatterItem.class)
	public static Capability<ICapabilityAntimatterItem> CAPABILITY_ANTIMATTERITEM;
	
	public static void register() {
		
		CapabilityManager.INSTANCE.register(ICapabilityTunnelMap.class, new IStorage<ICapabilityTunnelMap>() {

			@Override
			public INBT writeNBT(Capability<ICapabilityTunnelMap> capability, ICapabilityTunnelMap instance, Direction side) {
				return instance.toTag();
			}

			@Override
			public void readNBT(Capability<ICapabilityTunnelMap> capability, ICapabilityTunnelMap instance, Direction side, INBT nbt) {
				instance.fromTag((CompoundNBT) nbt);
			}
			
		}, () -> new CapabilityTunnelMap());
		
		CapabilityManager.INSTANCE.register(ICapabilityChannelMap.class, new IStorage<ICapabilityChannelMap>() {

			@Override
			public INBT writeNBT(Capability<ICapabilityChannelMap> capability, ICapabilityChannelMap instance, Direction side) {
				return instance.toTag();
			}

			@Override
			public void readNBT(Capability<ICapabilityChannelMap> capability, ICapabilityChannelMap instance, Direction side, INBT nbt) {
				instance.fromTag((CompoundNBT) nbt);
			}
			
		}, () -> new CapabilityChannelMap());
		
		
		CapabilityManager.INSTANCE.register(ICapabilityAntimatterItem.class, new IStorage<ICapabilityAntimatterItem>() {

			@Override
			public INBT writeNBT(Capability<ICapabilityAntimatterItem> capability, ICapabilityAntimatterItem instance, Direction side) {
				return instance.toTag();
			}

			@Override
			public void readNBT(Capability<ICapabilityAntimatterItem> capability, ICapabilityAntimatterItem instance, Direction side, INBT nbt) {
				instance.fromTag((CompoundNBT) nbt);
			}
			
		}, () -> new CapabilityAntimatterItem());
	}

}
