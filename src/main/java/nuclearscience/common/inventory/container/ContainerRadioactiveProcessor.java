package nuclearscience.common.inventory.container;

import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.item.BucketItem;
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
    	addSlot(new GenericSlot(inv, nextIndex(), 74, 31));
    	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 133, 31));
    	addSlot(new SlotRestricted(inv, nextIndex(), 74, 51, false, ItemCanister.class, BucketItem.class));
    	addSlot(new SlotRestricted(inv, nextIndex(), 153, 14,
    		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
    		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    	addSlot(new SlotRestricted(inv, nextIndex(), 153, 34,
    		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
    		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    	addSlot(new SlotRestricted(inv, nextIndex(), 153, 54,
    		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
    		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    }
}
