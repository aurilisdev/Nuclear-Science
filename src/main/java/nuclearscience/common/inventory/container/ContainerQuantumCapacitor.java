package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerQuantumCapacitor extends GenericContainerBlockEntity<TileQuantumCapacitor> {

	public ContainerQuantumCapacitor(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(0), new IntArray(3));
	}

	public ContainerQuantumCapacitor(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_QUANTUMCAPACITOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		// Filler
	}

}