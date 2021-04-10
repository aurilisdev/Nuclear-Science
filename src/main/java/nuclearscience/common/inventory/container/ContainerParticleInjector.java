package nuclearscience.common.inventory.container;

import electrodynamics.common.inventory.container.slot.GenericSlot;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileParticleInjector;

public class ContainerParticleInjector extends GenericContainer<TileParticleInjector> {

    public ContainerParticleInjector(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(3), new IntArray(3));
    }

    public ContainerParticleInjector(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_PARTICLEINJECTOR.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerParticleInjector(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new GenericSlot(inv, nextIndex(), 98, 14));
	addSlot(new SlotRestricted(inv, nextIndex(), 98, 50, DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get()));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 133, 32));
    }
}
