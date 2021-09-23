package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileFreezePlug;

public class ContainerFreezePlug extends GenericContainer<TileFreezePlug> {

    public ContainerFreezePlug(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(1), new IntArray(3));
    }

    public ContainerFreezePlug(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_FREEZEPLUG.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new SlotRestricted(inv, nextIndex(), 25, 42, DeferredRegisters.ITEM_FLINAK.get()));
    }
}
