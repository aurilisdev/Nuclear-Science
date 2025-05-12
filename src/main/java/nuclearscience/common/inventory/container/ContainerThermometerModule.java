package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.inventory.container.util.GenericInterfaceBoundContainer;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileThermometerModule;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerThermometerModule extends GenericInterfaceBoundContainer<TileThermometerModule> {

    public ContainerThermometerModule(int id, PlayerInventory playerinv) {
        this(id, playerinv, new Inventory(0), new IntArray(3));
    }

    public ContainerThermometerModule(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_THERMOMETERMODULE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory container, PlayerInventory inventory) {

    }
}
