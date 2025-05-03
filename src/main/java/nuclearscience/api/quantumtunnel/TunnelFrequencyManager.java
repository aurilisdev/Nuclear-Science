package nuclearscience.api.quantumtunnel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.server.ServerLifecycleHooks;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.prefab.utils.NuclearCapabilityUtils;
import nuclearscience.registers.NuclearScienceCapabilities;

public class TunnelFrequencyManager {

    public static void addPlayerFrequency(UUID player, TunnelFrequency frequency) {
        ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        if(!map.containsKey(player)) {
            map.put(player, new HashSet<>());
        }
        map.get(player).add(frequency);
    }

    public static void addPublicFrequency(TunnelFrequency frequency) {
        addPlayerFrequency(TunnelFrequency.PUBLIC_ID, frequency);
    }

    public static void removePlayerFrequency(UUID player, UUID requester, TunnelFrequency frequency) {
        if(!frequency.getCreatorId().equals(requester)) {
            return;
        }
        ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        if(!map.containsKey(player)) {
            return;
        }
        map.get(player).remove(frequency);
    }

    public static void removePublicFrequency(UUID requester, TunnelFrequency frequency) {
        removePlayerFrequency(TunnelFrequency.PUBLIC_ID, requester, frequency);
    }

    public static boolean isValidTunnelID(UUID proposedFrequencyID) {
    	ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return false;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        for(Map.Entry<UUID, HashSet<TunnelFrequency>> entry : map.entrySet()) {
            if(entry.getValue().size() > NuclearConstants.QUANTUM_TUNNEL_FREQUENCY_CAP_PER_PLAYER) {
                return false;
            }
            for(TunnelFrequency id : entry.getValue()) {
                if(id.getId().equals(proposedFrequencyID)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean doesFrequencyExist(TunnelFrequency tunnelFrequency) {
    	ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return false;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        for(Map.Entry<UUID, HashSet<TunnelFrequency>> entry : map.entrySet()) {
            for(TunnelFrequency id : entry.getValue()) {
                if(id.equals(tunnelFrequency)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void updatePlayerFrequencyName(UUID player, UUID requester, TunnelFrequency frequency) {
        if(!frequency.getCreatorId().equals(requester)) {
            return;
        }
        ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        if(!map.containsKey(player)) {
            map.put(player, new HashSet<>());
        }
        map.get(player).remove(frequency);
        map.get(player).add(frequency);

        ICapabilityTunnelMap tunCap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(tunCap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> tunMap = tunCap.getMap();
        TunnelFrequencyBuffer buffer = tunMap.getOrDefault(frequency, new TunnelFrequencyBuffer());
        tunMap.remove(frequency);
        tunMap.put(frequency, buffer);
    }

    public static void updatePublicFrequencyName(UUID requester, TunnelFrequency frequency) {
        updatePlayerFrequencyName(TunnelFrequency.PUBLIC_ID, requester, frequency);
    }

    public static HashMap<UUID, HashSet<TunnelFrequency>> getFrequenciesForPlayerClient(UUID playerID) {
        HashMap<UUID, HashSet<TunnelFrequency>> values = new HashMap<>();
        ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return new HashMap<>();
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();

        values.put(TunnelFrequency.PUBLIC_ID, map.getOrDefault(TunnelFrequency.PUBLIC_ID, new HashSet<>()));
        values.put(playerID, map.getOrDefault(playerID, new HashSet<>()));

        return values;


    }

    public static void wipeAllFrequencies() {

    	ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        map.clear();

        ICapabilityTunnelMap tunCap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(tunCap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> tunMap = tunCap.getMap();
        tunMap.clear();

    }

    public static void wipePublicFrequencies() {

    	ServerLevel level = getOverworld();
        ICapabilityChannelMap cap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_CHANNELMAP).orElse(NuclearCapabilityUtils.EMPTY_CHANNELMAP);
        if(cap == NuclearCapabilityUtils.EMPTY_CHANNELMAP) {
        	return;
        }
        HashMap<UUID, HashSet<TunnelFrequency>> map = cap.getMap();
        ICapabilityTunnelMap tunCap = level.getCapability(NuclearScienceCapabilities.CAPABILITY_TUNNELMAP).orElse(NuclearCapabilityUtils.EMPTY_TUNNELMAP);
        if(tunCap == NuclearCapabilityUtils.EMPTY_TUNNELMAP) {
        	return ;
        }
        HashMap<TunnelFrequency, TunnelFrequencyBuffer> tunMap = tunCap.getMap();

        HashSet<TunnelFrequency> publicFrequencies = map.getOrDefault(TunnelFrequency.PUBLIC_ID, new HashSet<>());

        if(publicFrequencies.isEmpty()) {
            return;
        }

        for(TunnelFrequency frequency : publicFrequencies) {
            tunMap.remove(frequency);
        }

        map.remove(TunnelFrequency.PUBLIC_ID);

    }

    private static ServerLevel getOverworld() {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }

}
