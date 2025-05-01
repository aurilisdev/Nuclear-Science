package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.inventory.container.util.GenericInterfaceBoundContainer;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileMonitorModule;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerMonitorModule extends GenericInterfaceBoundContainer<TileMonitorModule> {

    public ContainerMonitorModule(int id, Inventory playerinv) {
        this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
    }

    public ContainerMonitorModule(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_MONITORMODULE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container container, Inventory inventory) {

    }
}
