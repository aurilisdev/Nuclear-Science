package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.fml.network.NetworkDirection;
import nuclearscience.api.quantumtunnel.FrequencyConnectionManager;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.type.client.PacketSetClientTunnelFrequencies;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerQuantumTunnel extends GenericContainerBlockEntity<TileQuantumTunnel> {

	public ContainerQuantumTunnel(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(0), new IntArray(3));
	}

	public ContainerQuantumTunnel(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_QUANTUMTUNNEL.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		// Filler
	}

	@Override
	public void addPlayerInventory(PlayerInventory playerinv) {

	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();

		if(!getLevel().isClientSide() && getPlayer() != null && getSafeHost() != null) {
			PacketSetClientTunnelFrequencies packet = new PacketSetClientTunnelFrequencies(TunnelFrequencyManager.getFrequenciesForPlayerClient(getPlayer().getUUID()), FrequencyConnectionManager.getClientBuffer(getSafeHost().frequency.getValue()), getSafeHost().getBlockPos());
			NetworkHandler.CHANNEL.sendTo(packet, ((ServerPlayerEntity) getPlayer()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
		}


	}
}
