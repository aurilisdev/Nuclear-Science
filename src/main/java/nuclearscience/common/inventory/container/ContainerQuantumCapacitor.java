package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileQuantumCapacitor;

public class ContainerQuantumCapacitor extends GenericContainer<TileQuantumCapacitor> {

    public ContainerQuantumCapacitor(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(0), new IntArray(3));
    }

    public ContainerQuantumCapacitor(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_QUANTUMCAPACITOR.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	// Filler
    }

}
