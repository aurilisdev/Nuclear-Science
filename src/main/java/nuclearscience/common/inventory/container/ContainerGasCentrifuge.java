package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeProcessorUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainer;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileGasCentrifuge;

public class ContainerGasCentrifuge extends GenericContainer<TileGasCentrifuge> {

    public ContainerGasCentrifuge(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(5), new SimpleContainerData(3));
    }

    public ContainerGasCentrifuge(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerGasCentrifuge(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
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
