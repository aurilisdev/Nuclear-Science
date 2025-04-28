package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.inventory.container.util.GenericInterfaceBoundContainer;
import nuclearscience.common.tile.reactor.logisticsnetwork.TileSupplyModule;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.utilities.math.Color;

public class ContainerSupplyModule extends GenericInterfaceBoundContainer<TileSupplyModule> {

    public ContainerSupplyModule(int id, Inventory playerinv) {
        this(id, playerinv, new SimpleContainer(18), new SimpleContainerData(3));
    }

    public ContainerSupplyModule(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_SUPPLYMODULE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container container, Inventory inventory) {
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
