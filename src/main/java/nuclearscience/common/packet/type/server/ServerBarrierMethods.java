package nuclearscience.common.packet.type.server;

import java.util.UUID;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import nuclearscience.api.quantumtunnel.FrequencyType;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;

public class ServerBarrierMethods {
    public static void createNewPacket(UUID creator, FrequencyType type, String name) {

	Level world = ServerLifecycleHooks.getCurrentServer().overworld();

	Player player = world.getPlayerByUUID(creator);

	if (player == null) {
	    return;
	}

	UUID frequencyID = UUID.randomUUID();

	boolean valid = false;

	int tries = 0;

	while (!valid && tries < 10) {
	    if (TunnelFrequencyManager.isValidTunnelID(frequencyID)) {
		valid = true;
		break;
	    }
	    frequencyID = UUID.randomUUID();
	    tries++;
	}

	if (valid) {
	    if (type == FrequencyType.PRIVATE) {

		TunnelFrequencyManager.addPlayerFrequency(creator,
			TunnelFrequency.createPrivate(frequencyID, player, name));

	    } else if (type == FrequencyType.PUBLIC) {

		TunnelFrequencyManager.addPublicFrequency(TunnelFrequency.createPublic(frequencyID, player, name));

	    }
	}

    }

    public static void deleteFrequency(UUID requester, TunnelFrequency frequency) {

	if (frequency.getChannelType() == FrequencyType.PRIVATE) {
	    TunnelFrequencyManager.removePlayerFrequency(frequency.getCreatorId(), requester, frequency);
	} else if (frequency.getChannelType() == FrequencyType.PUBLIC) {
	    TunnelFrequencyManager.removePublicFrequency(requester, frequency);
	}

    }

    public static void editFrequency(UUID requester, TunnelFrequency frequency) {

	if (frequency.getChannelType() == FrequencyType.PRIVATE) {
	    TunnelFrequencyManager.updatePlayerFrequencyName(frequency.getCreatorId(), requester, frequency);
	} else if (frequency.getChannelType() == FrequencyType.PUBLIC) {
	    TunnelFrequencyManager.updatePublicFrequencyName(requester, frequency);
	}

    }
}
