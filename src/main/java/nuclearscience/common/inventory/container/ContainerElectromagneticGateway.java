package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerElectromagneticGateway extends GenericContainerBlockEntity<TileElectromagneticGateway> {

    public ContainerElectromagneticGateway(int id, PlayerInventory playerinv) {
        this(id, playerinv, new Inventory(0), new IntArray(3));
    }

    public ContainerElectromagneticGateway(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_ELECTROMAGNETICGATEWAY.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory container, PlayerInventory inventory) {

    }
}
