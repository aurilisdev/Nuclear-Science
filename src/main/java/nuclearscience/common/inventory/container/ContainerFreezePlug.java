package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileFreezePlug;

public class ContainerFreezePlug extends GenericContainer<TileFreezePlug> {

    public ContainerFreezePlug(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
    }

    public ContainerFreezePlug(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_FREEZEPLUG.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new SlotRestricted(inv, nextIndex(), 25, 42, DeferredRegisters.ITEM_FLINAK.get()));
    }
}
