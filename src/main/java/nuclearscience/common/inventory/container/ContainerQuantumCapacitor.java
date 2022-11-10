package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileQuantumCapacitor;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerQuantumCapacitor extends GenericContainerBlockEntity<TileQuantumCapacitor> {

	public ContainerQuantumCapacitor(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
	}

	public ContainerQuantumCapacitor(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_QUANTUMCAPACITOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		// Filler
	}

}
