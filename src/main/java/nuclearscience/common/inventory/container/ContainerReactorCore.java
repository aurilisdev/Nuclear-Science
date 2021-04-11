package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileReactorCore;

public class ContainerReactorCore extends GenericContainer<TileReactorCore> {

    public ContainerReactorCore(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(6), new IntArray(3));
    }

    public ContainerReactorCore(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_REACTORCORE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new SlotRestricted(inv, nextIndex(), 80, 11, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 152, 11, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 80, 47, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 152, 47, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 116, 24, DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 116, 59, DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
    }
}
