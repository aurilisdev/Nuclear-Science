package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileGasCentrifuge;

public class ContainerGasCentrifuge extends GenericContainer<TileGasCentrifuge> {

    public ContainerGasCentrifuge(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(5), new IntArray(3));
    }

    public ContainerGasCentrifuge(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerGasCentrifuge(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 126, 21));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 126, 48));
	addSlot(new SlotRestricted(inv, nextIndex(), 186, 14,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 186, 34,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 186, 54,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    }
}
