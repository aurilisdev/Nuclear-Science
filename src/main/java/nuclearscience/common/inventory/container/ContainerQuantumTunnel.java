package nuclearscience.common.inventory.container;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.api.quantumtunnel.FrequencyConnectionManager;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;
import nuclearscience.common.packet.type.client.PacketSetClientTunnelFrequencies;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerQuantumTunnel extends GenericContainerBlockEntity<TileQuantumTunnel> {

	public ContainerQuantumTunnel(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
	}

	public ContainerQuantumTunnel(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_QUANTUMTUNNEL.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		// Filler
	}

	@Override
	public void addPlayerInventory(Inventory playerinv) {

	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();

		if(!getLevel().isClientSide() && getPlayer() != null && getSafeHost() != null) {
			PacketSetClientTunnelFrequencies packet = new PacketSetClientTunnelFrequencies(TunnelFrequencyManager.getFrequenciesForPlayerClient(getPlayer().getUUID()), FrequencyConnectionManager.getClientBuffer(getSafeHost().frequency.getValue()), getSafeHost().getBlockPos());
			PacketDistributor.sendToPlayer((ServerPlayer) getPlayer(), packet);
		}


	}
}
