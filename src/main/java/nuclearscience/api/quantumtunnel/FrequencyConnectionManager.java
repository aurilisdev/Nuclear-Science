package nuclearscience.api.quantumtunnel;

import java.util.HashMap;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import nuclearscience.registers.NuclearScienceAttachmentTypes;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.prefab.utilities.object.TransferPack;

public class FrequencyConnectionManager {

    public static TransferPack getBufferedEnergy(TunnelFrequency frequency) {
	ServerLevel level = getOverworld();
	return level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP)
		.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedEnergy();
    }

    public static TransferPack recieveEnergy(TunnelFrequency frequency, TransferPack recieve, boolean simulate) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	TransferPack accepted = map.get(frequency).addEnergy(simulate, recieve);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return accepted;
    }

    public static TransferPack extractEnergy(TunnelFrequency frequency, TransferPack extract, boolean simulate) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	TransferPack taken = map.get(frequency).extractEnergy(simulate, extract);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return taken;
    }

    public static FluidStack getBufferedFluid(TunnelFrequency frequency) {
	ServerLevel level = getOverworld();
	return level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP)
		.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedFluid();
    }

    public static FluidStack recieveFluid(TunnelFrequency frequency, FluidStack recieve,
	    IFluidHandler.FluidAction action) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	FluidStack accepted = map.get(frequency).receiveFluid(action, recieve);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return accepted;
    }

    public static FluidStack extractFluid(TunnelFrequency frequency, FluidStack extract,
	    IFluidHandler.FluidAction action) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	FluidStack taken = map.get(frequency).extractFluid(action, extract);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return taken;
    }

    public static GasStack getBufferedGas(TunnelFrequency frequency) {
	ServerLevel level = getOverworld();
	return level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP)
		.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedGas();
    }

    public static GasStack receiveGas(TunnelFrequency frequency, GasStack recieve, GasAction action) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	GasStack accepted = map.get(frequency).receiveGas(action, recieve);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return accepted;
    }

    public static GasStack extractGas(TunnelFrequency frequency, GasStack extract, GasAction action) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	GasStack taken = map.get(frequency).extractGas(action, extract);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return taken;
    }

    public static ItemStack getBufferedItem(TunnelFrequency frequency) {
	ServerLevel level = getOverworld();
	return level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP)
		.getOrDefault(frequency, new TunnelFrequencyBuffer()).getBufferedItem();
    }

    public static ItemStack recieveItem(TunnelFrequency frequency, ItemStack recieve, boolean simulate) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	ItemStack accepted = map.get(frequency).receiveItem(simulate, recieve);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return accepted;
    }

    public static ItemStack extractItem(TunnelFrequency frequency, ItemStack extract, boolean simulate) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	if (!map.containsKey(frequency)) {
	    map.put(frequency, new TunnelFrequencyBuffer());
	}
	ItemStack taken = map.get(frequency).extractItem(simulate, extract);
	level.setData(NuclearScienceAttachmentTypes.TUNNEL_MAP, map);
	return taken;
    }

    public static TunnelFrequencyBuffer getClientBuffer(TunnelFrequency frequency) {
	ServerLevel level = getOverworld();
	HashMap<TunnelFrequency, TunnelFrequencyBuffer> map = level.getData(NuclearScienceAttachmentTypes.TUNNEL_MAP);
	return map.getOrDefault(frequency, TunnelFrequencyBuffer.EMPTY);
    }

    private static ServerLevel getOverworld() {
	return ServerLifecycleHooks.getCurrentServer().overworld();
    }
}
