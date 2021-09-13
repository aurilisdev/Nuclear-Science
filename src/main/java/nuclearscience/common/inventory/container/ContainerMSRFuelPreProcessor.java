package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.FurnaceResultSlot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileMSRFuelPreProcessor;

public class ContainerMSRFuelPreProcessor extends GenericContainer<TileMSRFuelPreProcessor> {

    public ContainerMSRFuelPreProcessor(int id, PlayerInventory playerinv) {
	this(id, playerinv, new Inventory(8), new IntArray(3));
    }

    public ContainerMSRFuelPreProcessor(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(DeferredRegisters.CONTAINER_MSRFUELPREPROCESSOR.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerMSRFuelPreProcessor(ContainerType<?> type, int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	addSlot(new GenericSlot(inv, nextIndex(), 74, 20));
	addSlot(new GenericSlot(inv, nextIndex(), 74, 40));
	addSlot(new GenericSlot(inv, nextIndex(), 74, 60));
	addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 128, 40));
	addSlot(new SlotRestricted(inv, nextIndex(), 45, 50, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
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
