package nuclearscience.common.inventory.container;

import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.BucketItem;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileNuclearBoiler;

public class ContainerNuclearBoiler extends GenericContainer<TileNuclearBoiler> {

    public ContainerNuclearBoiler(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
    }

    public ContainerNuclearBoiler(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_NUCLEARBOILER.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerNuclearBoiler(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new GenericSlot(inv, nextIndex(), 74, 31));
	addSlot(new SlotRestricted(inv, nextIndex(), 74, 51, false, ItemCanister.class, BucketItem.class));
	addSlot(new SlotRestricted(inv, nextIndex(), 108, 51, false, ItemCanister.class, BucketItem.class));
	addSlot(new SlotRestricted(inv, nextIndex(), 150, 14,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 150, 34,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
	addSlot(new SlotRestricted(inv, nextIndex(), 150, 54,
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.basicspeed),
		electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeProcessorUpgrade.advancedspeed)));
    }
}
