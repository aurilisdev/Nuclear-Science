package nuclearscience.common.inventory.container.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.NetworkDirection;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.type.client.PacketSetClientInterfaces;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public abstract class GenericInterfaceBoundContainer<T extends GenericTileInterfaceBound> extends GenericContainerBlockEntity<T> {

    public GenericInterfaceBoundContainer(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        if(!getLevel().isClientSide() && getPlayer() != null && getSafeHost() != null) {

            GenericTileInterfaceBound bound = getSafeHost();

            PacketSetClientInterfaces packet = new PacketSetClientInterfaces(bound.getBlockPos(), bound.getInterfacesForClient());
            
            NetworkHandler.CHANNEL.sendTo(packet, ((ServerPlayer) getPlayer()).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

        }

    }
}
