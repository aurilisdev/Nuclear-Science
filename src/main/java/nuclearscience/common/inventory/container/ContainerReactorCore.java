package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileReactorCore;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerReactorCore extends GenericContainerBlockEntity<TileReactorCore> {

	public ContainerReactorCore(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerReactorCore(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 80, 11, NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 152, 11, NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 80, 47, NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 152, 47, NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 24, NuclearScienceItems.ITEM_CELLDEUTERIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 59, NuclearScienceItems.ITEM_CELLDEUTERIUM.get()));
	}
}
