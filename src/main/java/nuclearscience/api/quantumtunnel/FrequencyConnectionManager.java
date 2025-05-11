package nuclearscience.api.quantumtunnel;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import nuclearscience.prefab.utils.NuclearCapabilityUtils;
import nuclearscience.registers.NuclearScienceCapabilities;
import voltaic.prefab.utilities.object.TransferPack;

public class FrequencyConnectionManager {
	
	private static final HashMap<TunnelFrequency, TunnelFrequencyBuffer> CLIENT_BUFFER = new HashMap<>();

    public static TransferPack getBufferedEnergy(TunnelFrequency frequency) {
        ServerWorld level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return TransferPack.EMPTY;
        }
        return cap.getMap().getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedEnergy();
    }

    public static TransferPack recieveEnergy(TunnelFrequency frequency, TransferPack recieve, boolean simulate) {
        ServerWorld level = getOverworld();
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
    	ServerWorld level = getOverworld();
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
    	ServerWorld level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return FluidStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        return map.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedFluid();
    }

    public static FluidStack recieveFluid(TunnelFrequency frequency, FluidStack recieve, FluidAction action) {
    	ServerWorld level = getOverworld();
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
    	ServerWorld level = getOverworld();
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
    	ServerWorld level = getOverworld();
        ICapabilityTunnelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ItemStack.EMPTY;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = cap.getMap();
        return map.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedItem();
    }

    public static ItemStack recieveItem(TunnelFrequency frequency, ItemStack recieve, boolean simulate) {
    	ServerWorld level = getOverworld();
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
    	ServerWorld level = getOverworld();
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
        return CLIENT_BUFFER.getOrDefault(frequency, TunnelFrequencyBuffer.EMPTY);
    }
    
    public static void setClientBuffer(HashMap<TunnelFrequency, TunnelFrequencyBuffer> map) {
    	CLIENT_BUFFER.clear();
    	CLIENT_BUFFER.putAll(map);
    }


    private static ServerWorld getOverworld() {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }
}
