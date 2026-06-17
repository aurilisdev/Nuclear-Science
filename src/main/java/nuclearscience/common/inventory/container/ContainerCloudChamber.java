package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileCloudChamber;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerCloudChamber extends GenericContainerBlockEntity<TileCloudChamber> {

    public ContainerCloudChamber(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(), new SimpleContainerData(3));
    }

    public ContainerCloudChamber(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(NuclearScienceMenuTypes.CONTAINER_CLOUDCHAMBER.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container container, Inventory inventory) {

    }
}
