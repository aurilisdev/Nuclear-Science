package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileRadioactiveProcessor;

public class ContainerRadioactiveProcessor extends GenericContainer<TileRadioactiveProcessor> {

    public ContainerRadioactiveProcessor(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(6), new IntArray(3));
    }

    public ContainerRadioactiveProcessor(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_RADIOACTIVEPROCESSOR.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerRadioactiveProcessor(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {

    }

}
