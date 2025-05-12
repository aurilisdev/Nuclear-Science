package nuclearscience.common.packet.type.server;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import nuclearscience.api.quantumtunnel.FrequencyType;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

public class ServerBarrierMethods {
    public static void createNewPacket(UUID creator, FrequencyType type, String name) {

        World world = ServerLifecycleHooks.getCurrentServer().overworld();

        PlayerEntity player = world.getPlayerByUUID(creator);

        if(player == null) {
            return;
        }

        UUID frequencyID = UUID.randomUUID();

        boolean valid = false;

        int tries = 0;

        while(!valid && tries < 10) {
            if(TunnelFrequencyManager.isValidTunnelID(frequencyID)) {
                valid = true;
                break;
            } else {
                frequencyID = UUID.randomUUID();
            }
            tries++;
        }

        if(valid) {
            if(type == FrequencyType.PRIVATE) {

                TunnelFrequencyManager.addPlayerFrequency(creator, TunnelFrequency.createPrivate(frequencyID, player, name));

            } else if(type == FrequencyType.PUBLIC) {

                TunnelFrequencyManager.addPublicFrequency(TunnelFrequency.createPublic(frequencyID, player, name));

            }
        }

    }

    public static void deleteFrequency(UUID requester, TunnelFrequency frequency) {

        if(frequency.getChannelType() == FrequencyType.PRIVATE) {
            TunnelFrequencyManager.removePlayerFrequency(frequency.getCreatorId(), requester, frequency);
        } else if (frequency.getChannelType() == FrequencyType.PUBLIC) {
            TunnelFrequencyManager.removePublicFrequency(requester, frequency);
        }

    }

    public static void editFrequency(UUID requester, TunnelFrequency frequency) {

        if(frequency.getChannelType() == FrequencyType.PRIVATE) {
            TunnelFrequencyManager.updatePlayerFrequencyName(frequency.getCreatorId(), requester, frequency);
        } else if (frequency.getChannelType() == FrequencyType.PUBLIC) {
            TunnelFrequencyManager.updatePublicFrequencyName(requester, frequency);
        }

    }
}
