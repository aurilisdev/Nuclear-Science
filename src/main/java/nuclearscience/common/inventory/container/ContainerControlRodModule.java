package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.inventory.container.util.GenericInterfaceBoundContainer;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileControlRodModule;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerControlRodModule extends GenericInterfaceBoundContainer<TileControlRodModule> {

    public ContainerControlRodModule(int id, PlayerInventory playerinv) {
        this(id, playerinv, new Inventory(0), new IntArray(5));
    }
    public ContainerControlRodModule(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_CONTROLRODMODULE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory container, PlayerInventory inventory) {

    }
}
