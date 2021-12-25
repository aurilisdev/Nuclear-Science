package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileQuantumAssembler;

public class ContainerQuantumAssembler extends GenericContainer<TileQuantumAssembler> {

	public ContainerQuantumAssembler(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(8), new SimpleContainerData(3));
	}

	public ContainerQuantumAssembler(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_QUANTUMASSEMBLER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 80, 40, DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 53, 56, DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 107, 56, DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 53, 88, DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 107, 88, DeferredRegisters.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 80, 103, DeferredRegisters.ITEM_CELLDARKMATTER.get()));

		addSlot(new GenericSlot(inv, nextIndex(), 80, 72));
		addSlot(new GenericSlot(inv, nextIndex(), 20, 72));
	}
}
