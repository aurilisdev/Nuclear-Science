package nuclearscience.common.inventory.container;

import electrodynamics.common.inventory.container.GenericContainerInventory;
import electrodynamics.common.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.tile.TileRadioisotopeGenerator;

public class ContainerRadioisotopeGenerator extends GenericContainerInventory<TileRadioisotopeGenerator> {

    public ContainerRadioisotopeGenerator(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(1));
    }

    public ContainerRadioisotopeGenerator(int id, PlayerInventory playerinv, IInventory inventory) {
	this(id, playerinv, inventory, new IntArray(0 + 3));
    }

    public ContainerRadioisotopeGenerator(int id, PlayerInventory playerinv, IInventory inventory,
	    IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_RADIOISOTOPEGENERATOR.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new SlotRestricted(inv, nextIndex(), 25, 42) {
	    @Override
	    public boolean isItemValid(ItemStack stack) {
		return RadiationRegister.get(stack.getItem()) != RadiationRegister.NULL;
	    }
	});
    }
}
