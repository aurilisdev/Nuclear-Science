package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileReactorCore;

public class ContainerReactorCore extends GenericContainer<TileReactorCore> {

    public ContainerReactorCore(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
    }

    public ContainerReactorCore(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_REACTORCORE.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new SlotRestricted(inv, nextIndex(), 80, 11, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get(),
		DeferredRegisters.ITEM_FUELPLUTONIUM.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 152, 11, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get(),
		DeferredRegisters.ITEM_FUELPLUTONIUM.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 80, 47, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get(),
		DeferredRegisters.ITEM_FUELPLUTONIUM.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 152, 47, DeferredRegisters.ITEM_FUELHEUO2.get(), DeferredRegisters.ITEM_FUELLEUO2.get(),
		DeferredRegisters.ITEM_FUELPLUTONIUM.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 116, 24, DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
	addSlot(new SlotRestricted(inv, nextIndex(), 116, 59, DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
    }
}
