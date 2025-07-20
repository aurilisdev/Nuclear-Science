package nuclearscience.api.quantumtunnel;

import java.util.HashMap;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.server.ServerLifecycleHooks;
import nuclearscience.prefab.utils.NuclearCapabilityUtils;
import nuclearscience.registers.NuclearScienceCapabilities;
import voltaic.prefab.utilities.object.TransferPack;

public class FrequencyConnectionManager {

    public static TransferPack getBufferedEnergy(TunnelFrequency frequency) {
        ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return TransferPack.EMPTY;
        }
        return cap.getMap().getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedEnergy();
    }

    public static TransferPack recieveEnergy(TunnelFrequency frequency, TransferPack recieve, boolean simulate) {
        ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return TransferPack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        if (!map.containsKey(frequency)) {
            map.put(frequency, new TunnelFrequencyBuffer());
        }
        return map.get(frequency).addEnergy(simulate, recieve);
    }

    public static TransferPack extractEnergy(TunnelFrequency frequency, TransferPack extract, boolean simulate) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return TransferPack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        if (!map.containsKey(frequency)) {
            map.put(frequency, new TunnelFrequencyBuffer());
        }
        return map.get(frequency).extractEnergy(simulate, extract);
    }

    public static FluidStack getBufferedFluid(TunnelFrequency frequency) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return FluidStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        return map.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedFluid();
    }

    public static FluidStack recieveFluid(TunnelFrequency frequency, FluidStack recieve, FluidAction action) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return FluidStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        if (!map.containsKey(frequency)) {
            map.put(frequency, new TunnelFrequencyBuffer());
        }
        return map.get(frequency).addFluid(action, recieve);
    }

    public static FluidStack extractFluid(TunnelFrequency frequency, FluidStack extract, FluidAction action) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return FluidStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        if (!map.containsKey(frequency)) {
            map.put(frequency, new TunnelFrequencyBuffer());
        }
        return map.get(frequency).extractFluid(action, extract);
    }

    public static ItemStack getBufferedItem(TunnelFrequency frequency) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ItemStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        return map.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedItem();
    }

    public static ItemStack recieveItem(TunnelFrequency frequency, ItemStack recieve, boolean simulate) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ItemStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        if (!map.containsKey(frequency)) {
            map.put(frequency, new TunnelFrequencyBuffer());
        }
        return map.get(frequency).addItem(simulate, recieve);
    }

    public static ItemStack extractItem(TunnelFrequency frequency, ItemStack extract, boolean simulate) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ItemStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        if (!map.containsKey(frequency)) {
            map.put(frequency, new TunnelFrequencyBuffer());
        }
        return map.get(frequency).extractItem(simulate, extract);
    }

    public static TunnelFrequencyBuffer getClientBuffer(TunnelFrequency frequency) {
    	ServerLevel level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return TunnelFrequencyBuffer.EMPTY;
        }
        return cap.getMap().getOrDefault(frequency, TunnelFrequencyBuffer.EMPTY);
    }


    private static ServerLevel getOverworld() {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }
}
