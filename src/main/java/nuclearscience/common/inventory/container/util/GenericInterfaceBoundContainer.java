package nuclearscience.common.inventory.container.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IIntArray;
import net.minecraftforge.fml.network.NetworkDirection;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.type.client.PacketSetClientInterfaces;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public abstract class GenericInterfaceBoundContainer<T extends GenericTileInterfaceBound> extends GenericContainerBlockEntity<T> {

    public GenericInterfaceBoundContainer(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
        super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        if(!getLevel().isClientSide() && getPlayer() != null && getSafeHost() != null) {

            GenericTileInterfaceBound bound = getSafeHost();

            PacketSetClientInterfaces packet = new PacketSetClientInterfaces(bound.getBlockPos(), bound.getInterfacesForClient());
            
            NetworkHandler.CHANNEL.sendTo(packet, ((ServerPlayerEntity) getPlayer()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

        }

    }
}
