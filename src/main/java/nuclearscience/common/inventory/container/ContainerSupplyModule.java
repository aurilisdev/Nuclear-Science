package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.inventory.container.util.GenericInterfaceBoundContainer;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.utilities.math.Color;

public class ContainerSupplyModule extends GenericInterfaceBoundContainer<TileSupplyModule> {

    public ContainerSupplyModule(int id, PlayerInventory playerinv) {
        this(id, playerinv, new Inventory(18), new IntArray(5));
    }

    public ContainerSupplyModule(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_SUPPLYMODULE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory container, PlayerInventory inventory) {
        int offset = 0;
        for(int x = 0; x < 9; ++x) {
            offset++;
            this.addSlot(new SlotGeneric(container, x, 8 + x * 18, 20).setIOColor(new Color(0, 240, 255, 255)));
        }
        for(int x = 0; x < 9; ++x) {
            this.addSlot(new SlotGeneric(container, x + offset, 8 + x * 18, 50).setIOColor(new Color(255, 0, 0, 255)));
        }
    }
}
